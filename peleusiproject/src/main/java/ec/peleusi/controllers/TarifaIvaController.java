package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TarifaIvaDao;
import ec.peleusi.models.entities.TarifaIva;


public class TarifaIvaController {

	private TarifaIvaDao tarifaIvaDao;

	public TarifaIvaController() {
		tarifaIvaDao = new DaoFactory().getTarifaIvaDao();
	}

	public String createTarifaIva(TarifaIva tarifaIva) {
		return tarifaIvaDao.create(tarifaIva);
	}
	
	public String updateTarifaIva(TarifaIva tarifaIva) {
		return tarifaIvaDao.update(tarifaIva);
	}

	public List<TarifaIva> tarifaIvaList() {
		return tarifaIvaDao.findAll();
	}

	public List<TarifaIva> getTarifaIvaList(String parametro) {
		return tarifaIvaDao.TarifaIvaList(parametro);
	}	
		
	public String deleteTarifaIva(TarifaIva tarifaIva) {
		return tarifaIvaDao.deleteById(tarifaIva.getId());
	}	
}
