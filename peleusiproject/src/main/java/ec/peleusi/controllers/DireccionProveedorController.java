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

	public String createDireccionProveedor(DireccionProveedor direccionProveedor) {
		return direccionProveedorDao.create(direccionProveedor);
	}
	
	public String updateDireccionProveedor(DireccionProveedor direccionProveedor) {
		return direccionProveedorDao.update(direccionProveedor);
	}	
	
	public String deleteDireccionProveedor(DireccionProveedor direccionProveedor) {
		return direccionProveedorDao.deleteById(direccionProveedor.getId());
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
	
	public DireccionProveedor getDireccionProveedorPorDefecto(Proveedor proveedor)
	{
		return direccionProveedorDao.getDireccionProveedorPorDefecto(proveedor);
	}
	
}
