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
@Table(name = "persona")
public class Persona {

	public Persona() {
	}

	public Persona(TipoIdentificacion tipoIdentificacion, String identificacion, String razonSocial,
			TipoPrecio tipoPrecio, TipoCalificacionPersona tipoCalificacionPersona, Integer diasCredito,
			Double porcentajeDescuento, String descripcion, String tipoPersona) {
		super();
		this.id = null;
		this.tipoIdentificacion = tipoIdentificacion;
		this.identificacion = identificacion;
		this.razonSocial = razonSocial;
		this.tipoPrecio = tipoPrecio;
		this.tipoCalificacionPersona = tipoCalificacionPersona;
		this.diasCredito = diasCredito;
		this.porcentajeDescuento = porcentajeDescuento;
		this.descripcion = descripcion;
		this.tipoPersona = tipoPersona;
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

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoPrecio tipoPrecio;
	// idTipoPrecio

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoCalificacionPersona tipoCalificacionPersona;
	// idCalificacionPersona

	@Column(name = "diasCredito", unique = true, nullable = false, length = 50)
	private Integer diasCredito;

	@Column(name = "porcentajeDescuento", unique = true, nullable = false)
	private Double porcentajeDescuento;

	@Column(name = "descripcion", unique = true, nullable = false)
	private String descripcion;

	@Column(name = "tipoPersona", unique = false, nullable = false)
	private String tipoPersona;

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

	public TipoPrecio getTipoPrecio() {
		return tipoPrecio;
	}

	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}

	public TipoCalificacionPersona getTipoCalificacionPersona() {
		return tipoCalificacionPersona;
	}

	public void setTipoCalificacionPersona(TipoCalificacionPersona tipoCalificacionPersona) {
		this.tipoCalificacionPersona = tipoCalificacionPersona;
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

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	@Override
	
	public String toString() {
        return "Persona [id=" + id +",identificacion="+identificacion+", razonSocial=" + razonSocial+", tipoPrecio=" + tipoPrecio +", diasCredito="+diasCredito+", porcentajeDescuento="+porcentajeDescuento+", descripcion=" + descripcion +"]";
    }

}
