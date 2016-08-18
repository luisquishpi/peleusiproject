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
@Table(name = "sucursal")
public class Sucursal {
		
	public Sucursal() {
		super();
	}
	public Sucursal(String nombre, String direccion, String telefono, String fax, String email, 
			 Ciudad ciudad, Empresa empresa) {
		super();
		this.id = null;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fax = fax;
		this.email = email;	
		this.ciudad = ciudad;
		this.empresa = empresa;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", unique = true, nullable = false, length = 256)
	private String nombre;
	
	@Column(name = "direcion", nullable = true, length = 256)
	private String direccion;
	
	@Column(name = "telefono", nullable = true,   length = 50)
	private String telefono;
	
	@Column(name = "fax",  nullable = true, length = 50)
	private String fax;
	
	@Column(name = "email",  nullable = true, length = 256)
	private String email;

	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Ciudad ciudad;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Empresa empresa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}		

	@Override
	public String toString() {
		return nombre;
	}	
}
