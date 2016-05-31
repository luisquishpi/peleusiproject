package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.Producto;

import ec.peleusi.utils.HibernateUtil;

public class ProductoDao extends GenericDao<Producto, Integer> {

	public ProductoDao() {
		super(Producto.class);
	}
	
	public Producto getProductoCodigo(String codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Producto T WHERE T.codigo = :codigo");
			query.setParameter("codigo", codigo);
			if (!query.list().isEmpty()) {
				return (Producto) query.list().get(0);
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
	public List<Producto> ProductoList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Producto T WHERE CONCAT(T.id,T.codigo,T.nombre) LIKE CONCAT('%', :parametro, '%')");
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
