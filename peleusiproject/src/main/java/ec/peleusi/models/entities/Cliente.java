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
@Table(name = "cliente")
public class Cliente {
	
	
	public Cliente(TipoIdentificacion tipoIdentificacion, String identificacion, String razonSocial, String direccion,
			String telefono, TipoPrecio tipoPrecio, TipoCalificacionPersona tipoCalificacionPersona,
			Integer diasCredito, Double porcentajeDescuento, String descripcion, String email, String celular,
			Ciudad ciudad) {
		this.id=null;
		this.tipoIdentificacion = tipoIdentificacion;
		this.identificacion = identificacion;
		this.razonSocial = razonSocial;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tipoPrecio = tipoPrecio;
		this.tipoCalificacionPersona = tipoCalificacionPersona;
		this.diasCredito = diasCredito;
		this.porcentajeDescuento = porcentajeDescuento;
		this.descripcion = descripcion;
		this.email = email;
		this.celular = celular;
		this.ciudad = ciudad;
	}
	
	public Cliente() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoIdentificacion tipoIdentificacion;

	@Column(name = "identificacion", unique = true, nullable = false, length = 25)
	private String identificacion;

	@Column(name = "razonSocial", unique = false, nullable = false, length = 250)
	private String razonSocial;

	@Column(name = "direccion", unique = false, nullable = false, length = 100)
	private String direccion;

	@Column(name = "telefono", unique = false, nullable = false, length = 30)
	private String telefono;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoPrecio tipoPrecio;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoCalificacionPersona tipoCalificacionPersona;

	@Column(name = "diasCredito", nullable = false, length = 50)
	private Integer diasCredito;

	@Column(name = "porcentajeDescuento", nullable = false, length = 15)
	private Double porcentajeDescuento;

	@Column(name = "descripcion",unique = false, nullable = true ,length = 100)
	private String descripcion;

	@Column(name = "Email", unique = false, nullable = true, length = 100)
	private String email;

	@Column(name = "Celular", unique = false, nullable = true, length = 30)
	private String celular;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Ciudad ciudad;

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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	@Override

	public String toString() {
		return "Persona [id=" + id + ",identificacion=" + identificacion + ", razonSocial=" + razonSocial
				+ ", tipoPrecio=" + tipoPrecio + ", diasCredito=" + diasCredito + ", porcentajeDescuento="
				+ porcentajeDescuento + ", descripcion=" + descripcion + "]";
	}

}
