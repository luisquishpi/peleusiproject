package ec.peleusi.controllers;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.UsuarioDao;
import ec.peleusi.models.entities.Usuario;

public class UsuarioController{
	private UsuarioDao usuarioDao;
	
	public UsuarioController() {
		usuarioDao = new DaoFactory().getUsuarioDao();
	}
	
	public String createUsuario(Usuario usuario){
		return usuarioDao.create(usuario);
	}
}


