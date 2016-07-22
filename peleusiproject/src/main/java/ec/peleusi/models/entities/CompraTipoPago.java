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
@Table(name = "compraTipoPago")


public class CompraTipoPago {
	
	
	
	public CompraTipoPago() {
		super();
	}

	public CompraTipoPago(Integer id, Compra compra, TipoPago tipoPago, Double valor, String detalle) {
		super();
		this.id = null;
		this.compra = compra;
		this.tipoPago = tipoPago;
		this.valor = valor;
		this.detalle = detalle;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Compra compra;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private TipoPago tipoPago;
	
	@Column(name = "valor", nullable = false)
	private Double valor = 0.0;
	
	@Column(name = "detalle",  nullable = true, length = 200)
	private String detalle;

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

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return "CompraTipoPago [id=" + id + ", compra=" + compra + ", tipoPago=" + tipoPago + ", valor=" + valor
				+ ", detalle=" + detalle + "]";
	}	
	
}
