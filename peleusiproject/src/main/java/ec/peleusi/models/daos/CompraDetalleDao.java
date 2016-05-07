package ec.peleusi.models.daos;

import ec.peleusi.models.entities.CompraDetalle;

public class CompraDetalleDao extends GenericDao<CompraDetalle, Integer> {

	public CompraDetalleDao() {
		super(CompraDetalle.class);		
	}
}
