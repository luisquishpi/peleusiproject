package ec.peleusi.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "proveedor")

public class Proveedor {
	
	public Proveedor() {
		
	}
	
	public Proveedor(TipoIdentificacion tipoIdentificacion, String identificacion, String razonSocial,
			Integer diasCredito, Double porcentajeDescuento, String descripcion) {
		super();
		this.id = null;
		this.tipoIdentificacion = tipoIdentificacion;
		this.identificacion = identificacion;
		this.razonSocial = razonSocial;
		this.diasCredito = diasCredito;
		this.porcentajeDescuento = porcentajeDescuento;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoIdentificacion tipoIdentificacion;
	// idTipoIdentificacion

	@Column(name = "identificacion", unique = true, nullable = false, length = 25)
	private String identificacion;

	@Column(name = "razonSocial", unique = true, nullable = false, length = 50)
	private String razonSocial;

	@Column(name = "diasCredito", nullable = false, length = 50)
	private Integer diasCredito;

	@Column(name = "porcentajeDescuento", nullable = false, length = 15)
	private Double porcentajeDescuento;

	@Column(name = "descripcion", length = 100)
	private String descripcion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getDiasCredito() {
		return diasCredito;
	}

	public void setDiasCredito(Integer diasCredito) {
		this.diasCredito = diasCredito;
	}

	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(Double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return "Proveedor [id=" + id + ",identificacion=" + identificacion + ", razonSocial=" + razonSocial
				+ ", diasCredito=" + diasCredito + ", porcentajeDescuento="
				+ porcentajeDescuento + ", descripcion=" + descripcion + "]";
	}


}
