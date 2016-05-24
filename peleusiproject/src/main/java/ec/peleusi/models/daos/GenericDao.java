package ec.peleusi.models.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

import ec.peleusi.utils.HibernateUtil;

public abstract class GenericDao<T, ID extends Serializable> {

	private Class<T> persistentClass;

	public GenericDao(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public String create(T entity) {
		String error = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();
			error = null;
		} catch (ConstraintViolationException err) {
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			err.printStackTrace();
			error = "Datos duplicados, ya existen en la base de datos";
		} catch (DataException err) {
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			err.printStackTrace();
			error = "Campos incorrectos, posible causa: sobrepasa número de carateres permitidos";
		} catch (HibernateException e) {
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			e.printStackTrace();
			error = "No se pudo guardar, consulte su administrador. Clave:" + e.getCause();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return error;
	}

	public T read(ID id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		T entity = null;
		try {
			session.beginTransaction();
			entity = (T) session.get(persistentClass, id);
			Hibernate.initialize(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return entity;
	}

	public String update(T entity) {
		String error = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
		} catch (ConstraintViolationException err) {
			if (session != null && session.isOpen()) {
				if (session.getTransaction() != null)
					session.getTransaction().rollback();
			}
			err.printStackTrace();
			error = "Datos duplicados, ya existen en la base de datos";
		} catch (DataException err) {
			if (session != null && session.isOpen()) {
				if (session.getTransaction() != null)
					session.getTransaction().rollback();
			}
			err.printStackTrace();
			error = "Campos incorrectos, posible causa: sobrepasa número de carateres permitidos";
		} catch (HibernateException e) {
			if (session != null && session.isOpen()) {
				if (session.getTransaction() != null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
			error = "No se pudo actualizar, consulte su administrador. Clave:" + e.getCause();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return error;
	}

	public String deleteById(ID id) {
		String error = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			T entity = (T) session.load(persistentClass, id);
			session.delete(entity);
			session.getTransaction().commit();
		} catch (ConstraintViolationException err) {
			if (session != null && session.isOpen()) {
				if (session.getTransaction() != null)
					session.getTransaction().rollback();
			}
			err.printStackTrace();
			error = "No se pudo eliminar, Datos estan siendo usados";
		} catch (HibernateException e) {
			if (session != null && session.isOpen()) {
				if (session.getTransaction() != null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
			error = "No se pudo eliminar, consulte su administrador. Clave:" + e.getCause();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return error;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<T> list = new ArrayList<T>();
		try {
			session.beginTransaction();
			list = session.createCriteria(persistentClass).list();
			Hibernate.initialize(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return list;
	}

	public void query(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
