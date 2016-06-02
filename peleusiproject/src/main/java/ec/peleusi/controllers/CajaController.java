package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.CajaDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.Caja;

public class CajaController {
	
	private CajaDao cajaDao;

	public CajaController() {
		cajaDao = new DaoFactory().getCajaDao();
	}

	public String createCaja(Caja caja) {
		return cajaDao.create(caja);
	}

	public String updateCaja(Caja caja) {
		return cajaDao.update(caja);
	}

	public List<Caja> cajaList() {
		return cajaDao.findAll();
	}

	public List<Caja> getCajaList(String parametro) {
		return cajaDao.CajaList(parametro);
	}	
		
	public String deleteCaja(Caja caja) {
		return cajaDao.deleteById(caja.getId());
	}	
}
