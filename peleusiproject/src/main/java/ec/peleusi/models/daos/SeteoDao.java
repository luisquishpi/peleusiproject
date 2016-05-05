package ec.peleusi.models.daos;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.Seteo;
import ec.peleusi.utils.HibernateUtil;;

public class SeteoDao extends GenericDao<Seteo, Integer> {

	public SeteoDao() {

		super(Seteo.class);
	}
	
	public Seteo getSeteo(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Seteo T WHERE T.id != :id");
			query.setParameter("id", id);
			if (!query.list().isEmpty()) {
				return (Seteo) query.list().get(0);
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
