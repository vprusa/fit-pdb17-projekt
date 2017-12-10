package cz.vutbr.fit.pdb.project01;

import javax.persistence.Query;
import javax.swing.*;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.ParkovaciMisto;
import cz.vutbr.fit.pdb.project.tables.entities.JGeometryType;
import oracle.spatial.geometry.JGeometry;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class SpatialDataCanvasPanelForm extends JPanel implements MouseListener, MouseMotionListener {

	private Map<Long, Shape> shapesMap = new HashMap<>();
	private Map<Long, ParkovaciMisto> objectsMap = new HashMap<>();
	
	private int pressedX;
	private int pressedY;
	private Long selectedShape;
	private boolean selectedShapeChanged = false;
	
	/**
	 * Create the panel.
	 */
	public SpatialDataCanvasPanelForm() {
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
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

        for (Map.Entry<Long, Shape> shapeEntry : shapesMap.entrySet()) {
        	System.out.print("paintComponent: " + shapeEntry.getKey() + "\n");
        	if (shapeEntry.getKey().equals(selectedShape)) {
        		g2.setPaint(Color.green);
        	} else {
        		g2.setPaint(Color.cyan);
        	}
            g2.fill(shapeEntry.getValue());
            g2.setPaint(Color.black);
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
		
		System.out.print("selectedShape: " + selectedShape + "\n");	
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (selectedShapeChanged) {
			// update shape (geometry) in sql db
			saveSelectedShape();
			selectedShapeChanged = false;
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
		
		if(selectedShape != null) {
			int deltaX = e.getX() - pressedX;
			int deltaY = e.getY() - pressedY;
			
			pressedX = e.getX();
			pressedY = e.getY();
		
			moveSelectedShape(deltaX, deltaY);
			selectedShapeChanged = true;
		}
		System.out.print("mouseDragged: " + e.getX() + ", " + e.getY() + "\n");
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		;
	}
	
	
	private Long shapeAt(int x, int y) {
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
															 + "WHERE SDO_RELATE(geoMista, "
																			  + "SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1, 1003, 4), "
																			  + "SDO_ORDINATE_ARRAY(" + p1x + "," + p1y + ", "
																			  						  + p2x + "," + p2y + ", " 
																			  						  + p3x + "," + p3y + ")), 'mask=anyinteract') = 'TRUE')", Long.class);
			
		
			List<Long> result = q.getResultList();
			
			return result.get(0);
		} catch (Exception e) {
			System.out.print("shapeAt: " + e + "\n");
		}
		return null;
	}
	
	private void moveSelectedShape(int deltaX, int deltaY) {
		// TODO Auto-generated method stub
		// something like:
		// getGeometry(selectedShape) -> move geometry -> convert to shape -> update in Map
		
		ParkovaciMisto p = objectsMap.get(selectedShape);
		JGeometry geometry = p.getGeoMista().getJGeometry();
		
		System.out.print("deltaX: " + deltaX + ", deltaY: " + deltaY + "\n");
        System.out.print("geometry.getNumPoints(): " + geometry.getNumPoints() + " ,geometry.getOrdinatesArray().length: " + geometry.getOrdinatesArray().length + "\n");

        for (int i = 0; i < geometry.getOrdinatesArray().length; i = i + 2) {
        	geometry.getOrdinatesArray()[i] = geometry.getOrdinatesArray()[i] + deltaX;
            geometry.getOrdinatesArray()[i + 1] = geometry.getOrdinatesArray()[i + 1] + deltaY;
        }

        p.setGeoMista(new JGeometryType(geometry));
        objectsMap.put(selectedShape, p);
        shapesMap.put(selectedShape, geometry.createShape());
		
		repaint();
		return;
	}
	
	private void loadObjects() throws Exception {
		try {
			//Query q = TableBase.getEntityManager().createQuery("SELECT p FROM ParkovaciMisto p", ParkovaciMisto.class);
			
			List<ParkovaciMisto> resultList = ParkovaciMisto.list();
			for (ParkovaciMisto result : resultList) {
				objectsMap.put(result.getIdMista(), result);
				shapesMap.put(result.getIdMista(),  result.getGeoMista().getJGeometry().createShape());
				System.out.print("result: " + result.getIdMista().toString() + ", geo: " + result.getGeoMista().getJGeometry().toStringFull() + "\n");
			}
		}
		catch (Exception e) {
			System.out.print("loadObjects: " + e + "\n");
		}
		
	}
	
	private void saveSelectedShape() {
		ParkovaciMisto p = (ParkovaciMisto) TableBase.getEntityManager().find(ParkovaciMisto.class, selectedShape);
		p.update(p.getIdMista(), p.getPozn(), objectsMap.get(selectedShape).getGeoMista(), p.getParkovanis());

	}
	
	private void newParking() {
		;
	}
	
	private void newCar() {
		;
	}
	
	private void newEntrance() {
		;
	}
	
	private void newExit() {
		;
	}
	
	public Object getSelectedObject() {
		// something like: return objectsMap.get(selectedID);
		return objectsMap.get(selectedShape);
	}
	

}
