package cz.vutbr.fit.pdb.project.tables;
// Generated Nov 28, 2017 5:54:05 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import oracle.spatial.geometry.JGeometry;

/**
 * ParkovaciMisto generated by hbm2java
 */
@Entity
@Table(name = "PARKOVACI_MISTO")
public class ParkovaciMisto implements java.io.Serializable {

	private BigDecimal idMista;
	private String pozn;
	private Set<Parkovani> parkovanis = new HashSet<Parkovani>(0);

	public ParkovaciMisto() {
	}

	public ParkovaciMisto(BigDecimal idMista) {
		this.idMista = idMista;
	}

	public ParkovaciMisto(BigDecimal idMista, String pozn, Set<Parkovani> parkovanis) {
		this.idMista = idMista;
		this.pozn = pozn;
		this.parkovanis = parkovanis;
	}

	@Id

	@Column(name = "ID_MISTA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdMista() {
		return this.idMista;
	}

	public void setIdMista(BigDecimal idMista) {
		this.idMista = idMista;
	}

	@Column(name = "POZN")
	public String getPozn() {
		return this.pozn;
	}

	public void setPozn(String pozn) {
		this.pozn = pozn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parkovaciMisto")
	public Set<Parkovani> getParkovanis() {
		return this.parkovanis;
	}

	public void setParkovanis(Set<Parkovani> parkovanis) {
		this.parkovanis = parkovanis;
	}

}
