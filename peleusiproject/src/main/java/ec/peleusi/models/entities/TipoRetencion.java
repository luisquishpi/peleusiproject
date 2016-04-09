package ec.peleusi.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;

@Entity
@Table(name = "tiporetencion")

public class TipoRetencion {

	public TipoRetencion() {

	}

	public TipoRetencion(String codigo, Integer tipo, Double porcentaje, String descripcion) {
		this.id = null;
		this.codigo = codigo;
		this.tipo = tipo;
		this.porcentaje = porcentaje;
		this.descripcion = descripcion;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "codigo", unique = true, nullable = false, length = 15)
	private String codigo;

	@Column(name = "tipo", unique = true, nullable = false)
	private Integer tipo;

	@Column(name = "porcentaje", nullable = false, length = 15)
	private Double porcentaje;

	@Column(name = "descripcion", unique = true, nullable = false, length = 100)
	private String descripcion;

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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", tipo=" + tipo + "]";
	}
}
