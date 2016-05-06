package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.UnidadMedidaDao;
import ec.peleusi.models.entities.UnidadMedida;
import ec.peleusi.utils.HibernateUtil;

public class UnidadMedidaController {

	private UnidadMedidaDao unidadMedidaDao;

	public UnidadMedidaController() {
		unidadMedidaDao = new DaoFactory().getUnidadMedidaDao();
	}

	public String createUnidadMedida(UnidadMedida unidadMedida) {
		return unidadMedidaDao.create(unidadMedida);
	}

	public List<UnidadMedida> UnidadMedidaList() {
		return unidadMedidaDao.findAll();
	}

	public UnidadMedida getunidadMedida(int id) {
		return DaoFactory.getFactory().getUnidadMedidaDao().read(id);
	}
	
	public UnidadMedida getUnidadMedidaIdentificacion(String id) {
		return unidadMedidaDao.getUnidadMedidaIdentificacion(id);

	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadMedida> UnidadMedidaList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from UnidadMedida T WHERE CONCAT(T.id,T.nombre,T.abreviatura) LIKE CONCAT('%', :parametro, '%')");
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
