package ec.peleusi.controllers;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.ProveedorDao;
import ec.peleusi.models.entities.Proveedor;

public class ProveedorController {

	private ProveedorDao proveedorDao;

	public ProveedorController() {
		proveedorDao = new DaoFactory().getProveedorDao();
	}

	public String createPersona(Proveedor proveedor) {
		return proveedorDao.create(proveedor);

	}

	public List<Proveedor> ProveedorList() {
		return proveedorDao.findAll();
	}

	public Proveedor getProveedorIdentificacion(String identificacion) {
		return proveedorDao.getProveedorIdentificacion(identificacion);

	}

	public List<Proveedor> ProveedoresList(String identificacion) {
		return proveedorDao.findAll();
	}

	public Proveedor getProveedor(int id) {
		return DaoFactory.getFactory().getProveedorDao().read(id);
	}
	
	public List<Proveedor> getProveedorList(String parametro) {
		return proveedorDao.ProveedorList(parametro);
	}
	

	
}
