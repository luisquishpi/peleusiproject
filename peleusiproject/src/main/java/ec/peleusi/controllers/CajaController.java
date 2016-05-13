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

	public List<Caja> cajaList() {
		return cajaDao.findAll();
	}
}
