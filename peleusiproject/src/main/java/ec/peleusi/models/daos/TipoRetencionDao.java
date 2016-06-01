package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.HibernateUtil;

public class TipoRetencionDao extends GenericDao<TipoRetencion, Integer> {
	public TipoRetencionDao() {
		super(TipoRetencion.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoRetencion> tipoRetencionList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from TipoRetencion T WHERE CONCAT(T.id,T.codigo,T.porcentaje,T.descripcion) LIKE CONCAT('%',:parametro, '%')");
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

	public TipoRetencion getTipoRetencion(String codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from TipoRetencion T WHERE T.codigo = :codigo");
			query.setParameter("codigo", codigo);
			if (!query.list().isEmpty()) {
				return (TipoRetencion) query.list().get(0);
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
