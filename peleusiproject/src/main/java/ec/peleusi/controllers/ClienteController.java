package ec.peleusi.controllers;

import java.util.List;
import ec.peleusi.models.daos.ClienteDao;
import ec.peleusi.models.daos.DaoFactory;
import ec.peleusi.models.entities.Cliente;

public class ClienteController {
	
	private ClienteDao clienteDao;

	public ClienteController() {
		clienteDao = new DaoFactory().getClienteDao();
	}

	public String createCliente(Cliente cliente) {
		return clienteDao.create(cliente);
	}
	
	public String updateCliente(Cliente cliente) {
		return clienteDao.update(cliente);
	}
	
	public List<Cliente> ClienteList() {
		return clienteDao.findAll();
	}
	
	public List<Cliente> getClienteList(String parametro) {
		return clienteDao.ClienteList(parametro);
	}
		
	public String deleteCliente(Cliente cliente) {
		return clienteDao.deleteById(cliente.getId());
	}
	
	public boolean existCliente(Cliente cliente) {
		return clienteDao.read(cliente.getId()) != null;
	}	
	
	public Cliente getCliente(int id) {
		return DaoFactory.getFactory().getClienteDao().read(id);
	}
	
	public Cliente getClienteIdentificacion(String identificacion)
	{
		return clienteDao.getClienteIdentificacion(identificacion);		
		
	}
	
	

}
