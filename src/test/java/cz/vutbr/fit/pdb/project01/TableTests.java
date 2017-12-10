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
	 */
	public void testVyjezdTableCRUD() {
		String testVyjezdName = "testVyjezdName";
		String testVyjezdName2 = "testVyjezdName2";
		Vyjezd.list();

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
