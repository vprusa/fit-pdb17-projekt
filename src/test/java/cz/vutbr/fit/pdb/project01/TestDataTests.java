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

		int vjezdW = 20, vjezdH = 10;
		int vyjezdW = vjezdW, vyjezdH = vjezdH;
		int carW = 40, carH = 20;

		int recordsCount = 5;

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

		double middleX = 50;
		double middleY = 30;
		JGeometry vjezd1Geom = JGeometry
				.createLinearPolygon(new double[] { middleX + 10, middleY + 5, middleX - 10, middleY + 5, middleX - 15,
						middleY, middleX - 10, middleY - 5, middleX + 10, middleY - 5, middleX + 15, middleY }, 2, 0);

		Vjezd vjezd1 = Vjezd.insert(Zona.insert("vjezd1", vjezd1Geom));

		middleX = 200;
		middleY = 30;
		JGeometry vjezd2Geom = JGeometry
				.createLinearPolygon(new double[] { middleX + 10, middleY + 5, middleX - 10, middleY + 5, middleX - 15,
						middleY, middleX - 10, middleY - 5, middleX + 10, middleY - 5, middleX + 15, middleY }, 2, 0);

		// JGeometry vjezd2Geom = JGeometry.createCircle(200, 30, 10, 0);
		Vjezd vjezd2 = Vjezd.insert(Zona.insert("vjezd2", vjezd2Geom));

		middleX = 350;
		middleY = 30;
		JGeometry vyjezd1Geom = JGeometry
				.createLinearPolygon(new double[] { middleX + 10, middleY + 5, middleX - 10, middleY + 5, middleX - 15,
						middleY, middleX - 10, middleY - 5, middleX + 10, middleY - 5, middleX + 15, middleY }, 2, 0);

		// JGeometry vyjezd1Geom = JGeometry.createCircle(350, 30, 10, 0);
		Vyjezd vyjezd1 = Vyjezd.insert(Zona.insert("vyjezd1", vyjezd1Geom));

		List<ParkovaciMisto> parkovaciMistos = new ArrayList<ParkovaciMisto>();
		carList.stream().forEach(i -> {
			ParkovaciMisto parkovaciMisto = ParkovaciMisto.insert("pm", Zona.insert("pm", i), Collections.emptySet());
			parkovaciMistos.add(parkovaciMisto);
		});

		String resourcesPath = "./";
		File pathToFile = new File(resourcesPath + "/resources/Hibernate_logo_a.png");
		Image image = ImageIO.read(pathToFile);

		List<Vozidlo> vozidlos = new ArrayList<Vozidlo>();

		for (int y = 1; y < recordsCount; y++) {
			Vozidlo vozidlo = Vozidlo.insert("tSpzU" + y, image, image, Collections.emptySet());
			vozidlos.add(vozidlo);
		}

		List<Pobyt> pobyts = new ArrayList<Pobyt>();

		for (int y = 1; y < recordsCount; y++) {
			Pobyt pobyt = Pobyt.insert(vjezd1, vozidlos.get(y - 1), vyjezd1, new Date(), new Date(),
					Collections.emptySet());
			pobyts.add(pobyt);
		}

		List<Parkovani> parkovanis = new ArrayList<Parkovani>();
		for (int y = 1; y < recordsCount; y++) {
			Parkovani parkovani = Parkovani.insert(parkovaciMistos.get(y - 1), pobyts.get(y - 1), new Date(),
					new Date());
			parkovanis.add(parkovani);
		}

	}

}
