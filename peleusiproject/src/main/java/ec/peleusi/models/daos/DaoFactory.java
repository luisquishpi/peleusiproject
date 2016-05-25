package ec.peleusi.models.daos;

public class DaoFactory {
	public static DaoFactory factory = null;

	public static void setFactory(DaoFactory factory) {
		DaoFactory.factory = factory;
	}

	public static DaoFactory getFactory() {
		if (factory == null) {
			factory = new DaoFactory();
		}
		return factory;
	}

	public CiudadDao getCiudadDao() {
		return new CiudadDao();
	}

	public UnidadMedidaDao getUnidadMedidaDao() {
		return new UnidadMedidaDao();
	}

	public CategoriaProductoDao getCategoriaProductoDao() {
		return new CategoriaProductoDao();
	}

	public TipoGastoDeducibleDao getTipoGastoDeducibleDao() {
		return new TipoGastoDeducibleDao();
	}

	public EmpresaDao getEmpresaDao() {
		return new EmpresaDao();
	}

	public TarifaIvaDao getTarifaIvaDao() {
		return new TarifaIvaDao();
	}

	public TarifaIceDao getTarifaIceDao() {
		return new TarifaIceDao();
	}

	public ProductoDao getProductoDao() {
		return new ProductoDao();
	}

	public ProductoPrecioDao getPrecioProductoDao() {
		return new ProductoPrecioDao();
	}

	public TipoIdentificacionDao getTipoIdentificacionDao() {
		return new TipoIdentificacionDao();
	}

	public TipoPrecioDao getTipoPrecioDao() {
		return new TipoPrecioDao();
	}

	public SucursalDao getSucursalDao() {
		return new SucursalDao();
	}

	public TipoCalificacionClienteDao getTipoCalificacionClienteDao() {
		return new TipoCalificacionClienteDao();
	}

	public TipoRetencionDao getTipoRetencionDao() {
		return new TipoRetencionDao();
	}

	public TipoPagoDao getTipoPagoDao() {
		return new TipoPagoDao();
	}

	public UsuarioDao getUsuarioDao() {
		return new UsuarioDao();
	}

	public CompraDao getCompraDao() {
		return new CompraDao();
	}

	public CompraDetalleDao getDetalleCompraDao() {
		return new CompraDetalleDao();
	}

	public SeteoDao getSeteoDao() {
		return new SeteoDao();
	}

	public ClienteDao getClienteDao() {
		return new ClienteDao();
	}

	public ProveedorDao getProveedorDao() {
		return new ProveedorDao();
	}

	public DireccionProveedorDao getDireccionProveedorDao() {
		return new DireccionProveedorDao();
	}

	public CajaDao getCajaDao() {
		return new CajaDao();
	}

}
