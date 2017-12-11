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
import cz.vutbr.fit.pdb.project.tables.Vozidlo;
import cz.vutbr.fit.pdb.project.tables.Vyjezd;
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
public class TableTests extends TestCase {

	protected static Logger log = Logger.getLogger(TableTests.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TableTests(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TableTests.class);
	}

	// http://junit.sourceforge.net/junit3.8.1/javadoc/junit/framework/TestCase.html
	public void setUp() {
		log.info("Run this method before each test");
		TableBase.login(null, null, null);
	}

	/**
	 * @throws IOException
	 */
	public void testParkovaciMistoAndSomeParkovaniTableCRUD() throws IOException {
		String testParkovaniName = "testParkovaniName";
		String testParkovaniName2 = "testParkovaniName2";
		Parkovani.list();
		log.info("listed\n\n");

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(20, 20, 10, 1);
		JGeometry geo3 = new JGeometry(35, 35, 5, 10, 1);

		Zona zonaGeo = Zona.insert("", geo);
		Zona zonaGeo2 = Zona.insert("", geo2);
		Zona zonaGeo3 = Zona.insert("", geo3);

		ParkovaciMisto parkovaciMisto = ParkovaciMisto.insert("wasted time", zonaGeo3, Collections.emptySet());

		String resourcesPath = "./";
		File pathToFile = new File(resourcesPath + "/resources/Hibernate_logo_a.png");
		Image image = ImageIO.read(pathToFile);

		Vozidlo vozidlo = Vozidlo.insert("tSpzU", image, image, Collections.emptySet());
		Vjezd vjezd = Vjezd.insert(zonaGeo);
		Vyjezd vyjezd = Vyjezd.insert(zonaGeo2);
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

		Zona.delete(zonaGeo.getIdZony());
		Zona.delete(zonaGeo2.getIdZony());
		Zona.delete(zonaGeo3.getIdZony());
	}

	/**
	 * @throws IOException
	 */
	public void testPobytTableCRUD() throws IOException {
		String testPobytName = "testPobytName";
		String testPobytName2 = "testPobytName2";

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(20, 20, 10, 1);

		Zona zonaGeo = Zona.insert("", geo);
		Zona zonaGeo2 = Zona.insert("", geo2);

		String resourcesPath = "./";
		File pathToFile = new File(resourcesPath + "/resources/Hibernate_logo_a.png");
		Image image = ImageIO.read(pathToFile);

		Vozidlo vozidlo = Vozidlo.insert("tSpzU2", image, image, Collections.emptySet());
		Vjezd vjezd = Vjezd.insert(zonaGeo);
		Vyjezd vyjezd = Vyjezd.insert(zonaGeo2);
		Date vjezd_1 = new Date();
		Date vyjezd_1 = new Date();
		Date vyjezd_1_2 = new Date(); // updating
		Set<Parkovani> parkovanis = Collections.emptySet();

		Pobyt.list();
		log.info("listed\n\n");

		Pobyt newPobyt = Pobyt.insert(vjezd, vozidlo, vyjezd, vjezd_1, vyjezd_1, parkovanis);
		assertTrue("New Pobyt with name " + testPobytName + " does not exists", Pobyt.list().contains(newPobyt));
		Pobyt newPobyt2 = Pobyt.update(newPobyt.getIdPobyt(), vjezd, vozidlo, vyjezd, vjezd_1, vyjezd_1_2, parkovanis);
		assertTrue("New Pobyt with name " + testPobytName2 + " should not be null but is", newPobyt2 != null);
		assertTrue("New Pobyt with original name " + testPobytName + " should be updatable with new date "
				+ testPobytName2 + " but is not", newPobyt2.getVyjezd_1().equals(newPobyt.getVyjezd_1()));
		assertTrue("New Pobyt with original name " + testPobytName + " should be deletable but is not",
				Pobyt.delete(newPobyt2.getIdPobyt()));
		assertTrue("New Pobyt with name " + testPobytName2 + " should be deleted but is not",
				!Pobyt.list().contains(newPobyt2));

		// clearing
		assertTrue("Old vjezd with id " + vjezd.getIdVjezd() + "should be removable", Vjezd.delete(vjezd.getIdVjezd()));
		assertTrue("Old vyjezd with id " + vyjezd.getIdVyjezd() + "should be removable",
				Vyjezd.delete(vyjezd.getIdVyjezd()));
		assertTrue("Old vozidlo with id " + vozidlo.getSpz() + "should be removable", Vozidlo.delete(vozidlo.getSpz()));

		Zona.delete(zonaGeo.getIdZony());
		Zona.delete(zonaGeo2.getIdZony());
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

		Zona zonaGeo = Zona.insert("", geo);
		Zona zonaGeo2 = Zona.insert("", geo2);

		Vjezd newVjezd = Vjezd.insert(zonaGeo);
		assertTrue("New Vjezd with name " + testVjezdName + " does not exists", Vjezd.list().contains(newVjezd));
		Vjezd newVjezd2 = Vjezd.update(newVjezd.getIdVjezd(), zonaGeo2);
		assertTrue("New Vjezd with name " + testVjezdName2 + " should not be null but is", newVjezd2 != null);
		assertTrue("New Vjezd with original name " + testVjezdName + " should be updatable with new name "
				+ testVjezdName2 + " but is not", newVjezd2.getIdVjezd().equals(newVjezd.getIdVjezd()));
		assertTrue("New Vjezd with original name " + testVjezdName + " should be deletable but is not",
				Vjezd.delete(newVjezd2.getIdVjezd()));
		assertTrue("New Vjezd with name " + testVjezdName2 + " should be deleted but is not",
				!Vjezd.list().contains(newVjezd2));

		Zona.delete(zonaGeo.getIdZony());
		Zona.delete(zonaGeo2.getIdZony());
	}

	/**
	 */
	public void testVyjezdTableCRUD() {
		String testVyjezdName = "testVyjezdName";
		String testVyjezdName2 = "testVyjezdName2";
		Vyjezd.list();

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(0, 0, 10, 1);

		Zona zonaGeo = Zona.insert("", geo);
		Zona zonaGeo2 = Zona.insert("", geo2);

		Vyjezd newVyjezd = Vyjezd.insert(zonaGeo);
		assertTrue("New Vyjezd with name " + testVyjezdName + " does not exists", Vyjezd.list().contains(newVyjezd));
		Vyjezd newVyjezd2 = Vyjezd.update(newVyjezd.getIdVyjezd(), zonaGeo2);
		assertTrue("New Vyjezd with name " + testVyjezdName2 + " should not be null but is", newVyjezd2 != null);
		assertTrue("New Vyjezd with original name " + testVyjezdName + " should be updatable with new name "
				+ testVyjezdName2 + " but is not", newVyjezd2.getIdVyjezd().equals(newVyjezd.getIdVyjezd()));
		assertTrue("New Vyjezd with original name " + testVyjezdName + " should be deletable but is not",
				Vyjezd.delete(newVyjezd2.getIdVyjezd()));
		assertTrue("New Vyjezd with name " + testVyjezdName2 + " should be deleted but is not",
				!Vyjezd.list().contains(newVyjezd2));

		Zona.delete(zonaGeo.getIdZony());
		Zona.delete(zonaGeo2.getIdZony());
	}

	/**
	 * duplicite with GeometryTest
	 */
	public void testZonaTableCRUD() {
		String testZonaName = "testZonaName";
		String testZonaName2 = "testZonaName2";
		Zona.list();

		JGeometry geo = new JGeometry(0, 0, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(0, 0, 10, 1);

		Zona newZona = Zona.insert(testZonaName, geo);
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

	private static BufferedImage toBufferedImage(Image src) {
		int w = src.getWidth(null);
		int h = src.getHeight(null);
		int type = BufferedImage.TYPE_INT_RGB; // other options
		BufferedImage dest = new BufferedImage(w, h, type);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(src, 0, 0, null);
		g2.dispose();
		return dest;
	}

	/**
	 * @throws IOException
	 */
	public void testVozidloTableCRUD() throws IOException {
		log.info("Loading image");
		String resourcesPath = "./";
		File pathToFile = new File(resourcesPath + "/resources/Hibernate_logo_a.png");
		Image image = ImageIO.read(pathToFile);
		String testVozidloSpz = "Spz";
		log.info("Creating new vizidlo");
		Vozidlo newVozidlo = Vozidlo.insert(testVozidloSpz, image, image, Collections.emptySet());
		assertTrue("New vozidlo with name " + testVozidloSpz + " does not exists and should",
				Vozidlo.list().contains(newVozidlo));

		log.info("Get new vozidlo with spz: " + testVozidloSpz);
		Vozidlo v = Vozidlo.list().stream().filter(p -> p.getSpz().equals(newVozidlo.getSpz())).findFirst().get();
		Image i = v.getFoto();
		String pathJpg = resourcesPath + "/resources/Hibernate_logo_a.2.jpg";
		File out = new File(pathJpg);
		out.deleteOnExit();
		ImageIO.write(toBufferedImage(i), "jpg", out);

		log.info("Update image for vozidlo with spz: " + testVozidloSpz);
		Vozidlo newVozidlo2 = Vozidlo.update(testVozidloSpz,
				toBufferedImage(ImageIO.read(out).getScaledInstance(50, 50, 20)), image, null);

		assertTrue("New vozidlo with name " + testVozidloSpz + " should not be null but is", newVozidlo2 != null);

		log.info("Save image of updated vozidlo with spz: " + testVozidloSpz);

		Vozidlo scaledv = Vozidlo.list().stream().filter(p -> p.getSpz().equals(newVozidlo2.getSpz())).findFirst()
				.get();
		Image scaledi = scaledv.getFoto();
		String scaledPathJpg = resourcesPath + "/resources/Hibernate_logo_a.scaled.jpg";
		File scaledOut = new File(scaledPathJpg);
		scaledOut.deleteOnExit();
		ImageIO.write(toBufferedImage(scaledi), "jpg", scaledOut);

		assertTrue("New vozidlo with original name " + testVozidloSpz + " should be deletable but is not",
				Vozidlo.delete(scaledv.getSpz()));
		assertTrue("New vozidlo with name " + testVozidloSpz + " should be deleted but is not",
				!Vozidlo.list().contains(scaledv));

		assertTrue("New vozidlo with name " + testVozidloSpz + " must have deletable image", out.delete());
		assertTrue("New vozidlo with name " + testVozidloSpz + " must have deletable scaled image", scaledOut.delete());
	}
}
