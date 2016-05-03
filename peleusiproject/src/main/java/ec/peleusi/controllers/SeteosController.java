package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.SeteosDao;
import ec.peleusi.models.daos.UsuarioDao;
import ec.peleusi.models.entities.Usuario;

public class SeteosController {
	
private SeteosDao seteosDao;
	
	public SeteosController() {
		seteosDao = new DaoFactory().getSet();
	}
	
	public String createUsuario(Usuario usuario){
		return usuarioDao.create(usuario);
	}

}
