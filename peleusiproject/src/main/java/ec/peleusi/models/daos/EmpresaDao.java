package ec.peleusi.models.daos;
import ec.peleusi.models.entities.Empresa;

public class EmpresaDao extends GenericDao< Empresa, Integer>{
	public EmpresaDao(){
		super(Empresa.class);
	}

}

