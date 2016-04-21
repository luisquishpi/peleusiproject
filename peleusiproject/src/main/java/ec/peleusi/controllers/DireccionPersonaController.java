package ec.peleusi.controllers;

import ec.peleusi.models.daos.DireccionPersonaDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.DireccionPersona;


public class DireccionPersonaController {
	
	private DireccionPersonaDao direccionPersonaDao;

	public DireccionPersonaController() {
		direccionPersonaDao = new DaoFactory().getDireccionPersonaDao();
	}

	public String createDireccionPersona(DireccionPersona direccionPersona) {
		return direccionPersonaDao.create(direccionPersona);
	}
}
