package ec.peleusi.controllers;

import ec.peleusi.models.daos.DireccionProveedorDao;
import java.util.List;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.DireccionProveedor;
import ec.peleusi.models.entities.Proveedor;

public class DireccionProveedorController {

	private DireccionProveedorDao direccionProveedorDao;

	public DireccionProveedorController() {
		direccionProveedorDao = new DaoFactory().getDireccionProveedorDao();
	}

	public String createDireccionPersona(DireccionProveedor direccionProveedor) {
		return direccionProveedorDao.create(direccionProveedor);
	}
	
	public List<DireccionProveedor> direccionProveedorList() {
		return direccionProveedorDao.findAll();
	}
	
	public List<DireccionProveedor> direccionProveedorIdList(Proveedor proveedor) {
		return direccionProveedorDao.DireccionProveedorList(proveedor);
	}			
	public DireccionProveedor getDireccionProveedor(int id) {
		return DaoFactory.getFactory().getDireccionProveedorDao().read(id);
	}	
	
	public boolean update(DireccionProveedor direccionProveedor) {
		return direccionProveedorDao.update(direccionProveedor);
	}
	
	public DireccionProveedor getDireccionProveedorPorDefecto(Proveedor proveedor)
	{
		return direccionProveedorDao.getDireccionProveedorPorDefecto(proveedor);
	}
	
}
