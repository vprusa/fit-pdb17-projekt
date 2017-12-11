package cz.vutbr.fit.pdb.project01;

import javax.persistence.Query;
import javax.swing.*;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.ParkovaciMisto;
import cz.vutbr.fit.pdb.project.tables.Vjezd;
import cz.vutbr.fit.pdb.project.tables.Vyjezd;
import cz.vutbr.fit.pdb.project.tables.entities.JGeometryType;
import oracle.spatial.geometry.JGeometry;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class SpatialDataCanvasPanelForm extends JPanel implements MouseListener, MouseMotionListener {
	
	public enum SpatialType {
		parkPlace,
		entrance,
		exit,
		nothing;
	}

	private Map<Long, Shape> shapesMap = new HashMap<>();
	private Map<Long, ParkovaciMisto> objectsMap = new HashMap<>();
	
	private Map<Long, Shape> entranceShapesMap = new HashMap<>();
	private Map<Long, Vjezd> entranceObjectsMap = new HashMap<>();
	
	private Map<Long, Shape> exitShapesMap = new HashMap<>();
	private Map<Long, Vyjezd> exitObjectsMap = new HashMap<>();
	
	private int pressedX;
	private int pressedY;
	private SpatialType selectedType = SpatialType.nothing;
	private Long selectedShape;
	private boolean selectedShapeChanged = false;
	
	private final SpatialDataPanelForm parentFormPanel;
	
	/**
	 * Create the panel.
	 */
	public SpatialDataCanvasPanelForm(SpatialDataPanelForm parentFormPanel) {
		this.parentFormPanel = parentFormPanel;
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
        	if (selectedType == SpatialType.parkPlace && 
        			shapeEntry.getKey().equals(selectedShape)) {
        		g2.setPaint(Color.green);
        	} else {
        		g2.setPaint(Color.cyan);
        	}
            g2.fill(shapeEntry.getValue());
            g2.setPaint(Color.black);
            g2.draw(shapeEntry.getValue());
        }
        
        for (Map.Entry<Long, Shape> shapeEntry : entranceShapesMap.entrySet()) {
        	System.out.print("paintComponent: " + shapeEntry.getKey() + "\n");
        	if (selectedType == SpatialType.entrance && 
        			shapeEntry.getKey().equals(selectedShape)) {
        		g2.setPaint(Color.green);
        	} else {
        		g2.setPaint(Color.blue);
        	}
            g2.fill(shapeEntry.getValue());
            g2.setPaint(Color.black);
            g2.draw(shapeEntry.getValue());
        }
        
        for (Map.Entry<Long, Shape> shapeEntry : exitShapesMap.entrySet()) {
        	System.out.print("paintComponent: " + shapeEntry.getKey() + "\n");
        	if (selectedType == SpatialType.exit && 
        			shapeEntry.getKey().equals(selectedShape)) {
        		g2.setPaint(Color.green);
        	} else {
        		g2.setPaint(Color.red);
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
		parentFormPanel.onObjectSelected();
		
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
		
		Long idx = null;
		
		JGeometry geometry = JGeometry.createLinearPolygon(new double[]{p1x, p1y, p2x, p2y, p3x, p3y}, 2, 0);
		idx = ParkovaciMisto.selectObjectByGeometry(new JGeometryType(geometry));
		if (idx != null) {
			selectedType = SpatialType.parkPlace;
			return idx;
		} else {
			// seach entrance/exit (less priority)
			idx = Vjezd.selectObjectByGeometry(new JGeometryType(geometry));
			if (idx != null) {
				selectedType = SpatialType.entrance;
				return idx;
			}
			
			idx = Vyjezd.selectObjectByGeometry(new JGeometryType(geometry));
			if (idx != null) {
				selectedType = SpatialType.exit;
				return idx;
			}
			
			selectedType = SpatialType.nothing;
			return null;
		}
	}
	
	private void moveSelectedShape(int deltaX, int deltaY) {
		// something like:
		// getGeometry(selectedShape) -> move geometry -> convert to shape -> update in Map
		switch (selectedType) {
		case parkPlace:
			ParkovaciMisto parkPlace = objectsMap.get(selectedShape);
			JGeometry parkPlacegeometry = parkPlace.getGeoMista().getJGeometry();
			
	        for (int i = 0; i < parkPlacegeometry.getOrdinatesArray().length; i = i + 2) {
	        	parkPlacegeometry.getOrdinatesArray()[i] = parkPlacegeometry.getOrdinatesArray()[i] + deltaX;
	            parkPlacegeometry.getOrdinatesArray()[i + 1] = parkPlacegeometry.getOrdinatesArray()[i + 1] + deltaY;
	        }

	        parkPlace.setGeoMista(new JGeometryType(parkPlacegeometry));
	        objectsMap.put(selectedShape, parkPlace);
	        shapesMap.put(selectedShape, parkPlacegeometry.createShape());
			break;
			
		case entrance:
			Vjezd entrance = entranceObjectsMap.get(selectedShape);
			JGeometry entranceGeometry = entrance.getGeoVjezd().getJGeometry();
			
	        for (int i = 0; i < entranceGeometry.getOrdinatesArray().length; i = i + 2) {
	        	entranceGeometry.getOrdinatesArray()[i] = entranceGeometry.getOrdinatesArray()[i] + deltaX;
	            entranceGeometry.getOrdinatesArray()[i + 1] = entranceGeometry.getOrdinatesArray()[i + 1] + deltaY;
	        }

	        entrance.setGeoVjezd(new JGeometryType(entranceGeometry));
	        entranceObjectsMap.put(selectedShape, entrance);
	        entranceShapesMap.put(selectedShape, entranceGeometry.createShape());
			break;
			
		case exit:
			Vyjezd exit = exitObjectsMap.get(selectedShape);
			JGeometry exitGeometry = exit.getGeoVyjezd().getJGeometry();
			
	        for (int i = 0; i < exitGeometry.getOrdinatesArray().length; i = i + 2) {
	        	exitGeometry.getOrdinatesArray()[i] = exitGeometry.getOrdinatesArray()[i] + deltaX;
	            exitGeometry.getOrdinatesArray()[i + 1] = exitGeometry.getOrdinatesArray()[i + 1] + deltaY;
	        }

	        exit.setGeoVyjezd(new JGeometryType(exitGeometry));
	        exitObjectsMap.put(selectedShape, exit);
	        exitShapesMap.put(selectedShape, exitGeometry.createShape());
			break;
			
		case nothing:
			break;
		}
		
		repaint();
		return;
	}
	
	private void loadObjects() throws Exception {
		try {
			//Query q = TableBase.getEntityManager().createQuery("SELECT p FROM ParkovaciMisto p", ParkovaciMisto.class);
			
			List<ParkovaciMisto> parkPlaceresultList = ParkovaciMisto.list();
			for (ParkovaciMisto result : parkPlaceresultList) {
				objectsMap.put(result.getIdMista(), result);
				shapesMap.put(result.getIdMista(),  result.getGeoMista().getJGeometry().createShape());
				System.out.print("ParkovaciMisto result: " + result.getIdMista().toString() + ", geo: " + result.getGeoMista().getJGeometry().toStringFull() + "\n");
			}
			
			List<Vjezd> entranceResultList = Vjezd.list();
			for (Vjezd result : entranceResultList) {
				entranceObjectsMap.put(result.getIdVjezd(), result);
				entranceShapesMap.put(result.getIdVjezd(),  result.getGeoVjezd().getJGeometry().createShape());
				System.out.print("Vjezd result: " + result.getIdVjezd().toString() + ", geo: " + result.getGeoVjezd().getJGeometry().toStringFull() + "\n");
			}
			
			List<Vyjezd> exitResultList = Vyjezd.list();
			for (Vyjezd result : exitResultList) {
				exitObjectsMap.put(result.getIdVyjezd(), result);
				exitShapesMap.put(result.getIdVyjezd(),  result.getGeoVyjezd().getJGeometry().createShape());
				System.out.print("Vyjezd result: " + result.getIdVyjezd().toString() + ", geo: " + result.getGeoVyjezd().getJGeometry().toStringFull() + "\n");
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
	
	public SpatialType getSelectedObjectType() {
		// something like: return objectsMap.get(selectedID);
		return selectedType;
	}
	
	public Long getSelectedObjectID() {
		// something like: return objectsMap.get(selectedID);
		return selectedShape;
	}
	
	public ParkovaciMisto getParkPlaceByID(Long id) {
		return objectsMap.get(id);
	}
	
	public Vjezd getEntranceByID(Long id) {
		return entranceObjectsMap.get(id);
	}
	
	public Vyjezd getExitByID(Long id) {
		return exitObjectsMap.get(id);
	}

	public void deleteSelected() {
		switch(selectedType) {
		case parkPlace:
			objectsMap.remove(selectedShape);
			shapesMap.remove(selectedShape);
			ParkovaciMisto.delete(selectedShape);
			repaint();
			break;
			
		case entrance:
			entranceObjectsMap.remove(selectedShape);
			entranceShapesMap.remove(selectedShape);
			Vjezd.delete(selectedShape);
			repaint();
			break;
			
		case exit:
			exitObjectsMap.remove(selectedShape);
			exitShapesMap.remove(selectedShape);
			Vyjezd.delete(selectedShape);
			repaint();
			break;
			
		case nothing:	
			break;
		}
		selectedShape = null;
		selectedType = SpatialType.nothing;
		parentFormPanel.onObjectSelected();
	}

	public void addParkPlace() {
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		JGeometry geo = JGeometry.createLinearPolygon(new double[]{middleX + 20, middleY + 10,
																   middleX - 20, middleY + 10,
																   middleX - 20, middleY - 10,
																   middleX + 20, middleY - 10}, 2, 0);
		ParkovaciMisto parkPlace = ParkovaciMisto.insert("Nové místo", geo, Collections.emptySet());
		
		objectsMap.put(parkPlace.getIdMista(), parkPlace);
		shapesMap.put(parkPlace.getIdMista(), parkPlace.getGeoMista().getJGeometry().createShape());
		
		repaint();
	}

	public void addEntrance() {
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		JGeometry geo = JGeometry.createLinearPolygon(new double[]{middleX + 10, middleY + 5, 
																   middleX - 10, middleY + 5,
																   middleX - 15, middleY,
																   middleX - 10, middleY - 5,
																   middleX + 10, middleY - 5,
																   middleX + 15, middleY}, 2, 0);
		Vjezd entrance = Vjezd.insert(geo);
		
		entranceObjectsMap.put(entrance.getIdVjezd(), entrance);
		entranceShapesMap.put(entrance.getIdVjezd(), entrance.getGeoVjezd().getJGeometry().createShape());
		
		repaint();
	}

	public void addExit() {
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		JGeometry geo = JGeometry.createLinearPolygon(new double[]{middleX + 10, middleY + 5, 
																   middleX - 10, middleY + 5,
																   middleX - 15, middleY,
																   middleX - 10, middleY - 5,
																   middleX + 10, middleY - 5,
																   middleX + 15, middleY}, 2, 0);
		Vyjezd exit = Vyjezd.insert(geo);
		
		exitObjectsMap.put(exit.getIdVyjezd(), exit);
		exitShapesMap.put(exit.getIdVyjezd(), exit.getGeoVyjezd().getJGeometry().createShape());
		
		repaint();
	}
	

}
