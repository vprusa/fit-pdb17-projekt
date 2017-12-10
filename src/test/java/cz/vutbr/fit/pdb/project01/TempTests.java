package cz.vutbr.fit.pdb.project01;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jboss.logging.Logger;
import org.junit.Before;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.ParkovaciMisto;
import cz.vutbr.fit.pdb.project.tables.Parkovani;
import cz.vutbr.fit.pdb.project.tables.Pobyt;
import cz.vutbr.fit.pdb.project.tables.Vjezd;
import cz.vutbr.fit.pdb.project.tables.Parkovani;
import cz.vutbr.fit.pdb.project.tables.Vozidlo;
import cz.vutbr.fit.pdb.project.tables.Vyjezd;
import cz.vutbr.fit.pdb.project.tables.Pobyt;
import cz.vutbr.fit.pdb.project.tables.Pobyt;
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
	 * @throws IOException
	 */
	public void testParkovaniTableCRUD() throws IOException {
		String testParkovaniName = "testParkovaniName";
		String testParkovaniName2 = "testParkovaniName2";
		Parkovani.list();
		log.info("listed\n\n");

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(20, 20, 10, 1);
		JGeometry geo3 = new JGeometry(35, 35, 5, 10, 1);

		ParkovaciMisto parkovaciMisto = ParkovaciMisto.insert("wasted time", geo3, Collections.emptySet());

		String resourcesPath = "./";
		File pathToFile = new File(resourcesPath + "/resources/Hibernate_logo_a.png");
		Image image = ImageIO.read(pathToFile);

		Vozidlo vozidlo = Vozidlo.insert("tSpzU", image, image, Collections.emptySet());
		Vjezd vjezd = Vjezd.insert(geo);
		Vyjezd vyjezd = Vyjezd.insert(geo2);
		Date vjezd_1 = new Date();
		Date vyjezd_1 = new Date();
		Set<Parkovani> parkovanis = Collections.emptySet();

		Pobyt pobyt = Pobyt.insert(vjezd, vozidlo, vyjezd, vjezd_1, vyjezd_1, parkovanis);
		Date zacatek = new Date();
		Date konec = new Date();
		Date konec2 = new Date(); // updating

		Parkovani newParkovani = Parkovani.insert(parkovaciMisto, pobyt, zacatek, konec);
		assertTrue("New Parkovani with name " + testParkovaniName + " does not exists",
				Parkovani.list().contains(newParkovani));
		Parkovani newParkovani2 = Parkovani.update(newParkovani.getIdParkovani(), parkovaciMisto, pobyt, zacatek,
				konec2);
		assertTrue("New Parkovani with name " + testParkovaniName2 + " should not be null but is",
				newParkovani2 != null);
		assertTrue(
				"New Parkovani with original name " + testParkovaniName + " should be updatable with new name "
						+ testParkovaniName2 + " but is not",
				newParkovani2.getIdParkovani().equals(newParkovani.getIdParkovani()));
		assertTrue("New Parkovani with original name " + testParkovaniName + " should be deletable but is not",
				Parkovani.delete(newParkovani2.getIdParkovani()));
		assertTrue("New Parkovani with name " + testParkovaniName2 + " should be deleted but is not",
				!Parkovani.list().contains(newParkovani2));

		// clearing
		assertTrue("Old pobyt with id " + pobyt.getIdPobyt() + "should be removable", Pobyt.delete(pobyt.getIdPobyt()));
		assertTrue("Old vjezd with id " + vjezd.getIdVjezd() + "should be removable", Vjezd.delete(vjezd.getIdVjezd()));
		assertTrue("Old vyjezd with id " + vyjezd.getIdVyjezd() + "should be removable",
				Vyjezd.delete(vyjezd.getIdVyjezd()));
		assertTrue("Old vozidlo with id " + vozidlo.getSpz() + "should be removable", Vozidlo.delete(vozidlo.getSpz()));

	}

}
