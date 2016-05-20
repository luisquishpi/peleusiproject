package ec.peleusi.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.Date;

@Entity
@Table(name = "ventaproducto")

public class VentaProducto {

	public VentaProducto() {
	}

	public VentaProducto(String tipoDocumento, Cliente cliente, Date fechaEmision, Date fechaAutorizacion,
			String establecimiento, String puntoEmision, String secuencial, String numeroAutorizacion,
			Double baseImponibleIva, Double baseImponibleIvaDiferente0, Double baseImponibleNoObjetoIva,
			Double baseImponibleExentoIva, Double montoIva, Double montoIce, Date fechaVencimiento, Double diasCredito,
			Integer valorDescuento, String estado, Usuario usuario, Caja caja, String guiaRemision) {
		super();
		this.id = null;
		this.tipoDocumento = tipoDocumento;
		this.cliente = cliente;
		this.fechaEmision = fechaEmision;
		this.fechaAutorizacion = fechaAutorizacion;
		this.establecimiento = establecimiento;
		this.puntoEmision = puntoEmision;
		this.secuencial = secuencial;
		this.numeroAutorizacion = numeroAutorizacion;
		this.baseImponibleIva = baseImponibleIva;
		this.baseImponibleIvaDiferente0 = baseImponibleIvaDiferente0;
		this.baseImponibleNoObjetoIva = baseImponibleNoObjetoIva;
		this.baseImponibleExentoIva = baseImponibleExentoIva;
		this.montoIva = montoIva;
		this.montoIce = montoIce;
		this.fechaVencimiento = fechaVencimiento;
		this.diasCredito = diasCredito;
		this.valorDescuento = valorDescuento;
		this.estado = estado;
		this.usuario = usuario;
		this.caja = caja;
		this.guiaRemision = guiaRemision;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "tipoDocumento", nullable = false, length = 50)
	private String tipoDocumento;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Cliente cliente;
	// idCliente

	@Column(name = "fechaEmision", nullable = false)
	private Date fechaEmision;

	@Column(name = "fechaAutorizacion", nullable = false)
	private Date fechaAutorizacion;

	@Column(name = "establecimiento", nullable = false, length = 3)
	private String establecimiento;

	@Column(name = "puntoEmision", nullable = false, length = 3)
	private String puntoEmision;

	@Column(name = "secuencial", nullable = false, length = 30)
	private String secuencial;

	@Column(name = "numeroAutorizacion", nullable = false, length = 50)
	private String numeroAutorizacion;

	@Column(name = "baseImponibleIva0", nullable = false, length = 60)
	private Double baseImponibleIva;

	@Column(name = "baseImponibleIvaDiferente0", nullable = false)
	private Double baseImponibleIvaDiferente0;

	@Column(name = "baseImponibleNoObjetoIva", nullable = false)
	private Double baseImponibleNoObjetoIva;

	@Column(name = "baseImponibleExentoIva", nullable = false)
	private Double baseImponibleExentoIva;

	@Column(name = "montoIva", nullable = false)
	private Double montoIva;

	@Column(name = "montoIce", nullable = false)
	private Double montoIce;

	@Column(name = "fechaVencimiento", nullable = false)
	private Date fechaVencimiento;

	@Column(name = "diasCredito", nullable = false)
	private Double diasCredito;

	@Column(name = "valorDescuento", nullable = false)
	private Integer valorDescuento;

	@Column(name = "estado", nullable = false)
	private String estado;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Usuario usuario;
	// idUsuario

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Caja caja;
	// idCaja

	@Column(name = "guiaRemision", nullable = false)
	private String guiaRemision;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
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

	public Double getBaseImponibleIva() {
		return baseImponibleIva;
	}

	public void setBaseImponibleIva(Double baseImponibleIva) {
		this.baseImponibleIva = baseImponibleIva;
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

	public Integer getValorDescuento() {
		return valorDescuento;
	}

	public void setValorDescuento(Integer valorDescuento) {
		this.valorDescuento = valorDescuento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public String getGuiaRemision() {
		return guiaRemision;
	}

	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}
	@Override	
	public String toString() {
		return "ventaproducto [id=" + id + ",tipoDocumento=" + tipoDocumento + ", estado=" + estado + "]";
	}
}
