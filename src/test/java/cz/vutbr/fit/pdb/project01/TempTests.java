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
import cz.vutbr.fit.pdb.project.tables.Vjezd;
import cz.vutbr.fit.pdb.project.tables.Vjezd;
import junit.framework.Test;
import junit.framework.TestCase;

import junit.framework.TestSuite;
import oracle.jdbc.OracleResultSet;
import oracle.ord.im.OrdImage;
import oracle.spatial.geometry.JGeometry;

/**
 * Unit test for simple App.
 */
public class TempTests extends TestCase {

	protected static Logger log = Logger.getLogger(TempTests.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TempTests(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TempTests.class);
	}

	// http://junit.sourceforge.net/junit3.8.1/javadoc/junit/framework/TestCase.html
	public void setUp() {
		log.info("Run this method before each test");
		TableBase.login(null, null, null);
	}

	/**
	 */
	public void testVjezdTableCRUD() {
		String testVjezdName = "testVjezdName";
		String testVjezdName2 = "testVjezdName2";
		Vjezd.list();
		log.info("listed\n\n");

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(0, 0, 10, 1);

		Vjezd newVjezd = Vjezd.insert(geo);
		assertTrue("New Vjezd with name " + testVjezdName + " does not exists", Vjezd.list().contains(newVjezd));
		Vjezd newVjezd2 = Vjezd.update(newVjezd.getIdVjezd(), geo2);
		assertTrue("New Vjezd with name " + testVjezdName2 + " should not be null but is", newVjezd2 != null);
		assertTrue("New Vjezd with original name " + testVjezdName + " should be updatable with new name "
				+ testVjezdName2 + " but is not", newVjezd2.getIdVjezd().equals(newVjezd.getIdVjezd()));
		assertTrue("New Vjezd with original name " + testVjezdName + " should be deletable but is not",
				Vjezd.delete(newVjezd2.getIdVjezd()));
		assertTrue("New Vjezd with name " + testVjezdName2 + " should be deleted but is not",
				!Vjezd.list().contains(newVjezd2));
	}

}
