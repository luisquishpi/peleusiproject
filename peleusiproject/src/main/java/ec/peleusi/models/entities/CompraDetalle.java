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
@Table(name = "compraDetalle")

public class CompraDetalle {
	
	
	public CompraDetalle() {
		super();
	}

	public CompraDetalle(Compra compra, Producto producto, String codigoProducto, String nombreProducto,
			Double costoProducto, Double valorDescuentoProducto, Double porcentajeDescuentoProducto,
			Double porcentajeIvaProducto, Double valorIvaProducto, Double stock, Double subtotal, Double porcentajeIceProducto, Double valorIceProducto) {
		super();
		this.id=null;
		this.compra = compra;
		this.producto = producto;
		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.costoProducto = costoProducto;
		this.valorDescuentoProducto = valorDescuentoProducto;
		this.porcentajeDescuentoProducto = porcentajeDescuentoProducto;
		this.porcentajeIvaProducto = porcentajeIvaProducto;
		this.valorIvaProducto = valorIvaProducto;
		Stock = stock;
		this.subtotal = subtotal;
		this.porcentajeIceProducto=porcentajeIceProducto;
		this.valorIceProducto= valorIceProducto;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Compra compra;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Producto producto;
	
	@Column(name = "codigoProducto", nullable = false, length = 50)
	private String codigoProducto;

	@Column(name = "nombreProducto", nullable = false, length = 250)
	private String nombreProducto;
	
	@Column(name = "costoProducto", nullable = false)
	private Double costoProducto = 0.0;
	
	@Column(name = "valorDescuentoProducto", nullable = false)
	private Double valorDescuentoProducto = 0.0;
	
	@Column(name = "porcentajeDescuentoProducto", nullable = false)
	private Double porcentajeDescuentoProducto = 0.0;
	
	@Column(name = "porcentajeIvaProducto", nullable = false)
	private Double porcentajeIvaProducto = 0.0;
	
	@Column(name = "valorIvaProducto", nullable = false)
	private Double valorIvaProducto = 0.0;
	
	@Column(name = "Stock", nullable = false)
	private Double Stock = 0.0;
	
	@Column(name = "subtotal", nullable = false)
	private Double subtotal = 0.0;
	
	@Column(name = "porcentajeIceProducto", nullable = false)
	private Double porcentajeIceProducto = 0.0;
	
	@Column(name = "valorIceProducto", nullable = false)
	private Double valorIceProducto = 0.0;

	
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Double getCostoProducto() {
		return costoProducto;
	}

	public void setCostoProducto(Double costoProducto) {
		this.costoProducto = costoProducto;
	}

	public Double getValorDescuentoProducto() {
		return valorDescuentoProducto;
	}

	public void setValorDescuentoProducto(Double valorDescuentoProducto) {
		this.valorDescuentoProducto = valorDescuentoProducto;
	}

	public Double getPorcentajeDescuentoProducto() {
		return porcentajeDescuentoProducto;
	}

	public void setPorcentajeDescuentoProducto(Double porcentajeDescuentoProducto) {
		this.porcentajeDescuentoProducto = porcentajeDescuentoProducto;
	}

	public Double getPorcentajeIvaProducto() {
		return porcentajeIvaProducto;
	}

	public void setPorcentajeIvaProducto(Double porcentajeIvaProducto) {
		this.porcentajeIvaProducto = porcentajeIvaProducto;
	}

	public Double getValorIvaProducto() {
		return valorIvaProducto;
	}

	public void setValorIvaProducto(Double valorIvaProducto) {
		this.valorIvaProducto = valorIvaProducto;
	}

	public Double getStock() {
		return Stock;
	}

	public void setStock(Double stock) {
		Stock = stock;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}	
	
	public Double getPorcentajeIceProducto() {
		return porcentajeIceProducto;
	}

	public void setPorcentajeIceProducto(Double porcentajeIceProducto) {
		this.porcentajeIceProducto = porcentajeIceProducto;
	}

	public Double getValorIceProducto() {
		return valorIceProducto;
	}

	public void setValorIceProducto(Double valorIceProducto) {
		this.valorIceProducto = valorIceProducto;
	}

	
	}
