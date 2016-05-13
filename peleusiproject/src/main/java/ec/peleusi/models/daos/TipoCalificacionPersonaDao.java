package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.TipoCalificacionPersona;
import ec.peleusi.utils.HibernateUtil;

public class TipoCalificacionPersonaDao extends GenericDao<TipoCalificacionPersona, Integer> {
	public TipoCalificacionPersonaDao() {
		super(TipoCalificacionPersona.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoCalificacionPersona> TipoCalificacionPersonaList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from TipoCalificacionPersona T WHERE CONCAT(T.id,T.nombre) LIKE CONCAT('%', :parametro, '%')");
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
