package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoriaproducto")
public class CategoriaProducto {
	
	public CategoriaProducto() {
	}
	
	public CategoriaProducto(String nombre, Integer dependencia, Boolean contieneProductos) {

		this.id = null;
		this.nombre = nombre;
		this.dependencia = dependencia;
		this.contieneProductos = contieneProductos;
	}
	public CategoriaProducto(Integer Id, String nombre, Integer dependencia, Boolean contieneProductos) {

		this.id = Id;
		this.nombre = nombre;
		this.dependencia = dependencia;
		this.contieneProductos = contieneProductos;
	}	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "dependencia", nullable = true)
	private Integer dependencia = 0;

	@Column(name = "contieneproductos", nullable = false)
	private Boolean contieneProductos = false;

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

	public Integer getDependencia() {
		return dependencia;
	}

	public void setDependencia(Integer dependencia) {
		this.dependencia = dependencia;
	}

	public Boolean getContieneProductos() {
		return contieneProductos;
	}

	public void setContieneProductos(Boolean contieneProductos) {
		this.contieneProductos = contieneProductos;
	}
	
	public String toString() {
		return nombre;
	}
}
