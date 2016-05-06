package ec.peleusi.controllers;

import ec.peleusi.models.daos.DaoFactory;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.daos.CiudadDao;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.utils.HibernateUtil;


public class CiudadController {
	private CiudadDao ciudadDao;

	public CiudadController() {
		ciudadDao = new DaoFactory().getCiudadDao();
	}

	public String createCiudad(Ciudad ciudad) {
		return ciudadDao.create(ciudad);
	}
	
	public List<Ciudad> CiudadList() {
		return ciudadDao.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<Ciudad> CiudadList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Ciudad T WHERE CONCAT(T.id,T.nombre) LIKE CONCAT('%', :parametro, '%')");
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
