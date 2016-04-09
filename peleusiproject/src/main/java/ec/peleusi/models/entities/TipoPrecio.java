package ec.peleusi.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tipoprecio")

public class TipoPrecio {
	public TipoPrecio() {

	}

	public TipoPrecio(String nombre, Double porcentaje) {
		super();
		this.id = null;
		this.nombre = nombre;
		this.porcentaje = porcentaje;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;

	@Column(name = "Porcentaje", unique = true, nullable = false, length = 10)
	private Double porcentaje;

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

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", nombre" + nombre + "]";

	}
}
