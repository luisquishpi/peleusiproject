package ec.peleusi.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "kardex")

public class Kardex {
	
	public Kardex() {
		
	}

	public Kardex(Producto producto, Boolean activo, Date fecha, String detalle, Double cantidadEntrada,
			Double precioUnitarioEntrada, Double precioTotalEntrada, Double cantidadSalida, Double precioUnitarioSalida,
			Double precioTotalSalida, Double cantidadSaldo, Double precioUnitarioSaldo, Double precioTotalSaldo) {
		super();
		this.id = null;
		this.producto = producto;
		this.activo = activo;
		this.fecha = fecha;
		this.detalle = detalle;
		this.cantidadEntrada = cantidadEntrada;
		this.precioUnitarioEntrada = precioUnitarioEntrada;
		this.precioTotalEntrada = precioTotalEntrada;
		this.cantidadSalida = cantidadSalida;
		this.precioUnitarioSalida = precioUnitarioSalida;
		this.precioTotalSalida = precioTotalSalida;
		this.cantidadSaldo = cantidadSaldo;
		this.precioUnitarioSaldo = precioUnitarioSaldo;
		this.precioTotalSaldo = precioTotalSaldo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Producto producto;
	//idProducto
	
	@Column(name = "activo", nullable = false)
	private Boolean activo;
	
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	
	@Column(name = "detalle", nullable = false, length = 100)
	private String detalle;
	
	@Column(name= "cantidadEntrada", nullable = false)
	private Double cantidadEntrada = 0.0;
	
	@Column(name = "precioUnitarioEntrada", nullable = false)
	private Double precioUnitarioEntrada = 0.0;
	
	@Column(name = "precioTotalEntrada", nullable = false)
	private Double precioTotalEntrada = 0.0;
	
	@Column(name = "cantidadSalida", nullable = false)
	private Double cantidadSalida = 0.0; 
	
	@Column(name = "precioUnitarioSalida", nullable = false)
	private Double precioUnitarioSalida = 0.0;
	
	@Column(name = "precioTotalSalida", nullable = false)
	private Double precioTotalSalida = 0.0;
	
	@Column(name = "cantidadSaldo", nullable = false)
	private Double cantidadSaldo = 0.0;
	
	@Column(name = "precioUnitarioSaldo", nullable = false)
	private Double precioUnitarioSaldo = 0.0;
	
	@Column(name = "precioTotalSaldo",nullable = false)
	private Double precioTotalSaldo = 0.0;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Double getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(Double cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}

	public Double getPrecioUnitarioEntrada() {
		return precioUnitarioEntrada;
	}

	public void setPrecioUnitarioEntrada(Double precioUnitarioEntrada) {
		this.precioUnitarioEntrada = precioUnitarioEntrada;
	}

	public Double getPrecioTotalEntrada() {
		return precioTotalEntrada;
	}

	public void setPrecioTotalEntrada(Double precioTotalEntrada) {
		this.precioTotalEntrada = precioTotalEntrada;
	}

	public Double getCantidadSalida() {
		return cantidadSalida;
	}

	public void setCantidadSalida(Double cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public Double getPrecioUnitarioSalida() {
		return precioUnitarioSalida;
	}

	public void setPrecioUnitarioSalida(Double precioUnitarioSalida) {
		this.precioUnitarioSalida = precioUnitarioSalida;
	}

	public Double getPrecioTotalSalida() {
		return precioTotalSalida;
	}

	public void setPrecioTotalSalida(Double precioTotalSalida) {
		this.precioTotalSalida = precioTotalSalida;
	}

	public Double getCantidadSaldo() {
		return cantidadSaldo;
	}

	public void setCantidadSaldo(Double cantidadSaldo) {
		this.cantidadSaldo = cantidadSaldo;
	}

	public Double getPrecioUnitarioSaldo() {
		return precioUnitarioSaldo;
	}

	public void setPrecioUnitarioSaldo(Double precioUnitarioSaldo) {
		this.precioUnitarioSaldo = precioUnitarioSaldo;
	}

	public Double getPrecioTotalSaldo() {
		return precioTotalSaldo;
	}

	public void setPrecioTotalSaldo(Double precioTotalSaldo) {
		this.precioTotalSaldo = precioTotalSaldo;
	}
}
