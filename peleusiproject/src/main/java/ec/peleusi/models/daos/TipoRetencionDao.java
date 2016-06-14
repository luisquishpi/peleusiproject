package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.HibernateUtil;
import ec.peleusi.utils.TipoRetencionEnum;

public class TipoRetencionDao extends GenericDao<TipoRetencion, Integer> {
	public TipoRetencionDao() {
		super(TipoRetencion.class);
	}
	
	@SuppressWarnings("unchecked")
	
	public List<TipoRetencion> tipoRetencionTipoList(TipoRetencionEnum tipo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<TipoRetencion> lista = null;

		try {
			session.beginTransaction();  
			Query query = session.createQuery("from TipoRetencion C WHERE C.tipo=:tipo");
			query.setParameter("tipo", tipo);
			lista = query.list();
			session.getTransaction().commit();
			System.out.println("--->------- " + tipo);
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
