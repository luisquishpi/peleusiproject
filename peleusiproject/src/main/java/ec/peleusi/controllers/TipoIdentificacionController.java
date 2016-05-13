package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoIdentificacionDao;
import ec.peleusi.models.entities.TipoIdentificacion;

public class TipoIdentificacionController {

	private TipoIdentificacionDao tipoIdentificacionDao;

	public TipoIdentificacionController() {
		tipoIdentificacionDao = new DaoFactory().getTipoIdentificacionDao();
	}

	public String createTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		return tipoIdentificacionDao.create(tipoIdentificacion);
	}
	public List<TipoIdentificacion> tipoIdentificacionList() {
		return tipoIdentificacionDao.findAll();
	}
	
	public List<TipoIdentificacion> getTipoIdentificacionList(String parametro) {
		return tipoIdentificacionDao.TipoIdentificacionList(parametro);
	}	
}
