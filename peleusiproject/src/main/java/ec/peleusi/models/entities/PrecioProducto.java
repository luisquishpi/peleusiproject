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
@Table(name = "precioproducto")
public class PrecioProducto {
	public PrecioProducto() {

	}

	public PrecioProducto(Producto producto, TipoPrecio tipoPrecio, Double porcentajeUtilidadUnitario,
			Double porcentajeUtilidadLote, Double precioBrutoUnitario, Double precioBrutoLote, Double utilidadUnitario,
			Double utilidadLote) {
		super();
		this.id = null;
		this.producto = producto;
		this.tipoPrecio = tipoPrecio;
		this.porcentajeUtilidadUnitario = porcentajeUtilidadUnitario;
		this.porcentajeUtilidadLote = porcentajeUtilidadLote;
		this.precioBrutoUnitario = precioBrutoUnitario;
		this.precioBrutoLote = precioBrutoLote;
		this.utilidadUnitario = utilidadUnitario;
		this.utilidadLote = utilidadLote;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Producto producto;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoPrecio tipoPrecio;

	@Column(name = "porcentajeutilidadunitario", nullable = false)
	private Double porcentajeUtilidadUnitario;

	@Column(name = "porcentajeutilidadlote", nullable = false)
	private Double porcentajeUtilidadLote;

	@Column(name = "preciobrutounitario", nullable = false)
	private Double precioBrutoUnitario;

	@Column(name = "preciobrutolote", nullable = false)
	private Double precioBrutoLote;

	@Column(name = "utilidadunitario", nullable = false)
	private Double utilidadUnitario;

	@Column(name = "utilidadlote", nullable = false)
	private Double utilidadLote;

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

	public TipoPrecio getTipoPrecio() {
		return tipoPrecio;
	}

	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}

	public Double getPorcentajeUtilidadUnitario() {
		return porcentajeUtilidadUnitario;
	}

	public void setPorcentajeUtilidadUnitario(Double porcentajeUtilidadUnitario) {
		this.porcentajeUtilidadUnitario = porcentajeUtilidadUnitario;
	}

	public Double getPorcentajeUtilidadLote() {
		return porcentajeUtilidadLote;
	}

	public void setPorcentajeUtilidadLote(Double porcentajeUtilidadLote) {
		this.porcentajeUtilidadLote = porcentajeUtilidadLote;
	}

	public Double getPrecioBrutoUnitario() {
		return precioBrutoUnitario;
	}

	public void setPrecioBrutoUnitario(Double precioBrutoUnitario) {
		this.precioBrutoUnitario = precioBrutoUnitario;
	}

	public Double getPrecioBrutoLote() {
		return precioBrutoLote;
	}

	public void setPrecioBrutoLote(Double precioBrutoLote) {
		this.precioBrutoLote = precioBrutoLote;
	}

	public Double getUtilidadUnitario() {
		return utilidadUnitario;
	}

	public void setUtilidadUnitario(Double utilidadUnitario) {
		this.utilidadUnitario = utilidadUnitario;
	}

	public Double getUtilidadLote() {
		return utilidadLote;
	}

	public void setUtilidadLote(Double utilidadLote) {
		this.utilidadLote = utilidadLote;
	}

	@Override
	public String toString() {
		return "[" + producto + "," + tipoPrecio + "," + porcentajeUtilidadUnitario + "]";
	}

}
