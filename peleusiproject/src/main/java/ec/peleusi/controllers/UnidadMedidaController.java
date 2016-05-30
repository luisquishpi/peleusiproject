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
	
	public String updateUnidadMedida(UnidadMedida unidadMedida) {
		return unidadMedidaDao.update(unidadMedida);
	}
	
	public String deleteUnidadMedida(UnidadMedida unidadMedida) {
		return unidadMedidaDao.deleteById(unidadMedida.getId());
	}	

	public List<UnidadMedida> unidadMedidaList() {
		return unidadMedidaDao.findAll();
	}

	public UnidadMedida getunidadMedida(int id) {
		return DaoFactory.getFactory().getUnidadMedidaDao().read(id);
	}

	public UnidadMedida getUnidadMedidaIdentificacion(String id) {
		return unidadMedidaDao.getUnidadMedidaIdentificacion(id);
	}
	
	public List<UnidadMedida> getUnidadMedidaList(String parametro) {
		return unidadMedidaDao.UnidadMedidaList(parametro);
	}
}
