package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.entities.Compra;
import ec.peleusi.models.entities.CompraTipoPago;
import ec.peleusi.utils.HibernateUtil;

public class CompraTipoPagoDao  extends GenericDao<CompraTipoPago, Integer>{
	public CompraTipoPagoDao()
	{
		
		super(CompraTipoPago.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompraTipoPago> compraTipoPagoList(Compra compra) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CompraTipoPago> lista = null;

		try {
			session.beginTransaction();  
			Query query = session.createQuery("from CompraTipoPago C WHERE C.compra=:compra");
			query.setParameter("compra", compra);
			lista = query.list();
			session.getTransaction().commit();
			System.out.println("--->------- " + compra);
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

}





