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
@Table(name = "compras")
public class Compra {
	

	public Compra() {
		super();
	}

	public Compra(Proveedor proveedor, Date fechaEmision, Date fechaRegistro, Date fechaAutorizacion,
			String establecimiento, String puntoEmision, String secuencial, String numeroAutorizacion,
			Double baseImponibleIva0, Double baseImponibleIvaDiferente0, Double baseImponibleNoObjetoIva,
			Double baseImponibleExentoIva, Double montoIva, Double montoIce, Date fechaVencimiento, Double diasCredito,
			Double valorDescuento, Usuario usuario, Boolean estado, Sucursal sucursal) {
		super();
		this.id=null;
		this.proveedor = proveedor;
		this.fechaEmision = fechaEmision;
		this.fechaRegistro = fechaRegistro;
		this.fechaAutorizacion = fechaAutorizacion;
		this.establecimiento = establecimiento;
		this.puntoEmision = puntoEmision;
		this.secuencial = secuencial;
		this.numeroAutorizacion = numeroAutorizacion;
		this.baseImponibleIva0 = baseImponibleIva0;
		this.baseImponibleIvaDiferente0 = baseImponibleIvaDiferente0;
		this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
		this.baseImponibleExentoIva = baseImponibleExentoIva;
		this.montoIva = montoIva;
		this.montoIce = montoIce;
		this.fechaVencimiento = fechaVencimiento;
		this.diasCredito = diasCredito;
		this.valorDescuento = valorDescuento;
		this.usuario = usuario;
		this.estado = estado;
		this.sucursal = sucursal;

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Proveedor proveedor;

	@Column(name = "fechaEmision",  nullable = true)
	private Date fechaEmision;
	
	@Column(name = "fechaRegistro",  nullable = true)
	private Date fechaRegistro;
	
	@Column(name = "fechaAutorizacion",  nullable = true)
	private Date fechaAutorizacion;
	
	@Column(name = "establecimiento",  nullable = true, length = 3)
	private String establecimiento;

	@Column(name = "puntoEmision",  nullable = true, length = 3)
	private String puntoEmision;
	
	@Column(name = "secuencial",  nullable = true, length = 30)
	private String secuencial;
	
	@Column(name = "numeroAutorizacion",  nullable = true, length = 60)
	private String numeroAutorizacion;
	
	@Column(name = "baseImponibleIva0", nullable = false)
	private Double baseImponibleIva0 = 0.0;
	
	@Column(name = "baseImponibleIvaDiferente0", nullable = false)
	private Double baseImponibleIvaDiferente0 = 0.0;
	
	@Column(name = "baseImponibleNoObjetoIva", nullable = false)
	private Double baseImponibleNoObjetoIva = 0.0;
	
	@Column(name = "baseImponibleExentoIva", nullable = false)
	private Double baseImponibleExentoIva = 0.0;
	
	@Column(name = "montoIva", nullable = false)
	private Double montoIva = 0.0;
	
	@Column(name = "montoIce", nullable = false)
	private Double montoIce = 0.0;
	
	@Column(name = "fechaVencimiento",  nullable = true)
	private Date fechaVencimiento;
	
	@Column(name = "diasCredito", nullable = false)
	private Double diasCredito = 0.0;
	
	@Column(name = "valorDescuento", nullable = false)
	private Double valorDescuento = 0.0;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Usuario usuario;
	
	@Column(name = "estado", nullable = false)
	private Boolean estado = false;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Sucursal sucursal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
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

	public Double getBaseImponibleIva0() {
		return baseImponibleIva0;
	}

	public void setBaseImponibleIva0(Double baseImponibleIva0) {
		this.baseImponibleIva0 = baseImponibleIva0;
	}

	public Double getBaseImponibleIvaDiferente0() {
		return baseImponibleIvaDiferente0;
	}

	public void setBaseImponibleIvaDiferente0(Double baseImponibleIvaDiferente0) {
		this.baseImponibleIvaDiferente0 = baseImponibleIvaDiferente0;
	}

	public Double getBaseImponibleNoObjetoIva() {
		return baseImponibleNoObjetoIva;
	}

	public void setBaseImponibleNoObjetoIva(Double baseImponibleNoObjetoIva) {
		this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
	}

	public Double getBaseImponibleExentoIva() {
		return baseImponibleExentoIva;
	}

	public void setBaseImponibleExentoIva(Double baseImponibleExentoIva) {
		this.baseImponibleExentoIva = baseImponibleExentoIva;
	}

	public Double getMontoIva() {
		return montoIva;
	}

	public void setMontoIva(Double montoIva) {
		this.montoIva = montoIva;
	}

	public Double getMontoIce() {
		return montoIce;
	}

	public void setMontoIce(Double montoIce) {
		this.montoIce = montoIce;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Double getDiasCredito() {
		return diasCredito;
	}

	public void setDiasCredito(Double diasCredito) {
		this.diasCredito = diasCredito;
	}

	public Double getValorDescuento() {
		return valorDescuento;
	}

	public void setValorDescuento(Double valorDescuento) {
		this.valorDescuento = valorDescuento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}	
	
}
