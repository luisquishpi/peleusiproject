package ec.peleusi.controllers;


import ec.peleusi.models.daos.CompraTipoPagoDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.CompraTipoPago;

public class CompraTipoPagoController {
	
 private CompraTipoPagoDao compraTipoPagoDao;
	
	public CompraTipoPagoController() {
		compraTipoPagoDao = new DaoFactory().getCompraTipoPagoDao();
	}

	public String createCompraTipoPago(CompraTipoPago compraTipoPago) {
		return compraTipoPagoDao.create(compraTipoPago);
	}

	public String updateCompraRetencion(CompraTipoPago compraTipoPago) {
		return compraTipoPagoDao.update(compraTipoPago);
	}
	
}
