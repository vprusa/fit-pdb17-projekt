package cz.vutbr.fit.pdb.project.tables;
// Generated Dec 10, 2017 4:55:13 PM by Hibernate Tools 4.3.5.Final

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.entities.JGeometryType;
import oracle.spatial.geometry.JGeometry;

/**
 * ParkovaciMisto generated by hbm2java
 */
@Entity
@Table(name = "PARKOVACI_MISTO")
public class ParkovaciMisto extends TableBase implements java.io.Serializable {

	@Id
	@Column(name = "ID_MISTA", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parkovaci_misto_seq")
	@SequenceGenerator(name = "parkovaci_misto_seq", sequenceName = "parkovaci_misto_seq", allocationSize = 1, initialValue = 1)
	private Long idMista;
	@Column(name = "POZN")
	private String pozn;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZONA_ID", nullable = false)
	private Zona zona;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parkovaciMisto")
	private Set<Parkovani> parkovanis = new HashSet<Parkovani>(0);

	public ParkovaciMisto() {
	}

	public ParkovaciMisto(Long idMista) {
		this.idMista = idMista;
	}

	public ParkovaciMisto(Long idMista, String pozn, Zona zona, Set<Parkovani> parkovanis) {
		this.idMista = idMista;
		this.pozn = pozn;
		this.zona = zona;
		this.parkovanis = parkovanis;
	}

	public Long getIdMista() {
		return this.idMista;
	}

	public void setIdMista(Long idMista) {
		this.idMista = idMista;
	}

	public String getPozn() {
		return this.pozn;
	}

	public void setPozn(String pozn) {
		this.pozn = pozn;
	}

	public JGeometry getJGeometry() {
		return this.zona.getJGeoZony();
	}
	
	public Zona getZona() {
		return this.zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Set<Parkovani> getParkovanis() {
		return this.parkovanis;
	}

	public void setParkovanis(Set<Parkovani> parkovanis) {
		this.parkovanis = parkovanis;
	}

	public static ParkovaciMisto insert(String pozn, Zona zona, Set<Parkovani> parkovanis) {
		// Long idMista, String pozn, Zona zona, Set<Parkovani> parkovanis

		ParkovaciMisto ParkovaciMisto = new ParkovaciMisto();
		try {
			entityManager.getTransaction().begin();

			ParkovaciMisto.setPozn(pozn);
			ParkovaciMisto.setZona(zona);
			ParkovaciMisto.setParkovanis(parkovanis);

			log.info("\n\n\nSave");
			ParkovaciMisto = entityManager.merge(ParkovaciMisto);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return ParkovaciMisto;
	}

	public static List<ParkovaciMisto> list() {
		try {
			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			List<ParkovaciMisto> ParkovaciMistos = entityManager.createQuery("from ParkovaciMisto").getResultList();
			for (Iterator<ParkovaciMisto> iterator = ParkovaciMistos.iterator(); iterator.hasNext();) {
				ParkovaciMisto ParkovaciMisto = (ParkovaciMisto) iterator.next();
				System.out.println(ParkovaciMisto.getIdMista());
			}
			entityManager.getTransaction().commit();
			return ParkovaciMistos;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public static ParkovaciMisto update(Long ParkovaciMistoId, String pozn, Zona zona, Set<Parkovani> parkovanis) {
		log.info("ParkovaciMisto.update");
		try {
			Zona.updateOrInsert(zona.getIdZony(), zona.getNazevZony(), zona.getJGeoZony());
			entityManager.getTransaction().begin();
			ParkovaciMisto ParkovaciMisto = (ParkovaciMisto) entityManager.find(ParkovaciMisto.class, ParkovaciMistoId);
			if (ParkovaciMisto == null) {
				return null;
			}
			ParkovaciMisto.setPozn(pozn);
			ParkovaciMisto.setZona(zona);
			ParkovaciMisto.setParkovanis(parkovanis);
			entityManager.getTransaction().commit();
			log.info(ParkovaciMisto.getIdMista());
			return ParkovaciMisto;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}

	public static boolean delete(Long ParkovaciMistoId) {
		try {
			entityManager.getTransaction().begin();
			ParkovaciMisto ParkovaciMisto = (ParkovaciMisto) entityManager.find(ParkovaciMisto.class, ParkovaciMistoId);
			entityManager.remove(ParkovaciMisto);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ParkovaciMisto getByZona(Zona zona) {
		try {
			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			ParkovaciMisto parkovaciMistos = (ParkovaciMisto) entityManager
					.createQuery("from ParkovaciMisto where ZONA_ID = :id").setParameter("id", zona.getIdZony()).getSingleResult();
			entityManager.getTransaction().commit();
			return parkovaciMistos;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			//e.printStackTrace();
		}
		return null;
	}
	
	
/*
	public static ParkovaciMisto selectObjectByGeometry(JGeometry geometry) {
		try {
			entityManager.getTransaction().begin();
			Zona result = entityManager
					.createQuery("from Zona WHERE SDO_RELATE(zona, :geo, 'mask=anyinteract') = 'TRUE')", Zona.class)
					.setParameter("geo", geometry).getSingleResult();
			ParkovaciMisto resultPM = ParkovaciMisto.selectById(result.getIdZony());
			entityManager.getTransaction().commit();
			// TODO: simplyfy - too complicated
			return resultPM.getZona().getIdZony();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}*/

}
