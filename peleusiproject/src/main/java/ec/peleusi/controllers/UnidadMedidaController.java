package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.UnidadMedidaDao;
import ec.peleusi.models.entities.UnidadMedida;

public class UnidadMedidaController {

	private UnidadMedidaDao unidadMedidaDao;

	public UnidadMedidaController() {
		unidadMedidaDao = new DaoFactory().getUnidadMedidaDao();
	}

	public String createUnidadMedida(UnidadMedida unidadMedida) {
		return unidadMedidaDao.create(unidadMedida);
	}
	public List<UnidadMedida> unidadMedidaList() {
		return unidadMedidaDao.findAll();
	}

}
