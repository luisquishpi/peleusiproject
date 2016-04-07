package ec.peleusi.models.daos;

import ec.peleusi.models.entities.Ciudad;

public class CiudadDao extends GenericDao<Ciudad, Integer> {

	public CiudadDao() {
		super(Ciudad.class);
	}

}
