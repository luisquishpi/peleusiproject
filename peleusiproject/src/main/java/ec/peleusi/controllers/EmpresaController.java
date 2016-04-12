package ec.peleusi.controllers;

import ec.peleusi.models.daos.EmpresaDao;

import java.util.List;

import ec.peleusi.models.daos.DaoFactory;

import ec.peleusi.models.entities.Empresa;;


public class EmpresaController {
	
	private EmpresaDao empresaDao;
	public EmpresaController(){
		empresaDao= new DaoFactory().getEmpresaDao();
		
	}
	
	public String createEmpresa(Empresa empresa){
		return empresaDao.create(empresa);
	}
	
	public List<Empresa> EmpresaList() {
		return empresaDao.findAll();
	}
	

}

