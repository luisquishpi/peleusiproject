package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.KardexDao;
import ec.peleusi.models.entities.Kardex;

public class KardexController {

	private KardexDao kardexDao;

	public KardexController() {
		kardexDao = new DaoFactory().getKardexDao();
	}
	
	public String createKardex(Kardex kardex){
		return kardexDao.create(kardex);
	}

	public List<Kardex> kardexList(){
		
		return kardexDao.findAll();
	}
	
	public List<Kardex> getKardexList(String parametro) {
		return kardexDao.KardexList(parametro);
	}

	public boolean readKardex(Kardex kardex) {
		return kardexDao.read(kardex.getId()) != null;
	}
}
