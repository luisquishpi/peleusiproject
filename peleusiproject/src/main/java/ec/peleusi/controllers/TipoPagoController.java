package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.daos.TipoPagoDao;
import ec.peleusi.models.entities.TipoPago;
import ec.peleusi.utils.HibernateUtil;

public class TipoPagoController {
	private TipoPagoDao tipoPagoDao;

	public TipoPagoController() {
		tipoPagoDao = new DaoFactory().getTipoPagoDao();
	}
	public String createTipoPago(TipoPago tipoPago)
	{
		return tipoPagoDao.create(tipoPago);
	}
		public boolean existtTipoPago(TipoPago tipoPago){
			return tipoPagoDao.read(tipoPago.getId()) !=null;
		}
	public List<TipoPago> tipoPagoList() {
		return tipoPagoDao.findAll();
	}
	public TipoPago geTipoPago(int id){
		return DaoFactory.getFactory().getTipoPagoDao().read(id);
	}
	public boolean update(TipoPago tipoPago){
		return tipoPagoDao.update(tipoPago);
	}
	public boolean delete(Integer id){
		return tipoPagoDao.deleteById(id);
	}
	public TipoPago getTipoPago(String nombre ){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery("from TipoPago T WHERE T.nombre =:nombre");  //Revisar
			query.setParameter("nombre", nombre);  //codigo
			if(!query.list().isEmpty()){
				return (TipoPago) query.list().get(0);
			}
			session.getTransaction().commit();
		} catch(HibernateException e){
			if(session.getTransaction() !=null)
				e.printStackTrace();
		}finally{
			if(session !=null && session.isOpen()){
				session.close();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<TipoPago> tipoPagoList (String parametro){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			session.beginTransaction();
			Query query = session.createQuery("from TipoPago T WHERE CONCAT(T.nombre) LIKE CONCAT('%',:parametro, '%')"); 
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
