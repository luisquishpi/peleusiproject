package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.SucursalDao;
import ec.peleusi.models.entities.Sucursal;

public class SucursalController {	
	
	private SucursalDao sucursalDao;

	public SucursalController() {
		sucursalDao = new DaoFactory().getSucursalDao();
	}

	public String createSucursal(Sucursal sucursal){
		return sucursalDao.create(sucursal);
	}
	
	public String updateSucursal(Sucursal sucursal) {
		return sucursalDao.update(sucursal);
	}
	
	public List<Sucursal> SucursalList() {
		return sucursalDao.findAll();
	}	
	
	public List<Sucursal> getSucursalList(String parametro) {
		return sucursalDao.SucursalList(parametro);
	}	

	public String deleteSucursal(Sucursal sucursal) {
		return sucursalDao.deleteById(sucursal.getId());
	}	
	
	public boolean existCategoriaProducto(Sucursal sucursal) {
		return sucursalDao.read(sucursal.getId()) != null;
	}	

	public Sucursal getSucursal(int selectedSucursald) {
		return DaoFactory.getFactory().getSucursalDao().read(selectedSucursald);
	}	
	

}
