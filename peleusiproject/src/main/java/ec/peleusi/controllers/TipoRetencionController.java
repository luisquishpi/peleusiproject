package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoRetencionDao;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.TipoRetencionEnum;

public class TipoRetencionController {

	private TipoRetencionDao tipoRetencionDao;

	public TipoRetencionController() {
		tipoRetencionDao = new DaoFactory().getTipoRetencionDao();
	}

	public String createTipoRetencion(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.create(tipoRetencion);
	}

	public boolean existTipoRetencion(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.read(tipoRetencion.getId()) != null;
	}

	public List<TipoRetencion> tipoRetencionList() {
		return tipoRetencionDao.findAll();
	}

	public TipoRetencion getTipoRetencion(int id) {
		return DaoFactory.getFactory().getTipoRetencionDao().read(id);
	}

	public String update(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.update(tipoRetencion);
	}

	public String delete(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.deleteById(tipoRetencion.getId());
	}
	
	public List<TipoRetencion> getTipoRetencionList(String parametro) {
		return tipoRetencionDao.tipoRetencionList(parametro);
	}
	
	public List<TipoRetencion> tipoRetencionTipoList(TipoRetencionEnum tipo) {
		return tipoRetencionDao.tipoRetencionTipoList(tipo);
	}
}