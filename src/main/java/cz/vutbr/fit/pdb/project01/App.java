package cz.vutbr.fit.pdb.project01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import cz.vutbr.fit.pdb.project.tables.Zona;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Hello world!
 *https://www.boraji.com/hibernate-5-jpa-2-configuration-example
 *https://docs.jboss.org/hibernate/orm/4.0/quickstart/en-US/html/hibernate-gsg-tutorial-jpa.html
 *https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html
 *https://github.com/hibernate/hibernate-commons-annotations
 *http://www.deepakgaikwad.net/index.php/2009/04/02/jpa-hibernate-with-oracle-on-eclipse.html
 *http://www.javawebtutor.com/articles/maven/maven_hibernate_example.php
 *https://examples.javacodegeeks.com/enterprise-java/hibernate/eclipse-hibernate-tools-plugin-tutorial/
 *http://www.javawebtutor.com/articles/maven/maven_hibernate_example.php
 *
 *
 */
public class App {

	private static final String PERSISTENCE_UNIT_NAME = "project01";
	EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	// private static EntityManagerFactory factory = HibernatePersistenceProvider
	// .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	// private static PersistenceProvider provider = new
	// HibernatePersistenceProvider();
	// EntityManagerFactory emf =
	// provider.createEntityManagerFactory(PERSISTENCE_UNIT, props);

	private EntityManager entityManager = emf.createEntityManager();

	public Zona saveZona(String ZonaName) {
		Zona zona = new Zona();
		try {
			entityManager.getTransaction().begin();
			zona.setNazevZony(ZonaName);
			zona  = entityManager.merge(zona);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return zona;
	}

	public void listZona() {
		try {
			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			List<Zona> Zonas = entityManager.createQuery("from Zona").getResultList();
			for (Iterator<Zona> iterator = Zonas.iterator(); iterator.hasNext();) {
				Zona Zona = (Zona) iterator.next();
				System.out.println(Zona.getNazevZony());
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	public void updateZona(Long ZonaId, String ZonaName) {
		try {
			entityManager.getTransaction().begin();
			Zona Zona = (Zona) entityManager.find(Zona.class, ZonaId);
			Zona.setNazevZony(ZonaName);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	public void deleteZona(Long ZonaId) {
		try {
			entityManager.getTransaction().begin();
			Zona Zona = (Zona) entityManager.find(Zona.class, ZonaId);
			entityManager.remove(Zona);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("*** Oracle driver information ***");
		OracleDriver.main(args);
		System.out.println("*** Connecting to the Oracle db. and running a simple query ***");

		App example = new App();
		System.out.println("After Sucessfully insertion ");
		Zona student1 = example.saveZona("Sumith2");
	/*	Zona student2 = example.saveZona("Anoop");
		example.listZona();
		System.out.println("After Sucessfully modification ");
		example.updateZona(student1.getid_zony(), "Sumith Honai");
		example.updateZona(student2.getid_zony(), "Anoop Pavanai");
		example.listZona();
		System.out.println("After Sucessfully deletion ");
		example.deleteZona(student2.getid_zony());
		*/
		example.listZona();

	
	}
}
