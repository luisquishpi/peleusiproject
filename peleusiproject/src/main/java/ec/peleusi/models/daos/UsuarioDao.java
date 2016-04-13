package ec.peleusi.models.daos;

import ec.peleusi.models.entities.Usuario;

public class UsuarioDao extends GenericDao<Usuario, Integer> {
	
	public UsuarioDao() {
		super(Usuario.class);
	}
	
}
