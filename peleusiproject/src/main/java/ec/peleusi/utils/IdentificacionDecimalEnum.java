package ec.peleusi.utils;

import java.io.Serializable;

public enum IdentificacionDecimalEnum implements Serializable {
	
	Punto("."), Coma(",");

	private String description;

	private IdentificacionDecimalEnum(String description) {
		this.description = description;
	}

	public String getSignoMoneda() {
		return description;
	}
	@Override
	public String toString() {
		return description ;
	}	
}
