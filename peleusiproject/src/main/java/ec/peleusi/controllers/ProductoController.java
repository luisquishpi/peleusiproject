package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.ProductoDao;
import ec.peleusi.models.entities.Producto;

public class ProductoController {

	private ProductoDao productoDao;

	public ProductoController() {
		productoDao = new DaoFactory().getProductoDao();
	}

	public String createProducto(Producto producto) {
		return productoDao.create(producto);
	}

}
