package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoPagoDao;
import ec.peleusi.models.entities.TipoPago;

public class TipoPagoController {
	private TipoPagoDao tipoPagoDao;

	public TipoPagoController() {
		tipoPagoDao = new DaoFactory().getTipoPagoDao();
	}
	
	public String createTipoPago(TipoPago tipoPago)	{
		return tipoPagoDao.create(tipoPago);
	}
	
	public String updateTipoPago(TipoPago tipoPago) {
		return tipoPagoDao.update(tipoPago);
	}
	
	public String deleteTipoPago(TipoPago tipoPago) {
		return tipoPagoDao.deleteById(tipoPago.getId());
	}	
	
	public List<TipoPago> tipoPagoList() {
		return tipoPagoDao.findAll();
	}
	
	public List<TipoPago> getTipoPagoList(String parametro) {
		return tipoPagoDao.tipoPagoList(parametro);
	}	
}
