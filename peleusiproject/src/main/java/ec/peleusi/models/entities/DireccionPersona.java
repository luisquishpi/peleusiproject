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
@Table(name = "direccionPersona")

public class DireccionPersona {

	public DireccionPersona(Persona persona, String nombre, String direccion, String telefono, String celular,
			String mail, Ciudad cuidad, String codigoPostal, Boolean porDefecto) {
		super();
		this.id = null;
		this.persona = persona;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.mail = mail;
		this.cuidad = cuidad;
		this.codigoPostal = codigoPostal;
		this.porDefecto = porDefecto;
	}

	public DireccionPersona() {

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Persona persona;
	// idPersona

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "direccion", unique = true, nullable = false, length = 50)
	private String direccion;

	@Column(name = "telefono", unique = true, nullable = false, length = 20)
	private String telefono;

	@Column(name = "celular", length = 15)
	private String celular;

	@Column(name = "mail", length = 40)
	private String mail;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Ciudad cuidad;
	// idCiudad

	@Column(name = "codigoPostal", length = 30)
	private String codigoPostal;

	@Column(name = "porDefecto", nullable = false)
	private Boolean porDefecto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Ciudad getCuidad() {
		return cuidad;
	}

	public void setCuidad(Ciudad cuidad) {
		this.cuidad = cuidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Boolean getPorDefecto() {
		return porDefecto;
	}

	public void setPorDefecto(Boolean porDefecto) {
		this.porDefecto = porDefecto;
	}

	@Override

	public String toString() {
		return "Persona [id=" + id + ",nombre=" + nombre + ", direccion=" + direccion
				+ ", telefono=" + telefono + "]";
	}
}
