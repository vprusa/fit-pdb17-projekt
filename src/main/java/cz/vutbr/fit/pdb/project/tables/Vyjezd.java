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

/**
 * Vyjezd generated by hbm2java
 */
@Entity
@Table(name = "VYJEZD")
public class Vyjezd implements java.io.Serializable {

	private BigDecimal idVyjezd;
	private Set<Pobyt> pobyts = new HashSet<Pobyt>(0);

	public Vyjezd() {
	}

	public Vyjezd(BigDecimal idVyjezd) {
		this.idVyjezd = idVyjezd;
	}

	public Vyjezd(BigDecimal idVyjezd, Set<Pobyt> pobyts) {
		this.idVyjezd = idVyjezd;
		this.pobyts = pobyts;
	}

	@Id

	@Column(name = "ID_VYJEZD", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdVyjezd() {
		return this.idVyjezd;
	}

	public void setIdVyjezd(BigDecimal idVyjezd) {
		this.idVyjezd = idVyjezd;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vyjezd")
	public Set<Pobyt> getPobyts() {
		return this.pobyts;
	}

	public void setPobyts(Set<Pobyt> pobyts) {
		this.pobyts = pobyts;
	}

}