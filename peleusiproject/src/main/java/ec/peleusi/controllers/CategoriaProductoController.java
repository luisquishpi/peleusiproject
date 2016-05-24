package ec.peleusi.controllers;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.daos.CategoriaProductoDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.utils.HibernateUtil;

public class CategoriaProductoController {

	private CategoriaProductoDao categoriaProductoDao;

	public CategoriaProductoController() {
		categoriaProductoDao = new DaoFactory().getCategoriaProductoDao();
	}

	public void saveCategoriaProducto(CategoriaProducto categoriaProducto) {
		categoriaProductoDao.create(categoriaProducto);
	}

	public boolean existCategoriaProducto(CategoriaProducto categoriaProducto) {
		return categoriaProductoDao.read(categoriaProducto.getId()) != null;
	}

	public List<CategoriaProducto> CategoriaProductoList() {
		return categoriaProductoDao.findAll();
	}

	public CategoriaProducto getCateriaProducto(int selectedCategoriaProductoId) {
		return DaoFactory.getFactory().getCategoriaProductoDao().read(selectedCategoriaProductoId);
	}

	public String updateCategoriaProducto(CategoriaProducto categoriaProducto) {
		return categoriaProductoDao.update(categoriaProducto);

	}

	public boolean delete(Integer id) {
		return categoriaProductoDao.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	public List<CategoriaProducto> CategoriaProductoList(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CategoriaProducto> lista = null;

		try {
			session.beginTransaction();
			Query query = session.createQuery("from CategoriaProducto C WHERE C.dependencia=:id");
			query.setParameter("id", id);
			lista = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return lista;
	}

	public boolean existCategoriaProducto(String nombre) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from CategoriaProducto T WHERE T.nombre = :nombre and T.dependencia=:dependencia ");
			query.setParameter("nombre", nombre);
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
}
