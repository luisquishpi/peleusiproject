package ec.peleusi.models.daos;

import ec.peleusi.models.entities.TipoPago;

public class TipoPagoDao extends GenericDao<TipoPago, Integer> {

	public TipoPagoDao() {
		super(TipoPago.class);		
	}
}
