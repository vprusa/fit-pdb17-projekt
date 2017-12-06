package cz.vutbr.fit.pdb.project01;

import org.jboss.logging.Logger;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.Zona;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

	String testZonaName = "testZonaName";
	String testZonaName2 = "testZonaName2";

	/**
	 */
	public void testZonaTableCRUD() {
		Zona newZona = Zona.saveZona(testZonaName);
		assertTrue("New zona with name " + testZonaName + " does not exists", Zona.listZona().contains(newZona));
		Zona newZona2 = Zona.updateZona(newZona.getIdZony(), testZonaName2);
		assertTrue("New zona with name " + testZonaName2 + " should not be null but is", newZona2 != null);
		assertTrue("New zona with original name " + testZonaName + " should be updatable with new name " + testZonaName2
				+ " but is not", newZona2.getNazevZony().equals(testZonaName2));
		assertTrue("New zona with original name " + testZonaName + " should be deletable but is not",
				Zona.deleteZona(newZona2.getIdZony()));
		assertTrue("New zona with name " + testZonaName2 + " should be deleted but is not",
				!Zona.listZona().contains(newZona2));
	}

}
