package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.PersonaDao;
import ec.peleusi.models.entities.Persona;

public class PersonaControllers {
	private PersonaDao personaDao;

	public PersonaControllers() {

		personaDao = new DaoFactory().getPersonaDao();
	}

	public String createPersona(Persona persona) {
		return personaDao.create(persona);

	}

}
