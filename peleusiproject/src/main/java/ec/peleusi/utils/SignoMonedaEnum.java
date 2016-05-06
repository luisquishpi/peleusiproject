package ec.peleusi.utils;

import java.io.Serializable;

public enum SignoMonedaEnum implements Serializable {
	Dolar("$"), Pesos("P"), Euros("E");

	private String description;

	private SignoMonedaEnum(String descrition) {
		this.description = descrition;
	}

	public String getSignoMoneda() {
		return description;
	}
	
	@Override
	public String toString() {
		return description ;
	}	
}
