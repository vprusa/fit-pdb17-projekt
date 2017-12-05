package cz.vutbr.fit.pdb.project.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import cz.vutbr.fit.pdb.project.tables.Zona;

/**
 * considering that every crud table has its unique id
 */
public interface TableBaseInterface<T> {
	/*
	 * class TableInt{
	 * 
	 * }
	 * 
	 * static final String PERSISTENCE_UNIT_NAME = "project01"; static
	 * EntityManagerFactory emf =
	 * Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); static
	 * EntityManager entityManager = emf.createEntityManager();
	 * 
	 * static Logger log = Logger.getLogger(TableBase.class);
	 * 
	 * static TableBaseInterface<TableInt> create(TableBaseInterface c,Object...
	 * data) { TableBaseInterface zona = c.getClass().newInstance();
	 * 
	 * try {l entityManager.getTransaction().begin();
	 * zona.setNazevZony(c.getClass().getFields().); zona =
	 * entityManager.merge(zona); entityManager.getTransaction().commit(); } catch
	 * (Exception e) { entityManager.getTransaction().rollback();
	 * e.printStackTrace(); } return zona; }
	 * 
	 * T read(Object... data);
	 * 
	 * T update(long id, Object... data);
	 * 
	 * T delete(long id);
	 */
}
