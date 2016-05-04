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

	public Proveedor getPersonaIdentificacion(String identificacion) {
		return proveedorDao.getProveedorIdentificacion(identificacion);

	}

}
