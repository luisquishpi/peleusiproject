package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.PaisDao;
import ec.peleusi.models.entities.Pais;

public class PaisController {
	private PaisDao paisDao;

	public PaisController() {
		paisDao = new DaoFactory().getPaisDao();
	}

	public void createPais(Pais pais) {
		paisDao.create(pais);
	}

}
