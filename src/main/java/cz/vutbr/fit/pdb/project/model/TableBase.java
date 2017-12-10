package cz.vutbr.fit.pdb.project.model;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

public class TableBase {

	// https://stackoverflow.com/questions/19773258/how-to-modify-properties-after-create-entity-manager-factory-from-persistence-xm

	// NOTE: instances are created at runtime in method login
	protected final static String PERSISTENCE_UNIT_NAME = "project01";
	protected static Properties props = new Properties();
	protected static EntityManagerFactory emf;
	protected static EntityManager entityManager;

	protected static Logger log = Logger.getLogger(TableBase.class);

	public static boolean login(String url, String user, String pass) {
		try {
			// https://stackoverflow.com/questions/19773258/how-to-modify-properties-after-create-entity-manager-factory-from-persistence-xm
			// if null then default in hibernate.cfg.xml

			if (url == null) {
				String Durl = System.getProperty("url");
				if (Durl != null) {
					url = Durl;
				}

			}
			if (user == null) {
				String Dusername = System.getProperty("username");
				if (Dusername != null) {
					user = Dusername;
				}
			}
			if (pass == null) {
				String Dpassword = System.getProperty("password");
				if (Dpassword != null) {
					pass = Dpassword;
				}
			}

			if (url != null) {
				TableBase.props.setProperty("hibernate.connection.url", url);
			}
			if (user != null) {
				TableBase.props.setProperty("hibernate.connection.username", user);
			}
			if (user != null) {
				TableBase.props.setProperty("hibernate.default_catalog", user.toUpperCase());
			}
			if (pass != null) {
				TableBase.props.setProperty("hibernate.connection.password", pass);
			}

			TableBase.emf = Persistence.createEntityManagerFactory(TableBase.PERSISTENCE_UNIT_NAME, TableBase.props);
			TableBase.entityManager = TableBase.emf.createEntityManager();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static EntityManager getEntityManager() {
		return entityManager;
	}

}
