package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.UnidadMedidaDao;
import ec.peleusi.models.entities.UnidadMedida;

public class UnidadMedidaController {

	private UnidadMedidaDao unidadMedidaDao;

	public UnidadMedidaController() {
		unidadMedidaDao = new DaoFactory().getUnidadMedidaDao();
	}

	public void saveUnidadMedida(UnidadMedida unidadMedida) {
		unidadMedidaDao.create(unidadMedida);
	}

	public String createUnidadMedida(UnidadMedida unidadMedida) {
		return unidadMedidaDao.create(unidadMedida);
	}

}
