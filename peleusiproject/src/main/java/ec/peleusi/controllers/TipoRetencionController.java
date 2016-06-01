package ec.peleusi.controllers;

import java.util.List;

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
		
	public List<TipoRetencion> tipoRetencionList() {
		return  tipoRetencionDao.findAll();
	}
	
	public List<TipoRetencion> getTipoRetencionList(String parametro) {
		return tipoRetencionDao.tipoRetencionList(parametro);
	}	
	
	public String updateTipoRetencion(TipoRetencion tipoRetencion){
		return tipoRetencionDao.update(tipoRetencion);
	}
	public String deleteTipoRetencion(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.deleteById(tipoRetencion.getId());
	}	
}	