package ec.peleusi.controllers;

import java.util.List;

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

	public List<Producto> productoList() {
		return productoDao.findAll();
	}

	public List<Producto> productoList(String parametro) {
		return productoDao.ProductoList(parametro);
	}

	public Producto getProductoCodigo(String codigo) {
		return productoDao.getProductoCodigo(codigo);
	}

	public String deleteProducto(Producto producto) {
		return productoDao.deleteById(producto.getId());
	}
}
