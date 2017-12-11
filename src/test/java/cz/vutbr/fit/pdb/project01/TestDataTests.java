package cz.vutbr.fit.pdb.project01;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import cz.vutbr.fit.pdb.project.tables.Zona;
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
public class TestDataTests extends TestCase {

	protected static Logger log = Logger.getLogger(TestDataTests.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TestDataTests(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TestDataTests.class);
	}

	// http://junit.sourceforge.net/junit3.8.1/javadoc/junit/framework/TestCase.html
	public void setUp() {
		log.info("Run this method before each test");
		TableBase.login(null, null, null);
	}

	/**
	 * @throws IOException
	 */
	public void testZonaSelectByIdTableCRUD() throws IOException {
		String testVjezdName = "testVjezdName";
		String testVjezdName2 = "testVjezdName2";

		int vjezdW = 20, vjezdH = 10;
		int vyjezdW = vjezdW, vyjezdH = vjezdH;
		int carW = 40, carH = 20;
		
		JGeometry geo = new JGeometry(10, 10, 10, 10, 1);
		JGeometry geo2 = JGeometry.createCircle(0, 0, 10, 1);
		List<JGeometry> carList = new ArrayList<JGeometry>();// List();//Collections.emptyList();
		// List<JGeometry> carList = Collections.emptyList();
		int PaddingTop = 50;
		int lineHeight = 70;
		int CONST = 70;
		for (int y = 1; y < 5; y++) {
			for (int i = 1; i < 5; i++) {
				double[] da = new double[8];
				da[0] = CONST * i;
				da[1] = PaddingTop;
				da[2] = CONST * i;
				da[3] = PaddingTop + carH;
				da[4] = CONST * i + carW;
				da[5] = PaddingTop + carH;
				da[6] = CONST * i + carW;
				da[7] = PaddingTop;

				carList.add(JGeometry.createLinearPolygon(da, 2, 0));
			}
			PaddingTop = PaddingTop + lineHeight;
		}

		carList.stream().forEach(i -> {
			ParkovaciMisto.insert("Pm.", Zona.insert("pm", i), Collections.emptySet());
		});

		String resourcesPath = "./";
		File pathToFile = new File(resourcesPath + "/resources/Hibernate_logo_a.png");
		Image image = ImageIO.read(pathToFile);

		List<Vozidlo> vozidlos = new ArrayList<Vozidlo>();
		
		for (int y = 1; y < 5; y++) {
			Vozidlo vozidlo = Vozidlo.insert("tSpzU" + y, image, image, Collections.emptySet());
			vozidlos.add(vozidlo);
		}
		
		

		// JGeometry car1 = JGeometry.createLinearPolygon({10,10, 40, 20}, 2, 0);

	}

}
