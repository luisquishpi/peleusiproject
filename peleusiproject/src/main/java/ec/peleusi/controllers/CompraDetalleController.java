package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.CompraDetalleDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.CompraDetalle;

public class CompraDetalleController {
	
	private CompraDetalleDao compraDetalleDao;
	
	public CompraDetalleController() {
		compraDetalleDao = new DaoFactory().getDetalleCompraDao();
	}

	public String createCompra(CompraDetalle compraDetalle) {
		return compraDetalleDao.create(compraDetalle);
	}

	public List<CompraDetalle> compraDetalleList() {
		return compraDetalleDao.findAll();
	}
	
	

}
