package ec.peleusi.utils;

public enum TipoRetencionEnum {
			IVA("Iva"), FUENTE("Fuente");
	
	private String description;

	private TipoRetencionEnum(String description) {
		this.description = description;
	}

	public String getTipoRetencionEnum() {
		return description;
	}
	
	@Override
	public String toString() {
		return description ;
	}	
}
