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
import cz.vutbr.fit.pdb.project.tables.Vjezd;
import cz.vutbr.fit.pdb.project.tables.Vozidlo;
import cz.vutbr.fit.pdb.project.tables.Vyjezd;
import cz.vutbr.fit.pdb.project.tables.Vyjezd;
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
	public void testVyjezdTableCRUD() {
		String testVyjezdName = "testVyjezdName";
		String testVyjezdName2 = "testVyjezdName2";
		Vyjezd.list();
		log.info("listed\n\n");

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(0, 0, 10, 1);

		Vyjezd newVyjezd = Vyjezd.insert(geo);
		assertTrue("New Vyjezd with name " + testVyjezdName + " does not exists", Vyjezd.list().contains(newVyjezd));
		Vyjezd newVyjezd2 = Vyjezd.update(newVyjezd.getIdVyjezd(), geo2);
		assertTrue("New Vyjezd with name " + testVyjezdName2 + " should not be null but is", newVyjezd2 != null);
		assertTrue("New Vyjezd with original name " + testVyjezdName + " should be updatable with new name "
				+ testVyjezdName2 + " but is not", newVyjezd2.getIdVyjezd().equals(newVyjezd.getIdVyjezd()));
		assertTrue("New Vyjezd with original name " + testVyjezdName + " should be deletable but is not",
				Vyjezd.delete(newVyjezd2.getIdVyjezd()));
		assertTrue("New Vyjezd with name " + testVyjezdName2 + " should be deleted but is not",
				!Vyjezd.list().contains(newVyjezd2));
	}

}
