package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.utils.HibernateUtil;

public class ProveedorDao extends GenericDao<Proveedor, Integer> {

	public ProveedorDao() {
		super(Proveedor.class);

	}

	public Proveedor getProveedorIdentificacion(String identificacion) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Proveedor T WHERE T.identificacion = :identificacion");
			query.setParameter("identificacion", identificacion);
			if (!query.list().isEmpty()) {
				return (Proveedor) query.list().get(0);
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
