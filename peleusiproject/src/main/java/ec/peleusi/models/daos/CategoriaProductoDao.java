package ec.peleusi.models.daos;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.utils.HibernateUtil;

public class CategoriaProductoDao extends GenericDao<CategoriaProducto, Integer> {

	public CategoriaProductoDao() {
		super(CategoriaProducto.class);
	}

	public boolean existCategoriaProductoNuevo(Integer id, String nombre, Integer dependencia) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from CategoriaProducto T WHERE T.nombre = :nombre and T.dependencia=:dependencia ");
			query.setParameter("nombre", nombre);
			query.setParameter("dependencia", dependencia);
			if (!query.list().isEmpty()) {
				existe = true;
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
		return existe;
	}

	public boolean existCategoriaProductoActualizar(Integer id, String nombre, Integer dependencia) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from CategoriaProducto T WHERE id<>:id and T.nombre = :nombre and T.dependencia=:dependencia ");
			query.setParameter("id", id);
			query.setParameter("nombre", nombre);
			query.setParameter("dependencia", dependencia);

			if (!query.list().isEmpty()) {
				existe = true;
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
		return existe;
	}

	public boolean existCategoriaProducto(Integer id, String nombre, Integer dependencia) {
		Boolean exist = null;
		if (id == 0) {
			exist = existCategoriaProductoNuevo(id, nombre, dependencia);

		} else {
			if (existCategoriaProductoActualizar(id, nombre, dependencia) == false) {

				exist = false;
			} else {
				exist = existCategoriaProductoNuevo(id, nombre, dependencia);
			}
		}

		return exist;

	}
}
