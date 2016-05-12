package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "caja")

public class Caja {

	public Caja() {

	}

	public Caja(String nombre, Double saldoInicial, Sucursal sucursal) {
		super();
		this.id = null;
		this.nombre = nombre;
		this.saldoInicial = saldoInicial;
		this.sucursal = sucursal;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;

	@Column(name = "saldoInicial", nullable = false, length = 15)
	private Double saldoInicial;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Sucursal sucursal;
	// idSucursal

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public Sucursal getSucrusal() {
		return sucursal;
	}

	public void setSucrusal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public String toString() {
		return "Caja [id=" + id + ",nombre=" + nombre + ", saldoInicial=" + saldoInicial + "]";
	}
}
