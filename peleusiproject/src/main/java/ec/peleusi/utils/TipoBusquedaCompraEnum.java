package ec.peleusi.utils;

public enum TipoBusquedaCompraEnum {

PENDIENTES("Pendientes"), PAGADOS("Pagados"), TODOS("Todos");
	
	private String description;

	private TipoBusquedaCompraEnum(String description) {
		this.description = description;
	}

	public String getTipoBusquedaCompraEnum() {
		return description;
	}
	
	@Override
	public String toString() {
		return description ;
	}	
	
}
