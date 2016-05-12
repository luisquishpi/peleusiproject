package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoPrecioDao;
import ec.peleusi.models.entities.TipoPrecio;

public class TipoPrecioController {

	private TipoPrecioDao tipoPrecioDao;

	public TipoPrecioController() {
		tipoPrecioDao = new DaoFactory().getTipoPrecioDao();
	}

	public String createTipoPrecio(TipoPrecio tipoPrecio) {
		return tipoPrecioDao.create(tipoPrecio);
	}
	
	public List<TipoPrecio> tipoPrecioList() {
		return tipoPrecioDao.findAll();
	}
	
	public List<TipoPrecio> getTipoPrecioList(String parametro) {
		return tipoPrecioDao.TipoPrecioList(parametro);
	}


}
