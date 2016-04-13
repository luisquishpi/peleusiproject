package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.SucursalDao;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.utils.HibernateUtil;

public class SucursalController {
	
	
	private SucursalDao sucursalDao;

	public SucursalController() {
		sucursalDao = new DaoFactory().getSucursalDao();
	}

	public void saveSucursal(Sucursal sucursal) {
		sucursalDao.create(sucursal);
	}

	public boolean existCategoriaProducto(Sucursal sucursal) {
		return sucursalDao.read(sucursal.getId()) != null;
	}

	public List<Sucursal> SucursalList() {
		return sucursalDao.findAll();
	}

	public Sucursal getSucursal(int selectedSucursald) {
		return DaoFactory.getFactory().getSucursalDao().read(selectedSucursald);
	}

	public boolean updateSucursal(Sucursal sucursal) {
		return sucursalDao.update(sucursal);

	}

	public boolean delete(Integer id) {
		return sucursalDao.deleteById(id);
	}

	
	public boolean existSucursal(String nombre) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Sucursal T WHERE T.nombre = :nombre");
			query.setParameter("nombre", nombre);
			if (!query.list().isEmpty()) {
				existe = true;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null)
				e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return existe;
	}

}
