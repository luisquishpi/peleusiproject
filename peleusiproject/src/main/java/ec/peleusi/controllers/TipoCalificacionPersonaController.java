package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoCalificacionPersonaDao;
import ec.peleusi.models.entities.TipoCalificacionPersona;

public class TipoCalificacionPersonaController {
	private TipoCalificacionPersonaDao tipoCalificacionPersonaDao;

	public TipoCalificacionPersonaController() {
		tipoCalificacionPersonaDao = new DaoFactory().getTipoCalificacionPersonaDao();
	}

	public String createTipoCalificacionPersona(TipoCalificacionPersona tipoCalificacionPersona) {
		return tipoCalificacionPersonaDao.create(tipoCalificacionPersona);
	}

	public List<TipoCalificacionPersona> tipoCalificacionPersonaList() {
		return tipoCalificacionPersonaDao.findAll();
	}
}
