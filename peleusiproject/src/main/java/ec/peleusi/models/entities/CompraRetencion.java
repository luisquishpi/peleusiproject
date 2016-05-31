package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "compraRetencion")

public class CompraRetencion {
	
	
	public CompraRetencion() {
		super();
	}

	public CompraRetencion(Compra compra, TipoRetencion tipoRetencion, Double porcentajeRetencion,
			Double montoRetencion, String establecimiento, String puntoEmision, String secuencial,
			String numeroAutorizacion, Date fechaRegistro, Double baseImponible) {
		super();
		this.id=null;
		this.compra = compra;
		this.tipoRetencion = tipoRetencion;
		this.porcentajeRetencion = porcentajeRetencion;
		this.montoRetencion = montoRetencion;
		this.establecimiento = establecimiento;
		this.puntoEmision = puntoEmision;
		this.secuencial = secuencial;
		this.numeroAutorizacion = numeroAutorizacion;
		this.fechaRegistro = fechaRegistro;
		this.baseImponible = baseImponible;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Compra compra;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private TipoRetencion tipoRetencion;
	
	@Column(name = "porcenteRetencion", nullable = false)
	private Double porcentajeRetencion = 0.0;
	
	@Column(name = "montoRetencion", nullable = false)
	private Double montoRetencion = 0.0;

	@Column(name = "establecimiento",  nullable = true, length = 3)
	private String establecimiento;

	@Column(name = "puntoEmision",  nullable = true, length = 3)
	private String puntoEmision;
	
	@Column(name = "secuencial",  nullable = true, length = 30)
	private String secuencial;
	
	@Column(name = "numeroAutorizacion",  nullable = true, length = 60)
	private String numeroAutorizacion;
	
	@Column(name = "fechaRegistro",  nullable = true)
	private Date fechaRegistro;
	
	@Column(name = "baseImponible", nullable = false)
	private Double baseImponible = 0.0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public TipoRetencion getTipoRetencion() {
		return tipoRetencion;
	}

	public void setTipoRetencion(TipoRetencion tipoRetencion) {
		this.tipoRetencion = tipoRetencion;
	}

	public Double getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	public void setPorcentajeRetencion(Double porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	public Double getMontoRetencion() {
		return montoRetencion;
	}

	public void setMontoRetencion(Double montoRetencion) {
		this.montoRetencion = montoRetencion;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getPuntoEmision() {
		return puntoEmision;
	}

	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}

	public String getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Double getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(Double baseImponible) {
		this.baseImponible = baseImponible;
	}	
	
}
