package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoGastoDeducibleDao;
import ec.peleusi.models.entities.TipoGastoDeducible;

public class TipoGastoDeducibleController {

	private TipoGastoDeducibleDao tipoGastoDeducibleDao;

	public TipoGastoDeducibleController() {
		tipoGastoDeducibleDao = new DaoFactory().getTipoGastoDeducibleDao();
	}

	public String createTipoGastoDeducible(TipoGastoDeducible tipoGastoDeducible) {
		return tipoGastoDeducibleDao.create(tipoGastoDeducible);
	}

}
