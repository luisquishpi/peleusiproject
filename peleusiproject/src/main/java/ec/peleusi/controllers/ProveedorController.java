package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.ProveedorDao;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.utils.HibernateUtil;

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

	@SuppressWarnings("unchecked")
	public List<Proveedor> ProveedorList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from Proveedor T WHERE CONCAT(T.identificacion,T.razonSocial,T.tipoIdentificacion.nombre) LIKE CONCAT('%', :parametro, '%')");
			query.setParameter("parametro", parametro);
			if (!query.list().isEmpty()) {
				return query.list();
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
		return null;
	}

}
