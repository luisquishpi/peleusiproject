package ec.peleusi.models.daos;
import ec.peleusi.models.entities.CategoriaProducto;

public class CategoriaProductoDao extends GenericDao<CategoriaProducto, Integer>{

	public CategoriaProductoDao()
	{
		super(CategoriaProducto.class);
	}
}

