package cz.vutbr.fit.pdb.project01;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.jboss.logging.Logger;
import org.junit.Before;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.Vozidlo;
import cz.vutbr.fit.pdb.project.tables.Zona;
import junit.framework.Test;
import junit.framework.TestCase;

import junit.framework.TestSuite;
import oracle.jdbc.OracleResultSet;
import oracle.ord.im.OrdImage;
import oracle.spatial.geometry.JGeometry;

/**
 * Unit test for simple App.
 */
public class GeometryTests extends TestCase {

	protected static Logger log = Logger.getLogger(GeometryTests.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public GeometryTests(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GeometryTests.class);
	}

	// http://junit.sourceforge.net/junit3.8.1/javadoc/junit/framework/TestCase.html
	public void setUp() {
		log.info("Run this method before each test");
		TableBase.login(null, null, null);
	}

	/**
	 */
	public void testZonaTableCRUD() {
		String testZonaName = "testZonaName";
		String testZonaName2 = "testZonaName2";
		Zona.list();
		log.info("listed\n\n");

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(0, 0, 10, 1);

		Zona newZona = Zona.save(testZonaName, geo);
		assertTrue("New zona with name " + testZonaName + " does not exists", Zona.list().contains(newZona));
		Zona newZona2 = Zona.update(newZona.getIdZony(), testZonaName2, geo2);
		assertTrue("New zona with name " + testZonaName2 + " should not be null but is", newZona2 != null);
		assertTrue("New zona with original name " + testZonaName + " should be updatable with new name " + testZonaName2
				+ " but is not", newZona2.getNazevZony().equals(testZonaName2));
		assertTrue("New zona with original name " + testZonaName + " should be deletable but is not",
				Zona.delete(newZona2.getIdZony()));
		assertTrue("New zona with name " + testZonaName2 + " should be deleted but is not",
				!Zona.list().contains(newZona2));
	}

}
