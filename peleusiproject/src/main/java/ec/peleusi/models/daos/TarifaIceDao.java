package ec.peleusi.models.daos;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.utils.HibernateUtil;

public class TarifaIceDao extends GenericDao<TarifaIce, Integer> {

	public TarifaIceDao() {
		super(TarifaIce.class);
	}
	

	@SuppressWarnings("unchecked")
	public List<TarifaIce> TarifaIceList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from TarifaIce T WHERE CONCAT(T.id,T.codigo,T.nombre,T.porcentaje) LIKE CONCAT('%', :parametro, '%')");
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
