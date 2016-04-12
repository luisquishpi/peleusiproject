package ec.peleusi.models.daos;

import ec.peleusi.models.entities.TipoRetencion;

public class TipoRetencionDao extends GenericDao<TipoRetencion, Integer> {
	public TipoRetencionDao() {
		super(TipoRetencion.class);
	}
}
