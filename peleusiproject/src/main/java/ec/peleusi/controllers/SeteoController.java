package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.SeteoDao;
import ec.peleusi.models.entities.Seteo;


public class SeteoController {
	
private SeteoDao seteosDao;
	
	public SeteoController() {
		seteosDao = new DaoFactory().getSeteoDao();
	}
	
	public String createSeteos(Seteo seteos){
		return seteosDao.create(seteos);
	}

}
