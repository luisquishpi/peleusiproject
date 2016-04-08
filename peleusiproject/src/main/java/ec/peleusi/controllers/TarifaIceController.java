package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TarifaIceDao;
import ec.peleusi.models.entities.TarifaIce;

public class TarifaIceController {
	private TarifaIceDao tarifaIceDao;

	public TarifaIceController() {
		tarifaIceDao = new DaoFactory().getTarifaIceDao();
	}

	public String createTarifaIce(TarifaIce tarifaIce) {
		return tarifaIceDao.create(tarifaIce);
	}
}
