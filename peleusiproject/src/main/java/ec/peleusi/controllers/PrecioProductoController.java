package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.PrecioProductoDao;
import ec.peleusi.models.entities.PrecioProducto;
import ec.peleusi.models.entities.Producto;

public class PrecioProductoController {
	private PrecioProductoDao precioProductoDao;

	public PrecioProductoController() {
		precioProductoDao = new DaoFactory().getPrecioProductoDao();
	}

	public String createPrecioProducto(PrecioProducto precioProducto) {
		return precioProductoDao.create(precioProducto);
	}

	public List<PrecioProducto> precioProductoList() {
		return precioProductoDao.findAll();
	}

	public List<PrecioProducto> precioProductoList(Producto producto) {
		return precioProductoDao.precioProductoList(producto);
	}
}
