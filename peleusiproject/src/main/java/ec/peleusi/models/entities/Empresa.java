package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.FileInputStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ec.peleusi.models.entities.Ciudad;
import com.mysql.jdbc.Blob;

@Entity
@Table(name = "empresa")
public class Empresa {
	
	public Empresa(String nombre, String identificacion, String direccion, String telefono, String fax, String email,
			String url, FileInputStream foto, String ruta, Ciudad ciudad) {
		super();
		this.id= null;
		this.nombre = nombre;
		this.identificacion = identificacion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.fax = fax;
		this.email = email;
		this.url = url;
		this.foto = foto;
		this.ciudad = ciudad;
		this.ruta=ruta;
	}	

	public Empresa() {
		
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", unique = true, nullable = false, length = 100)
	private String nombre;

	@Column(name = "identificacion", unique = true, nullable = false, length = 13)
	private String identificacion;

	@Column(name = "direcion", unique = true, nullable = false, length = 100)
	private String direccion;
	
	@Column(name = "telefono", unique = true, nullable = false, length = 50)
	private String telefono;
	
	@Column(name = "fax", unique = true, nullable = false, length = 50)
	private String fax;
	
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;
	
	@Column(name = "url", unique = true, nullable = false, length = 100)
	private String url;
	
	@Column(name = "foto", unique = true, nullable = false, length = 200)
	private FileInputStream foto;
	
	@Column(name = "ruta", unique = true, nullable = false, length = 200)
	private String ruta;
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private Ciudad ciudad;

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

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public FileInputStream getFoto() {
		return foto;
	}

	public void setFoto(FileInputStream foto) {
		this.foto = foto;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}	

}
