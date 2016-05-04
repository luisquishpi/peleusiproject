package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.SeteoDao;
import ec.peleusi.models.entities.Seteo;

public class SeteoController {

	private SeteoDao seteoDao;

	public SeteoController() {
		seteoDao = new DaoFactory().getSeteoDao();
	}

	public String createSeteos(Seteo seteos) {
		return seteoDao.create(seteos);
	}

	public List<Seteo> seteoList() {
		return seteoDao.findAll();
	}

}
