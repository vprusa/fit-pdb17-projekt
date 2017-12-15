package cz.vutbr.fit.pdb.project01;

import javax.persistence.Query;
import javax.swing.*;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.ParkovaciMisto;
import cz.vutbr.fit.pdb.project.tables.Vjezd;
import cz.vutbr.fit.pdb.project.tables.Vyjezd;
import cz.vutbr.fit.pdb.project.tables.Zona;
import cz.vutbr.fit.pdb.project.tables.entities.JGeometryType;
import oracle.spatial.geometry.JGeometry;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class SpatialDataCanvasPanelForm extends JPanel implements MouseListener, MouseMotionListener {

	public enum SpatialType {
		parkPlace, entrance, exit, zone, nothing;
	}

	private Map<Long, Shape> shapesMap = new HashMap<>();
	private Map<Long, ParkovaciMisto> objectsMap = new HashMap<>();

	private Map<Long, Shape> entranceShapesMap = new HashMap<>();
	private Map<Long, Vjezd> entranceObjectsMap = new HashMap<>();

	private Map<Long, Shape> exitShapesMap = new HashMap<>();
	private Map<Long, Vyjezd> exitObjectsMap = new HashMap<>();

	private Map<Long, Shape> zoneShapesMap = new HashMap<>();
	private Map<Long, Zona> zoneObjectsMap = new HashMap<>();

	private int pressedX;
	private int pressedY;
	private SpatialType selectedType = SpatialType.nothing;
	private Zona selectedShape;
	private boolean selectedShapeChanged = false;

	private boolean zoneAdding = false;
	private List<Double> zoneAddingShape = new ArrayList<>();

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
		Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());
		g2.setPaint(Color.white);
		g2.fill(bounds);

		for (Map.Entry<Long, Shape> shapeEntry : zoneShapesMap.entrySet()) {
			if (selectedType == SpatialType.zone && shapeEntry.getKey().equals(selectedShape.getIdZony())) {
				g2.setPaint(Color.green);
			} else {
				g2.setPaint(Color.gray);
			}
			g2.fill(shapeEntry.getValue());
			g2.setPaint(Color.black);
			g2.draw(shapeEntry.getValue());
		}

		for (Map.Entry<Long, Shape> shapeEntry : shapesMap.entrySet()) {
			if (selectedType == SpatialType.parkPlace && shapeEntry.getKey().equals(selectedShape.getIdZony())) {
				g2.setPaint(Color.green);
			} else {
				g2.setPaint(Color.cyan);
			}
			g2.fill(shapeEntry.getValue());
			g2.setPaint(Color.black);
			g2.draw(shapeEntry.getValue());
		}

		for (Map.Entry<Long, Shape> shapeEntry : entranceShapesMap.entrySet()) {
			if (selectedType == SpatialType.entrance && shapeEntry.getKey().equals(selectedShape.getIdZony())) {
				g2.setPaint(Color.green);
			} else {
				g2.setPaint(Color.blue);
			}
			g2.fill(shapeEntry.getValue());
			g2.setPaint(Color.black);
			g2.draw(shapeEntry.getValue());
		}

		for (Map.Entry<Long, Shape> shapeEntry : exitShapesMap.entrySet()) {
			if (selectedType == SpatialType.exit && shapeEntry.getKey().equals(selectedShape.getIdZony())) {
				g2.setPaint(Color.green);
			} else {
				g2.setPaint(Color.red);
			}
			g2.fill(shapeEntry.getValue());
			g2.setPaint(Color.black);
			g2.draw(shapeEntry.getValue());
		}

		if (zoneAdding) {
			g2.setPaint(Color.green);
			if (!zoneAddingShape.isEmpty()) {
				if (zoneAddingShape.size() >= 4) {
					for (int i = 0; i < (zoneAddingShape.size() / 2) - 1; ++i) {
						System.out.print("for zone: " + i);
						g2.drawLine(zoneAddingShape.get(i * 2).intValue(), zoneAddingShape.get(i * 2 + 1).intValue(),
								zoneAddingShape.get(i * 2 + 2).intValue(), zoneAddingShape.get(i * 2 + 3).intValue());
					}
				} else {
					g2.drawOval(zoneAddingShape.get(0).intValue(), zoneAddingShape.get(1).intValue(), 5, 5);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
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

		if (zoneAdding) {
			if (!zoneAddingShape.isEmpty()) {
				if ((pressedX - 15) < zoneAddingShape.get(0) && (pressedX + 15) > zoneAddingShape.get(0)) {
					if ((pressedY - 15) < zoneAddingShape.get(1) && (pressedY + 15) > zoneAddingShape.get(1)) {
						double[] zoneArray = new double[zoneAddingShape.size()];
						for (int i = 0; i < zoneArray.length; i++) {
							zoneArray[i] = zoneAddingShape.get(i);
						}
						Zona z = Zona.insert("Nová zóna", JGeometry.createLinearPolygon(zoneArray, 2, 0));
						zoneShapesMap.put(z.getIdZony(), z.getGeoZony().getJGeometry().createShape());
						zoneObjectsMap.put(z.getIdZony(), z);

						zoneAdding = false;
						zoneAddingShape.clear();
						return;
					}
				}
			}
			zoneAddingShape.add((double) pressedX);
			zoneAddingShape.add((double) pressedY);
		} else {
			selectedShape = shapeAt(pressedX, pressedY);
			parentFormPanel.onObjectSelected();
		}

		System.out.print("selectedShape: " + selectedShape + "\n");
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (selectedShapeChanged) {
			// update shape (geometry) in sql db
			switch (selectedType) {
			case parkPlace:
				if (Zona.overlapByGeometry(objectsMap.get(selectedShape.getIdZony()).getZona())) {
					objectsMap.put(selectedShape.getIdZony(), ParkovaciMisto.getByZona(selectedShape));
					shapesMap.put(selectedShape.getIdZony(),
							ParkovaciMisto.getByZona(selectedShape).getJGeometry().createShape());
				} else {
					saveSelectedShape();
				}
				break;

			case entrance:
				if (Zona.overlapByGeometry(entranceObjectsMap.get(selectedShape.getIdZony()).getZona())) {
					entranceObjectsMap.put(selectedShape.getIdZony(), Vjezd.getByZona(selectedShape));
					entranceShapesMap.put(selectedShape.getIdZony(),
							Vjezd.getByZona(selectedShape).getJGeometry().createShape());
				} else {
					saveSelectedShape();
				}
				break;

			case exit:
				if (Zona.overlapByGeometry(exitObjectsMap.get(selectedShape.getIdZony()).getZona())) {
					exitObjectsMap.put(selectedShape.getIdZony(), Vyjezd.getByZona(selectedShape));
					exitShapesMap.put(selectedShape.getIdZony(),
							Vyjezd.getByZona(selectedShape).getJGeometry().createShape());
				} else {
					saveSelectedShape();
				}
				break;

			case zone:
				if (Zona.overlapByGeometry(objectsMap.get(selectedShape.getIdZony()).getZona())) {
					zoneObjectsMap.put(selectedShape.getIdZony(), selectedShape);
					zoneShapesMap.put(selectedShape.getIdZony(), selectedShape.getJGeoZony().createShape());
				} else {
					saveSelectedShape();
				}
				break;

			case nothing:
				break;
			}
			selectedShapeChanged = false;
			repaint();
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

		if (selectedShape != null) {
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

	private Zona shapeAt(int x, int y) {
		// create some bigger triangle and check "anyinteract" with sql
		// something like sql select on
		// SDO_RELATE(geometry, SDO_GEOMETRY(2003, NULL, NULL,
		// SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(p1, p2, p3)),
		// 'mask=anyinteract') = 'TRUE'")
		int size = 3;
		int p1x = x + size;
		int p1y = y - size;
		int p2x = x - size;
		int p2y = y + size;
		int p3x = x + size;
		int p3y = y + size;

		Zona idx = null;

		JGeometry geometry = JGeometry.createLinearPolygon(new double[] { p1x, p1y, p2x, p2y, p3x, p3y }, 2, 0);
		List<Zona> l = Zona.selectObjectsByGeometry(geometry);// todo get by priority
		if (l.isEmpty()) {
			selectedType = SpatialType.nothing;
			return null;
		}
		idx = l.get(0);
		Iterator<Zona> it = l.iterator();

		while (it.hasNext()) {
			Zona z = it.next();
			if (Vyjezd.getByZona(z) != null) {

				if (Vyjezd.getByZona(idx) != null) {

				} else if (Vjezd.getByZona(idx) != null) {

				} else if (ParkovaciMisto.getByZona(idx) != null) {
					idx = z;
				} else {
					// default zona
					idx = z;
				}

			} else if (Vjezd.getByZona(z) != null) {
				if (Vyjezd.getByZona(idx) != null) {

				} else if (Vjezd.getByZona(idx) != null) {

				} else if (ParkovaciMisto.getByZona(idx) != null) {
					idx = z;

				} else {
					// default zona
					idx = z;
				}

			} else if (ParkovaciMisto.getByZona(z) != null) {
				if (Vyjezd.getByZona(idx) != null) {

				} else if (Vjezd.getByZona(idx) != null) {

				} else if (ParkovaciMisto.getByZona(idx) != null) {

				} else {
					// default zona
					idx = z;
				}
			} else {
				// default zona

			}
		}

		// Zona zona = Zona.selectObjectByGeometry(geometry);

		/*
		 * (idx != null) { selectedType = SpatialType.parkPlace; return idx; } else { //
		 * seach entrance/exit (less priority) // todo - change idx =
		 * Vjezd.getByZona(zona); if (idx != null) { selectedType =
		 * SpatialType.entrance; return idx; }
		 * 
		 * // todo - change idx = Vyjezd.selectObjectByGeometry(geometry); if (idx !=
		 * null) { selectedType = SpatialType.exit; return idx; }
		 * 
		 * selectedType = SpatialType.nothing; return null; }
		 */
		ParkovaciMisto pm = ParkovaciMisto.getByZona(idx);
		Vjezd vj = Vjezd.getByZona(idx);
		Vyjezd vy = Vyjezd.getByZona(idx);
		if (pm != null) {
			selectedType = SpatialType.parkPlace;
			return idx;
		}
		if (vj != null) {
			selectedType = SpatialType.entrance;
			return idx;
		}
		if (vy != null) {
			selectedType = SpatialType.exit;
			return idx;
		}
		selectedType = SpatialType.zone;
		// selectedShape = idx;
		System.out.println("selectedType");
		System.out.println(selectedType);
		System.out.println(selectedShape);

		return idx;
	}

	private void moveSelectedShape(int deltaX, int deltaY) {
		// something like:
		// getGeometry(selectedShape) -> move geometry -> convert to shape -> update in
		// Map
		System.out.println("asd selectedShape");
		System.out.println(selectedShape);
		System.out.println(selectedType);
		System.out.println(selectedShape.getIdZony());
		switch (selectedType) {
		case parkPlace:
			ParkovaciMisto parkPlace = objectsMap.get(selectedShape.getIdZony());
			JGeometry parkPlacegeometry = parkPlace.getJGeometry();

			for (int i = 0; i < parkPlacegeometry.getOrdinatesArray().length; i = i + 2) {
				parkPlacegeometry.getOrdinatesArray()[i] = parkPlacegeometry.getOrdinatesArray()[i] + deltaX;
				parkPlacegeometry.getOrdinatesArray()[i + 1] = parkPlacegeometry.getOrdinatesArray()[i + 1] + deltaY;
			}
			parkPlace.getZona().setGeoZony(new JGeometryType(parkPlacegeometry));
			objectsMap.put(selectedShape.getIdZony(), parkPlace);
			shapesMap.put(selectedShape.getIdZony(), parkPlacegeometry.createShape());
			break;

		case entrance:

			Vjezd entrance = entranceObjectsMap.get(selectedShape.getIdZony());
			JGeometry entranceGeometry = entrance.getJGeometry();

			for (int i = 0; i < entranceGeometry.getOrdinatesArray().length; i = i + 2) {
				entranceGeometry.getOrdinatesArray()[i] = entranceGeometry.getOrdinatesArray()[i] + deltaX;
				entranceGeometry.getOrdinatesArray()[i + 1] = entranceGeometry.getOrdinatesArray()[i + 1] + deltaY;
			}

			entrance.getZona().setGeoZony(new JGeometryType(entranceGeometry));
			entranceObjectsMap.put(selectedShape.getIdZony(), entrance);
			entranceShapesMap.put(selectedShape.getIdZony(), entranceGeometry.createShape());
			break;

		case exit:
			Vyjezd exit = exitObjectsMap.get(selectedShape.getIdZony());
			JGeometry exitGeometry = exit.getJGeometry();

			for (int i = 0; i < exitGeometry.getOrdinatesArray().length; i = i + 2) {
				exitGeometry.getOrdinatesArray()[i] = exitGeometry.getOrdinatesArray()[i] + deltaX;
				exitGeometry.getOrdinatesArray()[i + 1] = exitGeometry.getOrdinatesArray()[i + 1] + deltaY;
			}

			// exit.setZona();
			exit.getZona().setGeoZony(new JGeometryType(exitGeometry));
			exitObjectsMap.put(selectedShape.getIdZony(), exit);
			exitShapesMap.put(selectedShape.getIdZony(), exitGeometry.createShape());
			break;

		case zone:
			Zona zone = zoneObjectsMap.get(selectedShape.getIdZony());
			JGeometry zoneGeometry = zone.getJGeoZony();

			for (int i = 0; i < zoneGeometry.getOrdinatesArray().length; i = i + 2) {
				zoneGeometry.getOrdinatesArray()[i] = zoneGeometry.getOrdinatesArray()[i] + deltaX;
				zoneGeometry.getOrdinatesArray()[i + 1] = zoneGeometry.getOrdinatesArray()[i + 1] + deltaY;
			}

			// exit.setZona();
			zone.setGeoZony(new JGeometryType(zoneGeometry));
			zoneObjectsMap.put(selectedShape.getIdZony(), zone);
			zoneShapesMap.put(selectedShape.getIdZony(), zoneGeometry.createShape());
			break;

		case nothing:
			break;
		}

		repaint();
		return;
	}

	private void loadObjects() throws Exception {
		try {
			// Query q = TableBase.getEntityManager().createQuery("SELECT p FROM
			// ParkovaciMisto p", ParkovaciMisto.class);

			List<Zona> zoneResultList = Zona.list();
			for (Zona result : zoneResultList) {
				System.out.print("result.getParkovaciMistos(): " + result.getParkovaciMistos() + "\n");
				System.out.print("result.getVjezds(): " + result.getVjezds() + "\n");
				System.out.print("result.getVyjezds(): " + result.getVyjezds() + "\n");
				if (result.getParkovaciMistos().isEmpty() && result.getVjezds().isEmpty()
						&& result.getVyjezds().isEmpty()) {
					zoneObjectsMap.put(result.getIdZony(), result);
					zoneShapesMap.put(result.getIdZony(), result.getGeoZony().getJGeometry().createShape());
					System.out.print("Zona result: " + result.getIdZony().toString() + ", geo: "
							+ result.getGeoZony().getJGeometry().toStringFull() + "\n");
				}
				/*
				 * objectsMap.put(result.getZona().getIdZony(), result);
				 * shapesMap.put(result.getZona().getIdZony(),
				 * result.getJGeometry().createShape());
				 * System.out.print("ParkovaciMisto result: " + result.getIdMista().toString() +
				 * ", geo: " + result.getJGeometry().toStringFull() + "\n");
				 */
			}

			List<ParkovaciMisto> parkPlaceresultList = ParkovaciMisto.list();
			for (ParkovaciMisto result : parkPlaceresultList) {
				objectsMap.put(result.getZona().getIdZony(), result);
				shapesMap.put(result.getZona().getIdZony(), result.getJGeometry().createShape());
				System.out.print("ParkovaciMisto result: " + result.getIdMista().toString() + ", geo: "
						+ result.getJGeometry().toStringFull() + "\n");
			}

			List<Vjezd> entranceResultList = Vjezd.list();
			for (Vjezd result : entranceResultList) {
				entranceObjectsMap.put(result.getZona().getIdZony(), result);
				entranceShapesMap.put(result.getZona().getIdZony(), result.getJGeometry().createShape());
				System.out.print("Vjezd result: " + result.getIdVjezd().toString() + ", geo: "
						+ result.getJGeometry().toStringFull() + "\n");
			}

			List<Vyjezd> exitResultList = Vyjezd.list();
			for (Vyjezd result : exitResultList) {
				exitObjectsMap.put(result.getZona().getIdZony(), result);
				exitShapesMap.put(result.getZona().getIdZony(), result.getJGeometry().createShape());
				System.out.print("Vyjezd result: " + result.getIdVyjezd().toString() + ", geo: "
						+ result.getJGeometry().toStringFull() + "\n");
			}
		} catch (Exception e) {
			System.out.print("loadObjects: " + e + "\n");
		}

	}

	private void saveSelectedShape() {
		// todo - remove
		// ParkovaciMisto p = (ParkovaciMisto)
		// TableBase.getEntityManager().find(ParkovaciMisto.class, selectedShape);
		// p.update(p.getIdMista(), p.getPozn(),
		// objectsMap.get(selectedShape).getZona(), p.getParkovanis());
		// ParkovaciMisto.selectObjectByGeometry(selectedShape);
		// selectedShape.update();
		// Zona.update(selectedShape.getIdZony(), selectedShape.getNazevZony(),
		// selectedShape.getJGeoZony());
		switch (selectedType) {
		case parkPlace:
			ParkovaciMisto parkPlace = objectsMap.get(selectedShape.getIdZony());
			ParkovaciMisto.update(parkPlace.getIdMista(), parkPlace.getPozn(), parkPlace.getZona(),
					parkPlace.getParkovanis());
			break;

		case entrance:
			Vjezd entrance = entranceObjectsMap.get(selectedShape.getIdZony());
			Vjezd.update(entrance.getIdVjezd(), entrance.getZona());
			break;

		case exit:
			Vyjezd exit = exitObjectsMap.get(selectedShape.getIdZony());
			Vyjezd.update(exit.getIdVyjezd(), exit.getZona());
			break;

		case zone:
			Zona zone = zoneObjectsMap.get(selectedShape.getIdZony());
			Zona.update(zone.getIdZony(), zone.getNazevZony(), zone.getJGeoZony());
			break;

		case nothing:
			break;
		}
	}

	public SpatialType getSelectedObjectType() {
		// something like: return objectsMap.get(selectedID);
		return selectedType;
	}

	public Long getSelectedObjectID() {
		// something like: return objectsMap.get(selectedID);
		return selectedShape.getIdZony();
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

	public Zona getZoneByID(Long id) {
		return zoneObjectsMap.get(id);
	}

	public void deleteSelected() {
		switch (selectedType) {
		case parkPlace:
			objectsMap.remove(selectedShape.getIdZony());
			shapesMap.remove(selectedShape.getIdZony());
			ParkovaciMisto.delete(ParkovaciMisto.getByZona(selectedShape).getIdMista());
			repaint();
			break;

		case entrance:
			entranceObjectsMap.remove(selectedShape.getIdZony());
			entranceShapesMap.remove(selectedShape.getIdZony());
			// Vjezd.delete(selectedShape.getIdZony());
			Vjezd.delete(Vjezd.getByZona(selectedShape).getIdVjezd());
			repaint();
			break;

		case exit:
			exitObjectsMap.remove(selectedShape.getIdZony());
			exitShapesMap.remove(selectedShape.getIdZony());
			// Vyjezd.delete(selectedShape.getIdZony());
			Vyjezd.delete(Vyjezd.getByZona(selectedShape).getIdVyjezd());
			break;

		case zone:
			zoneObjectsMap.remove(selectedShape.getIdZony());
			zoneShapesMap.remove(selectedShape.getIdZony());
			// Zona.delete(selectedShape.getIdZony());
			repaint();
			break;

		case nothing:
			return;
			//break;
		}
		Zona.delete(selectedShape.getIdZony());
		repaint();

		selectedShape = null;
		selectedType = SpatialType.nothing;
		parentFormPanel.onObjectSelected();
	}

	public void addParkPlace() {
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		JGeometry geo = JGeometry.createLinearPolygon(new double[] { middleX + 20, middleY + 10, middleX - 20,
				middleY + 10, middleX - 20, middleY - 10, middleX + 20, middleY - 10 }, 2, 0);
		ParkovaciMisto parkPlace = ParkovaciMisto.insert("Nové místo", Zona.insert("parkPlace", geo),
				Collections.emptySet());

		objectsMap.put(parkPlace.getZona().getIdZony(), parkPlace);
		shapesMap.put(parkPlace.getZona().getIdZony(), parkPlace.getJGeometry().createShape());

		repaint();
	}

	public void addEntrance() {
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		JGeometry geo = JGeometry
				.createLinearPolygon(new double[] { middleX + 10, middleY + 5, middleX - 10, middleY + 5, middleX - 15,
						middleY, middleX - 10, middleY - 5, middleX + 10, middleY - 5, middleX + 15, middleY }, 2, 0);
		Vjezd entrance = Vjezd.insert(Zona.insert("entrace", geo));

		entranceObjectsMap.put(entrance.getZona().getIdZony(), entrance);
		entranceShapesMap.put(entrance.getZona().getIdZony(), entrance.getJGeometry().createShape());

		repaint();
	}

	public void addExit() {
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		JGeometry geo = JGeometry
				.createLinearPolygon(new double[] { middleX + 10, middleY + 5, middleX - 10, middleY + 5, middleX - 15,
						middleY, middleX - 10, middleY - 5, middleX + 10, middleY - 5, middleX + 15, middleY }, 2, 0);
		Vyjezd exit = Vyjezd.insert(Zona.insert("exit", geo));

		exitObjectsMap.put(exit.getZona().getIdZony(), exit);
		exitShapesMap.put(exit.getZona().getIdZony(), exit.getJGeometry().createShape());

		repaint();
	}

	public void addZone() {
		zoneAdding = true;
		selectedShape = null;
		selectedType = SpatialType.nothing;
	}

}
