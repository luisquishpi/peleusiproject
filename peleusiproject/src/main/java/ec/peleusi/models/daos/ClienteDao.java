 package ec.peleusi.models.daos;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.utils.HibernateUtil;


public class ClienteDao extends GenericDao<Cliente, Integer>{

	public ClienteDao() {
		super(Cliente.class);
		
	}		
	public Cliente getPersonaIdentificacion(String identificacion) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Persona T WHERE T.identificacion = :identificacion");
			query.setParameter("identificacion", identificacion);
			if (!query.list().isEmpty()) {
				return (Cliente) query.list().get(0);
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
