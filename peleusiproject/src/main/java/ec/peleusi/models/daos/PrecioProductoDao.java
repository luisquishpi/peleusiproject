package ec.peleusi.models.daos;

import ec.peleusi.models.entities.PrecioProducto;

public class PrecioProductoDao extends GenericDao<PrecioProducto, Integer> {

	public PrecioProductoDao() {
		super(PrecioProducto.class);
	}
}
