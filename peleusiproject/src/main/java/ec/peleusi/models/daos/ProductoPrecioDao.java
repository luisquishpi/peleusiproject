package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.ProductoPrecio;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.utils.HibernateUtil;

public class ProductoPrecioDao extends GenericDao<ProductoPrecio, Integer> {

	public ProductoPrecioDao() {
		super(ProductoPrecio.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProductoPrecio> productoPrecioList(Producto producto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from ProductoPrecio T WHERE T.producto = :producto");
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

	@SuppressWarnings("unchecked")
	public List<ProductoPrecio> productoPrecioListCodigo(Producto producto, TipoPrecio tipoPrecio) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from ProductoPrecio T WHERE T.producto = :producto and T.tipoPreco=:tipoPrecio");
			query.setParameter("producto", producto).setParameter("tipoPrecio", tipoPrecio);
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

	@SuppressWarnings("unchecked")
	public List<ProductoPrecio> productoPrecioListPorTipoPrecio(String txtBuscar, TipoPrecio tipoPrecio) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			// Query query = session.createQuery("SELECT p,pp from
			// ProductoPrecio as pp inner join pp.producto as p where
			// pp.tipoPrecio=:parametro");
			Query query = session.createQuery(
					"SELECT pp from ProductoPrecio as pp WHERE pp.producto.nombre LIKE CONCAT('%', :txtBuscar, '%') and pp.tipoPrecio=:tipoPrecio");
			query.setParameter("tipoPrecio", tipoPrecio).setParameter("txtBuscar", txtBuscar);
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
