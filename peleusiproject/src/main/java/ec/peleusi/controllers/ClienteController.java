package ec.peleusi.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.peleusi.models.daos.ClienteDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.utils.HibernateUtil;

public class ClienteController {
	
	private ClienteDao clienteDao;

	public ClienteController() {

		clienteDao = new DaoFactory().getClienteDao();
	}

	public String createCliente(Cliente cliente) {
		return clienteDao.create(cliente);

	}
	public Boolean deleteCliente(Integer id) {
		return clienteDao.deleteById(id);

	}
	
	public boolean existCliente(Cliente cliente) {
		return clienteDao.read(cliente.getId()) != null;
	}
	
	public List<Cliente> ClienteList() {
		return clienteDao.findAll();
	}
	
	public Cliente getCliente(int id) {
		return DaoFactory.getFactory().getClienteDao().read(id);
	}
	
	
	public String update(Cliente cliente) {
		return clienteDao.update(cliente);
	}
	
	public Cliente getClienteIdentificacion(String identificacion)
	{
		return clienteDao.getClienteIdentificacion(identificacion);		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> ClienteList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Cliente T WHERE CONCAT(T.identificacion,T.razonSocial,T.tipoPrecio.nombre) LIKE CONCAT('%', :parametro, '%')");
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
