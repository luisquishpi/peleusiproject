package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.PersonaDao;
import ec.peleusi.models.entities.Persona;
import ec.peleusi.utils.HibernateUtil;


public class PersonaController {
	private PersonaDao personaDao;

	public PersonaController() {

		personaDao = new DaoFactory().getPersonaDao();
	}

	public String createPersona(Persona persona) {
		return personaDao.create(persona);

	}
	
	public boolean existPersona(Persona persona) {
		return personaDao.read(persona.getId()) != null;
	}
	
	public List<Persona> PersonaList() {
		return personaDao.findAll();
	}
	
	public Persona getPersona(int id) {
		return DaoFactory.getFactory().getPersonaDao().read(id);
	}
	
	
	public boolean update(Persona persona) {
		return personaDao.update(persona);
	}

	public boolean delete(Integer id) {
		return personaDao.deleteById(id);
	}
	
	public Persona getPersona(String codigo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Persona T WHERE T.codigo = :codigo");
			query.setParameter("codigo", codigo);
			if (!query.list().isEmpty()) {
				return (Persona) query.list().get(0);
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
	public List<Persona> PersonaList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Persona T WHERE CONCAT(T.identificacion,T.razonSocial,T.tipoPrecio.nombre) LIKE CONCAT('%', :parametro, '%')");
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
