package ec.peleusi.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tipoidentificacion")

public class TipoIdentificacion {

	public TipoIdentificacion() {

	}
	
	public TipoIdentificacion(String codigo, String nombre, Boolean valida) {
		super();
		this.id = null;
		this.codigo = codigo;
		this.nombre = nombre;
		this.valida = valida;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "codigo", unique = true, nullable = false, length = 15)
	private String codigo;

	@Column(name = "nombre", unique = true, nullable = false, length = 50)
	private String nombre;

	@Column(name = "valida", nullable = false)
	private Boolean valida;

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

	public Boolean getValida() {
		return valida;
	}

	public void setValida(Boolean valida) {
		this.valida = valida;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
