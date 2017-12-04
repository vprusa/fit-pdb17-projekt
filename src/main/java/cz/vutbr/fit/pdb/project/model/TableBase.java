package cz.vutbr.fit.pdb.project.model;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

public class TableBase {

	protected static final String PERSISTENCE_UNIT_NAME = "project01";
	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	protected static EntityManager entityManager = emf.createEntityManager();

	protected static Logger log = Logger.getLogger(TableBase.class); 
	
}
