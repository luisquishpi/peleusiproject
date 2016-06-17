package ec.peleusi.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import ec.peleusi.utils.TipoRetencionEnum;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;

@Entity
@Table(name = "tiporetencion")

public class TipoRetencion {

	public TipoRetencion(String codigo, TipoRetencionEnum tipo, Double porcentaje, String descripcion) {
		this.id = null;
		this.codigo = codigo;
		this.tipo = tipo;
		this.porcentaje = porcentaje;
		this.descripcion = descripcion;
	}

	public TipoRetencion() {

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "codigo", unique = true, nullable = false, length = 15)
	private String codigo;

	@Column(name = "tipo", nullable = false, length = 30)
	private TipoRetencionEnum tipo;

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

	public TipoRetencionEnum getTipoRetencionEnum() {
		return tipo;
	}

	public void setTipoRetencionEnum(TipoRetencionEnum tipoRetencionEnum) {
		this.tipo = tipoRetencionEnum;
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
		//return "[id=" + id + ", descripcion=" + descripcion + ", porcentaje=" + porcentaje + ", tipo=" + tipoRetencionEnum + "]";
		return  codigo + "-"+descripcion;
		//return  tipoRetencionEnum.toString();
	}
}
