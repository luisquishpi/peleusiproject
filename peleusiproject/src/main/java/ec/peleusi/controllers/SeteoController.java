package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.SeteoDao;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.models.entities.Seteo;

public class SeteoController {

	private SeteoDao seteoDao;

	public SeteoController() {
		seteoDao = new DaoFactory().getSeteoDao();
	}

	public String createSeteos(Seteo seteo) {
		return seteoDao.create(seteo);
	}

	public List<Seteo> seteoList() {
		return seteoDao.findAll();
	}
	public String update(Seteo seteo) {
		return seteoDao.update(seteo);
	}
	public Seteo getSeteo(Integer id)
	{
		return seteoDao.getSeteo(id);		
		
	}

}
