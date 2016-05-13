package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.CompraDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.Compra;


public class CompraController {
	
	
	private CompraDao  CompraDao;

	public CompraController() {
		CompraDao = new DaoFactory().getCompraDao();
	}

	public String createCompra(Compra compra) {
		return CompraDao.create(compra);
	}

	public List<Compra> compraList() {
		return CompraDao.findAll();
	}

}

