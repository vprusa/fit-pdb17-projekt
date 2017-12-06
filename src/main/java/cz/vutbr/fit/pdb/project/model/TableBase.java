package cz.vutbr.fit.pdb.project.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import cz.vutbr.fit.pdb.project.tables.Zona;

public abstract class TableBase<T> {

	protected static final String PERSISTENCE_UNIT_NAME = "project01";
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	protected static EntityManager entityManager = emf.createEntityManager();

	protected static Logger log = Logger.getLogger(TableBase.class);

}
