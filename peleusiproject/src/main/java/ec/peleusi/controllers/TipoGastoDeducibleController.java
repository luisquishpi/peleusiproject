package ec.peleusi.controllers;

import java.util.List;

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

	public String updateTipoGastoDeducible(TipoGastoDeducible tipoGastoDeducible) {
		return tipoGastoDeducibleDao.update(tipoGastoDeducible);
	}

	public List<TipoGastoDeducible> tipoGastoDeducibleList() {
		return tipoGastoDeducibleDao.findAll();
	}

	public List<TipoGastoDeducible> getTipoGastoDeducibleList(String parametro) {
		return tipoGastoDeducibleDao.TipoGastoDeducibleList(parametro);
	}	
		
	public String deleteTipoGastoDeducible(TipoGastoDeducible tipoGastoDeducible) {
		return tipoGastoDeducibleDao.deleteById(tipoGastoDeducible.getId());
	}	
}
