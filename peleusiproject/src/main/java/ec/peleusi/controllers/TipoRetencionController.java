package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoRetencionDao;
import ec.peleusi.models.entities.TipoRetencion;

public class TipoRetencionController {

	private TipoRetencionDao tipoRetencionDao;

	public TipoRetencionController() {
		tipoRetencionDao = new DaoFactory().getTipoRetencionDao();
	}

	public String createTipoRetencion(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.create(tipoRetencion);
	}
}
