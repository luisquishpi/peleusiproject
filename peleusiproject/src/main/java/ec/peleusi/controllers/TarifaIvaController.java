package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TarifaIvaDao;
import ec.peleusi.models.entities.TarifaIva;

public class TarifaIvaController {
	
	private TarifaIvaDao tarifaIvaDao;
	
	public TarifaIvaController(){		
		tarifaIvaDao = new DaoFactory().getTarifaIvaDao();
	}
	
	public void saveTarifaIva(TarifaIva tarifaIva) {
		tarifaIvaDao.create(tarifaIva);
	}

	public String createTarifaIva(TarifaIva tarifaIva) {
		return tarifaIvaDao.create(tarifaIva);
	}

}


