package ec.peleusi.utils;

public enum TipoUsuarioEnum {
	    ADMINISTRADOR("Administrador"), CONTADOR("Contador"), CAJERO("Cajero"), BODEGUERO("Bodeguero");
	
	private String description;

	private TipoUsuarioEnum(String description) {
		this.description = description;
	}

	public String getTipoUsuarioEnum() {
		return description;
	}
	
	@Override
	public String toString() {
		return description ;
	}	

}
