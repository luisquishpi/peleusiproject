package ec.peleusi.models.daos;

import ec.peleusi.models.entities.TipoCalificacionPersona;

public class TipoCalificacionPersonaDao extends GenericDao<TipoCalificacionPersona, Integer> {
	public TipoCalificacionPersonaDao() {
		super(TipoCalificacionPersona.class);
	}
}
