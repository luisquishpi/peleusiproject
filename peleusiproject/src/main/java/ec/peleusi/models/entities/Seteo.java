package ec.peleusi.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ec.peleusi.utils.IdentificacionDecimalEnum;
import ec.peleusi.utils.SignoMonedaEnum;
import ec.peleusi.utils.TipoInventarioEnum;

@Entity
@Table(name = "seteo")
public class Seteo {

	public Seteo() {
		super();
	}

	public Seteo(TarifaIva tarifaIva, Integer numeroDecimales, IdentificacionDecimalEnum identificacionDecimal,
			SignoMonedaEnum signoMoneda, Cliente cliente, String nombrePercentajeServicioAdicional,
			Double porcentajeServicioAdicional, TipoInventarioEnum tipoInventario, Boolean conServicioAdicional) {
		this.id = null;
		this.tarifaIva = tarifaIva;
		this.numeroDecimales = numeroDecimales;
		this.identificacionDecimal = identificacionDecimal;
		this.signoMoneda = signoMoneda;
		this.cliente = cliente;
		this.nombrePercentajeServicioAdicional = nombrePercentajeServicioAdicional;
		this.porcentajeServicioAdicional = porcentajeServicioAdicional;
		this.tipoInventario = tipoInventario;
		this.conServicioAdicional= conServicioAdicional;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TarifaIva tarifaIva;

	@Column(name = "numeroDecimales", nullable = false)
	private Integer numeroDecimales;

	@Column(name = "identificacionDecimal", nullable = false, length = 8)
	private IdentificacionDecimalEnum identificacionDecimal;

	@Column(name = "signoMoneda", nullable = false, length = 8)
	private SignoMonedaEnum signoMoneda;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Cliente cliente;

	@Column(name = "nombrePercentajeServicioAdicional", nullable = true, length = 100)
	private String nombrePercentajeServicioAdicional;

	@Column(name = "porcentajeServicioAdicionalVenta", nullable = true, length = 8)
	private Double porcentajeServicioAdicional;

	@Column(name = "tipoIventario", nullable = true, length = 8)
	private TipoInventarioEnum tipoInventario;

	@Column(name = "conServicioAdicional", nullable = true, length = 8)
	private Boolean conServicioAdicional;

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

	public IdentificacionDecimalEnum getIdentificacionDecimal() {
		return identificacionDecimal;
	}

	public void setIdentificacionDecimal(IdentificacionDecimalEnum identificacionDecimal) {
		this.identificacionDecimal = identificacionDecimal;
	}

	public SignoMonedaEnum getSignoMoneda() {
		return signoMoneda;
	}

	public void setSignoMoneda(SignoMonedaEnum signoMoneda) {
		this.signoMoneda = signoMoneda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNombrePercentajeServicioAdicional() {
		return nombrePercentajeServicioAdicional;
	}

	public void setNombrePercentajeServicioAdicional(String nombrePercentajeServicioAdicional) {
		this.nombrePercentajeServicioAdicional = nombrePercentajeServicioAdicional;
	}

	public Double getPorcentajeServicioAdicional() {
		return porcentajeServicioAdicional;
	}

	public void setPorcentajeServicioAdicional(Double porcentajeServicioAdicional) {
		this.porcentajeServicioAdicional = porcentajeServicioAdicional;
	}

	public TipoInventarioEnum getTipoInventario() {
		return tipoInventario;
	}

	public void setTipoInventario(TipoInventarioEnum tipoInventario) {
		this.tipoInventario = tipoInventario;
	}

	public Boolean getConServicioAdicional() {
		return conServicioAdicional;
	}

	public void setConServicioAdicional(Boolean conServicioAdicional) {
		this.conServicioAdicional = conServicioAdicional;
	}

}
