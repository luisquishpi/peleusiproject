package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoCalificacionClienteDao;
import ec.peleusi.models.entities.TipoCalificacionCliente;

public class TipoCalificacionClienteController {
	
	private TipoCalificacionClienteDao tipoCalificacionClienteDao;

	public TipoCalificacionClienteController() {
		tipoCalificacionClienteDao = new DaoFactory().getTipoCalificacionClienteDao();
	}

	public String createTipoCalificacionCliente(TipoCalificacionCliente tipoCalificacionCliente) {
		return tipoCalificacionClienteDao.create(tipoCalificacionCliente);
	}
	
	public String updateTipoCalificacionCliente(TipoCalificacionCliente tipoCalificacionCliente) {
		return tipoCalificacionClienteDao.update(tipoCalificacionCliente);
	}
	
	public String deleteTipoCalificacionCliente(TipoCalificacionCliente tipoCalificacionCliente) {
		return tipoCalificacionClienteDao.deleteById(tipoCalificacionCliente.getId());
	}
	
	public List<TipoCalificacionCliente> tipoCalificacionClienteList() {
		return tipoCalificacionClienteDao.findAll();
	}
	
	public List<TipoCalificacionCliente> getTipoCalificacionClienteList(String parametro) {
		return tipoCalificacionClienteDao.TipoCalificacionClienteList(parametro);
	}
}
