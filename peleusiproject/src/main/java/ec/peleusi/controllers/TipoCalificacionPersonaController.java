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
	
	public String updateTipoCalificacionPersona(TipoCalificacionPersona tipoCalificacionPersona) {
		return tipoCalificacionPersonaDao.update(tipoCalificacionPersona);
	}
	
	public String deleteTipoCalificacionPersona(TipoCalificacionPersona tipoCalificacionPersona) {
		return tipoCalificacionPersonaDao.deleteById(tipoCalificacionPersona.getId());
	}
	
	public List<TipoCalificacionPersona> tipoCalificacionPersonaList() {
		return tipoCalificacionPersonaDao.findAll();
	}
	
	public List<TipoCalificacionPersona> getTipoCalificacionPersonaList(String parametro) {
		return tipoCalificacionPersonaDao.TipoCalificacionPersonaList(parametro);
	}
}
