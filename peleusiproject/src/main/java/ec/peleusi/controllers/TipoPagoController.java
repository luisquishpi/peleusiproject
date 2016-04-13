package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoPagoDao;
import ec.peleusi.models.entities.TipoPago;

public class TipoPagoController {
	private TipoPagoDao tipoPagoDao;

	public TipoPagoController() {
		tipoPagoDao = new DaoFactory().getTipoPagoDao();
	}
	public String createTipoPago(TipoPago tipoPago)
	{
		return tipoPagoDao.create(tipoPago);
	}
}
