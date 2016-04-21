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
@Table (name = "direccionPersona")

public class DireccionPersona {

	public DireccionPersona() {
		
	}

	public DireccionPersona(Persona persona, String direccion, String telefono, String celular, String mail,
			Ciudad cuidad, String codigoPostal) {
		super();
		this.id = null;
		this.persona = persona;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.mail = mail;
		this.cuidad = cuidad;
		this.codigoPostal = codigoPostal;
	}

	@Id 
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Persona persona;
	// idPersona
	
	@Column(name = "direccion", unique = true, nullable = false, length = 50)
	private String direccion;
	
	@Column (name = "telefono", unique = true, nullable = false, length = 20)
	private String telefono;
		
	@Column (name = "celular", length = 15)
	private String celular;
	
	@Column (name = "mail", length = 40)
	private String mail;
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Ciudad cuidad;
	// idCiudad
	
	@Column (name = "codigoPostal", length = 30)
	private String codigoPostal;
	
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
	
	@Override
	
	public String toString() {
		return "DireccionPersona [id=" + id + ",direccion=" + direccion + ", telefono=" + telefono
				+ ", celular=" + celular + ", mail=" + mail + ", codigoPostal="
				+ codigoPostal + "]";
	}
}
