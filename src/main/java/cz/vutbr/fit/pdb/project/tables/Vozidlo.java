package cz.vutbr.fit.pdb.project.tables;
// Generated Nov 28, 2017 5:54:05 PM by Hibernate Tools 4.3.5.Final

import java.awt.Image;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import cz.vutbr.fit.pdb.project.model.TableBase;
import cz.vutbr.fit.pdb.project.tables.entities.ImageOrdImageUserType;

/**
 * Vozidlo generated by hbm2java
 * http://rpouiller.developpez.com/tutoriels/java/manipulation-images-stockees-blob-ou-ordimage-avec-hibernate-grace-usertype/
 * 
 */
@Entity
@Table(name = "VOZIDLO")
public class Vozidlo extends TableBase {

	private String spz;
	private Image foto;
	private Image spzFoto;
	private Set<Pobyt> pobyts = new HashSet<Pobyt>(0);

	public Vozidlo() {
	}

	public Vozidlo(String spz) {
		this.spz = spz;
	}

	public Vozidlo(String spz, Image foto, Image spzFoto) {
		this.spz = spz;
		this.foto = foto;
		this.spzFoto = spzFoto;
		this.pobyts = Collections.emptySet();
	}

	public Vozidlo(String spz, Image foto, Image spzFoto, Set<Pobyt> pobyts) {
		this.spz = spz;
		this.foto = foto;
		this.spzFoto = spzFoto;
		this.pobyts = pobyts;
	}

	@Id
	@Column(name = "SPZ", unique = true, nullable = false, length = 10)
	public String getSpz() {
		return this.spz;
	}

	public void setSpz(String spz) {
		this.spz = spz;
	}

	@Column(name = "FOTO")
	@Type(type = "imageOrdImage")
	public Image getFoto() {
		return this.foto;
	}

	public void setFoto(Image foto) {
		this.foto = foto;
	}

	@Column(name = "SPZ_FOTO")
	@Type(type = "imageOrdImage")
	public Image getSpzFoto() {
		return this.spzFoto;
	}

	public void setSpzFoto(Image spzFoto) {
		this.spzFoto = spzFoto;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vozidlo")
	public Set<Pobyt> getPobyts() {
		return this.pobyts;
	}

	public void setPobyts(Set<Pobyt> pobyts) {
		this.pobyts = pobyts;
	}

	public static Vozidlo insert(String spz, Image foto, Image spzFoto, Set<Pobyt> pobyts) {
		Vozidlo vozidlo = new Vozidlo(spz, foto, spzFoto, pobyts);
		try {
			entityManager.getTransaction().begin();
			vozidlo = entityManager.merge(vozidlo);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return vozidlo;
	}

	public static List<Vozidlo> list() {
		log.info("Vozidlo.list");
		try {
			entityManager.getTransaction().begin();
			// @SuppressWarnings("unchecked")
			List<Vozidlo> vozidlos = (List<Vozidlo>) entityManager.createQuery("from Vozidlo").getResultList();
			for (Iterator<Vozidlo> iterator = vozidlos.iterator(); iterator.hasNext();) {
				Vozidlo vozidlo = (Vozidlo) iterator.next();
				log.info("Vozidlo: SPZ: " + vozidlo.spz + " , Img: " + vozidlo.foto.toString());
			}
			entityManager.getTransaction().commit();
			return vozidlos;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public static Vozidlo update(String spz, Image foto, Image spzFoto, Set<Pobyt> pobyts) {
		log.info("Vozidlo.update");
		try {
			entityManager.getTransaction().begin();
			Vozidlo vozidlo = (Vozidlo) entityManager.find(Vozidlo.class, spz);
			if (vozidlo == null) {
				return null;
			}
			// if (newSpz != null)
			// vozidlo.setSpz(newSpz);
			if (pobyts != null)
				vozidlo.setPobyts(pobyts);
			if (foto != null)
				vozidlo.setFoto(foto);
			if (spzFoto != null)
				vozidlo.setSpzFoto(spzFoto);
			entityManager.getTransaction().commit();
			return vozidlo;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return null;
	}

	public static boolean delete(String spz) {
		try {
			entityManager.getTransaction().begin();
			Vozidlo Vozidlo = (Vozidlo) entityManager.find(Vozidlo.class, spz);
			entityManager.remove(Vozidlo);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
