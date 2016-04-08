package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tarifaice")
public class TarifaIce {

	public TarifaIce() {
	}

	public TarifaIce(String codigo, String nombre, Double porcentaje) {
		super();
		this.id = null;
		this.codigo = codigo;
		this.nombre = nombre;
		this.porcentaje = porcentaje;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "codigo", unique = true, nullable = false, length = 15)
	private String codigo;

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;

	@Column(name = "porcentaje", nullable = false, length = 10)
	private Double porcentaje;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		return "[id=" + id + ", codigo=" + codigo + " nombre=" + nombre + "]";
	}

}
