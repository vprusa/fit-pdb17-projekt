package cz.vutbr.fit.pdb.project01;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.text.TableView;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.ParkovaciMisto;
import oracle.spatial.geometry.JGeometry;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class SpatialDataCanvasPanelForm extends JPanel implements MouseListener, MouseMotionListener {

	private Map<Integer, Shape> shapesMap = new HashMap<>();
	private Map<Integer, JGeometry> geometryMap = new HashMap<>();
	
	private int pressedX;
	private int pressedY;
	private int selectedShape;
	private boolean selectedShapeChanged = false;
	
	/**
	 * Create the panel.
	 */
	public SpatialDataCanvasPanelForm() {
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		int key = 0;
		Shape shape = new Ellipse2D.Float(100.0f, 100.0f, 100.0f, 100.0f);
		shapesMap.put(key++, shape);
		
		shape = new Ellipse2D.Float(50.0f, 50.0f, 50.0f, 50.0f);
		shapesMap.put(key++, shape);
		
		shape = new Ellipse2D.Float(25.0f, 100.0f, 50.0f, 100.0f);
		shapesMap.put(key++, shape);
		
		System.out.print("SpatialDataCanvasPanelForm\n");
		try {
			loadObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = new Rectangle(0,0,getWidth(), getHeight() );
        g2.setPaint(Color.white);
        g2.fill(bounds);

        for (Map.Entry<Integer, Shape> shapeEntry : shapesMap.entrySet()) {
        	
            g2.setPaint( Color.cyan );
            g2.fill(shapeEntry.getValue());
            g2.setPaint( Color.black );
            g2.draw(shapeEntry.getValue());
        }
    }
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// call method to check if i clicked on object
		// smthing like shapeAt(e.getX(), e.getY());
		pressedX = e.getX();
		pressedY = e.getY();
		
		selectedShape = shapeAt(pressedX, pressedY);
		
		System.out.print("mousePressed: " + e.getX() + ", " + e.getY() + "\n");
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (selectedShapeChanged) {
			// update shape (geometry) in sql db
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(selectedShape != -1) {
			int deltaX = e.getX() - pressedX;
			int deltaY = e.getY() - pressedY;
		
			moveSelectedShape(deltaX, deltaY);
			selectedShapeChanged = true;
		}
		System.out.print("mouseDragged: " + e.getX() + ", " + e.getY() + "\n");
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		;
	}
	
	
	private int shapeAt(int x, int y) {
		// create some bigger triangle and check "anyinteract" with sql
		// something like sql select on 
		// SDO_RELATE(geometry, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(p1, p2, p3)), 'mask=anyinteract') = 'TRUE'")
		int size = 3;
		int p1x = x + size; int p1y = y - size;
		int p2x = x - size; int p2y = y + size;
		int p3x = x + size; int p3y = y + size;
		try {
			
			Query q = TableBase.getEntityManager().createQuery("SELECT id "
															 + "FROM ParkovaciMisto "
															 + "WHERE SDO_RELATE(geometry, "
																			  + "SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1, 1003, 4), "
																			  + "SDO_ORDINATE_ARRAY(" + p1x + "," + p1y + ", "
																			  						  + p2x + "," + p2y + ", " 
																			  						  + p3x + "," + p3y + ")), 'mask=anyinteract') = 'TRUE')", BigDecimal.class);
			
		
			BigDecimal result = (BigDecimal) q.getSingleResult();
			
			return result.intValue();
		} catch (Exception e) {
			System.out.print("shapeAt: " + e + "\n");
		}
		return -1;
	}
	
	private void moveSelectedShape(int deltaX, int deltaY) {
		// TODO Auto-generated method stub
		// something like:
		// getGeometry(selectedShape) -> move geometry -> convert to shape -> update in Map
		
		repaint();
		return;
	}
	
	private void loadObjects() throws Exception {
		try {
			Query q = TableBase.getEntityManager().createQuery("SELECT p FROM ParkovaciMisto p", ParkovaciMisto.class);
			
			List<ParkovaciMisto> resultList = q.getResultList();
			for (ParkovaciMisto result : resultList) {
				JGeometry geo = JGeometry.load(result.getGeometry());
				geometryMap.put(result.getIdMista().intValue(), geo);
				shapesMap.put(result.getIdMista().intValue(), geo.createShape());
				System.out.print("result: " + result.getIdMista().toString() + ", geo: " + geo.toStringFull() + "\n");
			}
		}
		catch (Exception e) {
			System.out.print("loadObjects: " + e + "\n");
		}
		
	}
	

}
