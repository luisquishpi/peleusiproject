package ec.peleusi.models.daos;

import ec.peleusi.models.entities.Producto;

public class ProductoDao extends GenericDao<Producto, Integer> {

	public ProductoDao() {
		super(Producto.class);
	}

}
