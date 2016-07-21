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

import ec.peleusi.utils.UnidadMedidaPesoEnum;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@Entity
@Table(name = "producto")
public class Producto {

	public Producto() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "codigo", unique = true, nullable = false, length = 50)
	private String codigo;

	@Column(name = "nombre", nullable = false, length = 250)
	private String nombre;

	@Column(name = "peso", nullable = false)
	private Double peso = 0.0;

	@Column(name = "unidadmedidapeso", nullable = false)
	private UnidadMedidaPesoEnum unidadMedidaPeso;

	@Column(name = "costo", nullable = false)
	private Double costo = 0.0;

	@Column(name = "foto", nullable = true, length = 16777215)
	private byte[] foto;

	@Column(name = "esdeducible", nullable = false)
	private Boolean esDeducible = false;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	private TipoGastoDeducible tipoGastoDeducible;

	@Column(name = "sepuedeFraccionar", nullable = false)
	private Boolean sePuedeFraccionar = false;

	@Column(name = "manejainventario", nullable = false)
	private Boolean manejaInventario = false;

	@Column(name = "stock", nullable = false)
	private Double stock = 0.0;

	@Column(name = "stockMinimo", nullable = false)
	private Double stockMinimo = 0.0;

	@Column(name = "fechaActualizacion", nullable = true)
	private Date fechaActualizacion;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private CategoriaProducto categoriaProducto;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TarifaIce tarifaIce;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private UnidadMedida unidadMedidaCompra;

	@Column(name = "cantidadunidadmedidacompra", nullable = false)
	private Double cantidadUnidadMedidaCompra = 0.0;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private UnidadMedida unidadMedidaVenta;

	@Column(name = "cantidadunidadmedidaventa", nullable = false)
	private Double cantidadUnidadMedidaVenta = 0.0;

	@Column(name = "tieneiva", nullable = false)
	private Boolean tieneIva;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public UnidadMedidaPesoEnum getUnidadMedidaPeso() {
		return unidadMedidaPeso;
	}

	public void setUnidadMedidaPeso(UnidadMedidaPesoEnum unidadMedidaPeso) {
		this.unidadMedidaPeso = unidadMedidaPeso;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Boolean getEsDeducible() {
		return esDeducible;
	}

	public void setEsDeducible(Boolean esDeducible) {
		this.esDeducible = esDeducible;
	}

	public TipoGastoDeducible getTipoGastoDeducible() {
		return tipoGastoDeducible;
	}

	public void setTipoGastoDeducible(TipoGastoDeducible tipoGastoDeducible) {
		this.tipoGastoDeducible = tipoGastoDeducible;
	}

	public Boolean getSePuedeFraccionar() {
		return sePuedeFraccionar;
	}

	public void setSePuedeFraccionar(Boolean sePuedeFraccionar) {
		this.sePuedeFraccionar = sePuedeFraccionar;
	}

	public Boolean getManejaInventario() {
		return manejaInventario;
	}

	public void setManejaInventario(Boolean manejaInventario) {
		this.manejaInventario = manejaInventario;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(Double stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public TarifaIce getTarifaIce() {
		return tarifaIce;
	}

	public void setTarifaIce(TarifaIce tarifaIce) {
		this.tarifaIce = tarifaIce;
	}

	public UnidadMedida getUnidadMedidaCompra() {
		return unidadMedidaCompra;
	}

	public void setUnidadMedidaCompra(UnidadMedida unidadMedidaCompra) {
		this.unidadMedidaCompra = unidadMedidaCompra;
	}

	public Double getCantidadUnidadMedidaCompra() {
		return cantidadUnidadMedidaCompra;
	}

	public void setCantidadUnidadMedidaCompra(Double cantidadUnidadMedidaCompra) {
		this.cantidadUnidadMedidaCompra = cantidadUnidadMedidaCompra;
	}

	public UnidadMedida getUnidadMedidaVenta() {
		return unidadMedidaVenta;
	}

	public void setUnidadMedidaVenta(UnidadMedida unidadMedidaVenta) {
		this.unidadMedidaVenta = unidadMedidaVenta;
	}

	public Double getCantidadUnidadMedidaVenta() {
		return cantidadUnidadMedidaVenta;
	}

	public void setCantidadUnidadMedidaVenta(Double cantidadUnidadMedidaVenta) {
		this.cantidadUnidadMedidaVenta = cantidadUnidadMedidaVenta;
	}

	public Boolean getTieneIva() {
		return tieneIva;
	}

	public void setTieneIva(Boolean tieneIva) {
		this.tieneIva = tieneIva;
	}

	public BooleanProperty tieneIvaProperty() {
		BooleanProperty tieneIvaProperty = new SimpleBooleanProperty(tieneIva);
		return tieneIvaProperty;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + "]";
	}

}
