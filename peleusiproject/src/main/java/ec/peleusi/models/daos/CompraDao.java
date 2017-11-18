package ec.peleusi.models.daos;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.Compra;
import ec.peleusi.utils.HibernateUtil;

public class CompraDao extends GenericDao<Compra, Integer>{
	public CompraDao(){
		super(Compra.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Compra> CompraList(String parametro, Date fechaInicio, Date fechaFinal, Boolean estado) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		System.out.println("Fecha");
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from Compra T WHERE CONCAT(T.establecimiento,T.secuencial,T.puntoEmision,T.proveedor.identificacion,T.proveedor.razonSocial) LIKE CONCAT('%', :parametro, '%') and T.estado=:estado and T.fechaEmision>:fechaInicio AND T.fechaEmision<:fechaFinal");
			query.setParameter("parametro", parametro);		
			query.setParameter("fechaInicio", fechaInicio);
			query.setParameter("fechaFinal", fechaFinal);
			query.setParameter("estado", estado);		
			System.out.println("Fecha11");
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
	
	public List<Compra> CompraEstadoList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Boolean estado=true;
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from Compra T WHERE CONCAT(T.establecimiento,T.secuencial,T.puntoEmision,T.proveedor.identificacion,T.proveedor.razonSocial) LIKE CONCAT('%', :parametro, '%') and T.estado=:estado ");
			query.setParameter("parametro", parametro);
			query.setParameter("estado", estado);
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


