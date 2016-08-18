package ec.peleusi.controllers;


import java.util.List;

import ec.peleusi.models.daos.CompraTipoPagoDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.Compra;
import ec.peleusi.models.entities.CompraTipoPago;



public class CompraTipoPagoController {
	
 private CompraTipoPagoDao compraTipoPagoDao;
	
	public CompraTipoPagoController() {
		compraTipoPagoDao = new DaoFactory().getCompraTipoPagoDao();
	}

	public String createCompraTipoPago(CompraTipoPago compraTipoPago) {
		return compraTipoPagoDao.create(compraTipoPago);
	}

	public String updateCompraTipoPago(CompraTipoPago compraTipoPago) {
		return compraTipoPagoDao.update(compraTipoPago);
	}
	public List<CompraTipoPago> listCompraTipoPago(Compra compra) {
		return compraTipoPagoDao.compraTipoPagoList(compra);
	}	
	public String deleteCompraTipoPago(CompraTipoPago compraTipoPago) {
		return compraTipoPagoDao.deleteById(compraTipoPago.getId());
	}	
	
}
