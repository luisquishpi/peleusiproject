package ec.peleusi.models.entities;

import javax.persistence.Entity;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unidamedida")

public class UnidadMedida {

	public UnidadMedida() {

	}

	public UnidadMedida(String nombre, String abreviatura) {
		super();
		this.id = null;
		this.nombre = nombre;
		this.abreviatura = abreviatura;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;

	@Column(name = "abreviatura", unique = true, nullable = false, length = 10)
	private String abreviatura;

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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Override
	public String toString() {
		return nombre+" ("+abreviatura+")";
	}

}