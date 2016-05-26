package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoRetencionDao;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.HibernateUtil;

public class TipoRetencionController {

	private TipoRetencionDao tipoRetencionDao;

	public TipoRetencionController() {
		tipoRetencionDao = new DaoFactory().getTipoRetencionDao();
	}

	public String createTipoRetencion(TipoRetencion tipoRetencion) {
		return tipoRetencionDao.create(tipoRetencion);
	}
		public boolean existTipoRetencion(TipoRetencion tipoRetencion){
		  return tipoRetencionDao.read(tipoRetencion.getId()) !=null;
		}
		 
		
		
	public List<TipoRetencion> tipoRetencionList() {
		return  tipoRetencionDao.findAll();
	}
	
	public TipoRetencion geTipoRetencion(int id){
		return DaoFactory.getFactory().getTipoRetencionDao().read(id);
	}
	public String update(TipoRetencion tipoRetencion){
		return tipoRetencionDao.update(tipoRetencion);
	}
	public String delete(Integer id){
		return tipoRetencionDao.deleteById(id);
	}
	public TipoRetencion getTipoRetencion(String codigo){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery("from TipoRetencion T WHERE T.codigo = :codigo");
			query.setParameter("codigo", codigo);
			if(!query.list().isEmpty()){
				return (TipoRetencion) query.list().get(0);
			}
			session.getTransaction().commit();
		} catch(HibernateException e){
			if(session.getTransaction() !=null)
				e.printStackTrace();
		}finally{
			if (session !=null && session.isOpen()){
				session.close();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<TipoRetencion> tipoRetencionList(String parametro){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			
				session.beginTransaction();
				Query query = session.createQuery("from TipoRetencion T WHERE CONCAT(T.codigo,T.descripcion) LIKE CONCAT('%',:parametro, '%')");
				query.setParameter("parametro", parametro);
				if(!query.list().isEmpty()){
					return query.list();
				}
				session.getTransaction().commit();
		}catch(HibernateException e){
			   if(session.getTransaction() !=null)
				   e.printStackTrace();
		}finally{
				if(session !=null&&session.isOpen()){
					     session.close();
				}
		}
		return null;
	}
}
