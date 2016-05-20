package ec.peleusi.models.daos;

import ec.peleusi.models.entities.VentaProducto;

public class VentaProductoDao extends GenericDao<VentaProducto, Integer> {

	public VentaProductoDao(Class<VentaProducto> persistentClass) {
		super(VentaProducto.class);		
	}
}
