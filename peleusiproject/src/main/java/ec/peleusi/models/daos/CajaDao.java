package ec.peleusi.models.daos;

import ec.peleusi.models.entities.Caja;

public class CajaDao extends GenericDao<Caja, Integer> {

	public CajaDao() {
		super(Caja.class);
	}
}
