package cz.vutbr.fit.pdb.project01;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

import javax.imageio.ImageIO;

import org.jboss.logging.Logger;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.Vozidlo;
import cz.vutbr.fit.pdb.project.tables.Zona;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
//import oracle.jdbc.OracleResultSet;
//import oracle.ord.im.OrdImage;

/**
 * Unit test for simple App.
 */
public class TableImageTests extends TestCase {

	protected static Logger log = Logger.getLogger(TableImageTests.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TableImageTests(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TableImageTests.class);
	}

	/**
	 * @throws IOException
	 */
	public void testVozidloTableCRUD() throws IOException {

		// select the new ORDImage into a java proxy OrdImage object (imageProxy)
		// String rowSelectSQL = "select image from image_table where id = 1 for
		// update";
		// OracleResultSet rset = (OracleResultSet)stmt.executeQuery(rowSelectSQL);
		// rset.next();
		// OrdImage imageProxy = (OrdImage)rset.getORAData("image",
		// OrdImage.getORADataFactory());
		// rset.close();

		// OrdImage.getORADataFactory().

		// String testVozidloSpz = "Spz";
		// OrdImage oldSpzImg = new OrdImage();
		// OrdImage newSpzImg = new OrdImage();
		// OrdImage oldImg = new OrdImage();

		// Vozidlo newVozidlo = Vozidlo.save(testVozidloSpz, oldSpzImg, oldImg,
		// Collections.emptySet());
		// assertTrue("New vozidlo with name " + testVozidloSpz + " does not exists",
		// Vozidlo.list().contains(newVozidlo));

		log.info("Loading image");
		// File pathToFile = new File("./resources/Hibernate_logo_a.png.png");
		File pathToFile = new File(
				"/home/vprusa/workspace/school/MGR/1ZS/PDB/proj/demo/pdb-project-template-master-latest/fit-pdb17-projekt/resources/Hibernate_logo_a.png");

		Image image = ImageIO.read(pathToFile);
		String testVozidloSpz = "Spz";
		log.info("Creating new vizidlo");
		Vozidlo newVozidlo = Vozidlo.save(testVozidloSpz, image, image, Collections.emptySet());

		assertTrue("New vozidlo with name " + testVozidloSpz + " does not exists and should", Vozidlo.list().contains(newVozidlo));

		/*
		 * Zona newZona2 = Vozidlo.update(spz, foto, spzFoto, pobyts)(newVozidlo.get(),
		 * testVozidloSpz2); assertTrue("New zona with name " + testZonaName2 +
		 * " should not be null but is", newZona2 != null);
		 * assertTrue("New zona with original name " + testZonaName +
		 * " should be updatable with new name " + testZonaName2 + " but is not",
		 * newZona2.getNazevZony().equals(testZonaName2));
		 * assertTrue("New zona with original name " + testZonaName +
		 * " should be deletable but is not", Zona.delete(newZona2.getIdZony()));
		 * assertTrue("New zona with name " + testZonaName2 +
		 * " should be deleted but is not", !Zona.list().contains(newZona2));
		 */
	}

}
