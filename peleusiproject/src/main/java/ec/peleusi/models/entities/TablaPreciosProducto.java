package ec.peleusi.models.entities;

public class TablaPreciosProducto {

	private String nombre;
	private Double porcentajeUtilidad;
	private Double subtotal;
	private Double ice;
	private Double iva;
	private Double total;
	private Double utilidad;

	public TablaPreciosProducto() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPorcentajeUtilidad() {
		return porcentajeUtilidad;
	}

	public void setPorcentajeUtilidad(Double porcentajeUtilidad) {
		this.porcentajeUtilidad = porcentajeUtilidad;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getIce() {
		return ice;
	}

	public void setIce(Double ice) {
		this.ice = ice;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(Double utilidad) {
		this.utilidad = utilidad;
	}

	@Override
	public String toString() {
		return "TablaPreciosProducto [nombre=" + nombre + ",porcetajeUtilidad=" + porcentajeUtilidad + "]";
	}
}
