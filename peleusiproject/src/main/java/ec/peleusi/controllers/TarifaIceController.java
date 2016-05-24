package ec.peleusi.controllers;

import java.util.List;

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
	
	public String updateTarifaIce(TarifaIce tarifaIce) {
		return tarifaIceDao.update(tarifaIce);
	}

	public List<TarifaIce> tarifaIceList() {
		return tarifaIceDao.findAll();
	}
	
	public List<TarifaIce> getTarifaIceList(String parametro) {
		return tarifaIceDao.TarifaIceList(parametro);
	}
	
	public String deleteTarifaIce(TarifaIce tarifaIce) {
		return tarifaIceDao.deleteById(tarifaIce.getId());
	}	
}
