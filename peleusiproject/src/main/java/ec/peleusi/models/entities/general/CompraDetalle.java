package ec.peleusi.models.entities.general;

import ec.peleusi.models.entities.Producto;

public class CompraDetalle {

	private Integer id;
	private Producto idProducto;	
	private String codigo;
	private String nombre;
	private Double cantidad;
	private Double costo;
	private Double porcentajeDescuento;
	private Double valorDescuento;
	private Double precioNeto;
	private Double subtotal;
	private Double porcentaIva;
	private Double valorIva;
	private Double stock;
	private Double porcentajeIce;
	private Double valorIce;
	private Double total;
	
	
	public CompraDetalle(Integer id, Producto idProducto, String codigo, String nombre, Double cantidad,
			Double costo, Double porcentajeDescuento, Double valorDescuento, Double precioNeto, Double subtotal,
			Double porcentaIva, Double valorIva, Double stock, Double porcentajeIce, Double valorIce, Double total) {
		super();
		this.id = id;
		this.idProducto = idProducto;
		this.codigo = codigo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
		this.porcentajeDescuento = porcentajeDescuento;
		this.valorDescuento = valorDescuento;
		this.precioNeto = precioNeto;
		this.subtotal = subtotal;
		this.porcentaIva = porcentaIva;
		this.valorIva = valorIva;
		this.stock = stock;
		this.porcentajeIce = porcentajeIce;
		this.valorIce = valorIce;
		this.total = total;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(Double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	public Double getValorDescuento() {
		return valorDescuento;
	}
	public void setValorDescuento(Double valorDescuento) {
		this.valorDescuento = valorDescuento;
	}
	public Double getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(Double precioNeto) {
		this.precioNeto = precioNeto;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Double getPorcentaIva() {
		return porcentaIva;
	}
	public void setPorcentaIva(Double porcentaIva) {
		this.porcentaIva = porcentaIva;
	}
	public Double getValorIva() {
		return valorIva;
	}
	public void setValorIva(Double valorIva) {
		this.valorIva = valorIva;
	}
	public Double getPorcentajeIce() {
		return porcentajeIce;
	}
	public void setPorcentajeIce(Double porcentajeIce) {
		this.porcentajeIce = porcentajeIce;
	}
	public Double getValorIce() {
		return valorIce;
	}
	public void setValorIce(Double valorIce) {
		this.valorIce = valorIce;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Producto getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Producto idProducto) {
		this.idProducto = idProducto;
	}
	@Override
	public String toString() {
		return "CompraDetalle [id=" + id + ", stock=" + stock + ", codigo=" + codigo + ", nombre=" + nombre
				+ ", cantidad=" + cantidad + ", costo=" + costo + ", porcentajeDescuento=" + porcentajeDescuento
				+ ", valorDescuento=" + valorDescuento + ", precioNeto=" + precioNeto + ", subtotal=" + subtotal
				+ ", porcentaIva=" + porcentaIva + ", valorIva=" + valorIva + ", porcentajeIce=" + porcentajeIce
				+ ", valorIce=" + valorIce + ", total=" + total + ", idProducto=" + idProducto + "]";
	}
	
	

}
