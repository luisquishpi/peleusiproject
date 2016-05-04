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
@Table(name = "seteo")

public class Seteo {

	public Seteo() {
		super();
	}

	public Seteo(TarifaIva tarifaIva, Integer numeroDecimales, Integer identificacionDecimal, Integer signoMoneda,
			Persona persona, Integer numeroItemsFactura, Double porcentajeServicioAdicional,
			Boolean actualizacionAutomaticaPrecio, Integer tipoInventario) {
		this.id = null;
		this.tarifaIva = tarifaIva;
		this.numeroDecimales = numeroDecimales;
		this.identificacionDecimal = identificacionDecimal;
		this.signoMoneda = signoMoneda;
		this.persona = persona;
		this.numeroItemsFactura = numeroItemsFactura;
		this.porcentajeServicioAdicional = porcentajeServicioAdicional;
		this.actualizacionAutomaticaPrecio = actualizacionAutomaticaPrecio;
		this.tipoInventario = tipoInventario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TarifaIva getTarifaIva() {
		return tarifaIva;
	}

	public void setTarifaIva(TarifaIva tarifaIva) {
		this.tarifaIva = tarifaIva;
	}

	public Integer getNumeroDecimales() {
		return numeroDecimales;
	}

	public void setNumeroDecimales(Integer numeroDecimales) {
		this.numeroDecimales = numeroDecimales;
	}

	public Integer getIdentificacionDecimal() {
		return identificacionDecimal;
	}

	public void setIdentificacionDecimal(Integer identificacionDecimal) {
		this.identificacionDecimal = identificacionDecimal;
	}

	public Integer getSignoMoneda() {
		return signoMoneda;
	}

	public void setSignoMoneda(Integer signoMoneda) {
		this.signoMoneda = signoMoneda;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Integer getNumeroItemsFactura() {
		return numeroItemsFactura;
	}

	public void setNumeroItemsFactura(Integer numeroItemsFactura) {
		this.numeroItemsFactura = numeroItemsFactura;
	}

	public Double getPorcentajeServicioAdicional() {
		return porcentajeServicioAdicional;
	}

	public void setPorcentajeServicioAdicional(Double porcentajeServicioAdicional) {
		this.porcentajeServicioAdicional = porcentajeServicioAdicional;
	}

	public Boolean getActualizacionAutomaticaPrecio() {
		return actualizacionAutomaticaPrecio;
	}

	public void setActualizacionAutomaticaPrecio(Boolean actualizacionAutomaticaPrecio) {
		this.actualizacionAutomaticaPrecio = actualizacionAutomaticaPrecio;
	}

	public Integer getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(Integer tipoInvenatrio) {
		this.tipoInventario = tipoInvenatrio;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TarifaIva tarifaIva;

	@Column(name = "numeroDecimales", nullable = true, length = 8)
	private Integer numeroDecimales;

	@Column(name = "identificacionDecimal", nullable = true, length = 8)
	private Integer identificacionDecimal;

	@Column(name = "signoMoneda", nullable = true, length = 8)
	private Integer signoMoneda;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Persona persona;

	@Column(name = "numeroItemsFactura", nullable = true, length = 8)
	private Integer numeroItemsFactura;

	@Column(name = "porcentajeServicioAdicionalVenta", nullable = true, length = 8)
	private Double porcentajeServicioAdicional;

	@Column(name = "actualizacionAutomaticaPrecio", nullable = true, length = 1)
	private Boolean actualizacionAutomaticaPrecio;

	@Column(name = "tipoIventario", nullable = true, length = 8)
	private Integer tipoInventario;

}
