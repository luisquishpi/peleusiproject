package ec.peleusi.controllers;

import ec.peleusi.models.daos.DireccionPersonaDao;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.DireccionPersona;
import ec.peleusi.models.entities.Persona;


public class DireccionPersonaController {
	
	private DireccionPersonaDao direccionPersonaDao;

	public DireccionPersonaController() {
		direccionPersonaDao = new DaoFactory().getDireccionPersonaDao();
	}

	public String createDireccionPersona(DireccionPersona direccionPersona) {
		return direccionPersonaDao.create(direccionPersona);
	}
	
	public boolean existPersona(Persona persona) {
		return direccionPersonaDao.read(persona.getId()) != null;
	}
	
	public List<DireccionPersona> DireccionPersonaList() {
		return direccionPersonaDao.findAll();
	}
	
	public DireccionPersona getDireccionPersona(int id) {
		return DaoFactory.getFactory().getDireccionPersonaDao().read(id);
	}	
	
	public boolean update(DireccionPersona direccionPersona) {
		return direccionPersonaDao.update(direccionPersona);
	}

}
