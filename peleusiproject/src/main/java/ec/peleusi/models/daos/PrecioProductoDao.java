package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.PrecioProducto;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.utils.HibernateUtil;

public class PrecioProductoDao extends GenericDao<PrecioProducto, Integer> {

	public PrecioProductoDao() {
		super(PrecioProducto.class);
	}

	@SuppressWarnings("unchecked")
	public List<PrecioProducto> precioProductoList(Producto producto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from PrecioProducto T WHERE T.producto = :producto");
			query.setParameter("producto", producto);
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
