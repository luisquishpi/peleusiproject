package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@Entity
@Table(name = "direccionProveedor")

public class DireccionProveedor {

	public DireccionProveedor() {

	}
	
	public DireccionProveedor(Proveedor proveedor, String nombre, String direccion, String telefono, String celular,
			String email, Ciudad ciudad, String codigoPostal, Boolean porDefecto) {
		super();
		this.id= null;
		this.proveedor = proveedor;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.email = email;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.porDefecto = porDefecto;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Proveedor proveedor;
	// idProveedor

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;

	@Column(name = "direccion", nullable = false, length = 100)
	private String direccion;

	@Column(name = "telefono", nullable = false, length = 20)
	private String telefono;

	@Column(name = "celular", length = 15)
	private String celular;

	@Column(name = "email", length = 40)
	private String email;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Ciudad ciudad;
	// idCiudad

	@Column(name = "codigoPostal", length = 30)
	private String codigoPostal;

	@Column(name = "porDefecto")
	private Boolean porDefecto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
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

	public BooleanProperty porDefectoProperty() {
		BooleanProperty porDefectoProperty = new SimpleBooleanProperty(porDefecto);
		return porDefectoProperty;
	}
	
	@Override
	public String toString() {
		return "Direccion Proveedor [id=" + id + ",nombre=" + nombre + ",ciudad=" + ciudad + ", proveedor=" + proveedor
				+ ", direccion=" + direccion + ", telefono=" + telefono + ",  celular=" + celular + ",  email=" + email
				+ ",  codigoPostal=" + codigoPostal + "]";
	}

}
