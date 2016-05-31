package ec.peleusi.controllers;


import ec.peleusi.models.daos.CompraRetencionDao;
import ec.peleusi.models.daos.DaoFactory;

import ec.peleusi.models.entities.CompraRetencion;

public class CompraRetencionController {
	
	private CompraRetencionDao compraRetencionDao;
	
	public CompraRetencionController() {
		compraRetencionDao = new DaoFactory().getCompraRetencionDao();
	}

	public String createCompraRetencion(CompraRetencion compraRetencion) {
		return compraRetencionDao.create(compraRetencion);
	}

	public String updateCompraRetencion(CompraRetencion compraRetencion) {
		return compraRetencionDao.update(compraRetencion);
	}

}
