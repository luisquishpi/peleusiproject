package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.HibernateUtil;

public class TipoPrecioDao extends GenericDao<TipoPrecio, Integer> {

	public TipoPrecioDao() {
		super(TipoPrecio.class);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TipoPrecio> TipoPrecioList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from TipoPrecio T WHERE CONCAT(T.id,T.nombre,T.porcentaje) LIKE CONCAT('%', :parametro, '%')");
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
