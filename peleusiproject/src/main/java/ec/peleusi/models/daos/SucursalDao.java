package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.utils.HibernateUtil;

public class SucursalDao extends GenericDao<Sucursal, Integer>{
	public SucursalDao(){
		super(Sucursal.class);
		
	}
	@SuppressWarnings("unchecked")
	public List<Sucursal> SucursalList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from Sucursal T WHERE CONCAT(T.id,T.empresa,T.nombre,T.ciudad,T.direccion,T.telefono,T.fax,T.email) LIKE CONCAT('%', :parametro, '%')");
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
	
	public boolean existSucursal(String nombre) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from Sucursal T WHERE T.nombre = :nombre");
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


