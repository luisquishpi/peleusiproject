package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.PrecioProductoDao;
import ec.peleusi.models.entities.PrecioProducto;

public class PrecioProductoController {
	private PrecioProductoDao precioProductoDao;

	public PrecioProductoController() {
		precioProductoDao = new DaoFactory().getPrecioProductoDao();
	}

	public String createPrecioProducto(PrecioProducto precioProducto) {
		return precioProductoDao.create(precioProducto);
	}
}
