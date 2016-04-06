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

	public PaisDao getPaisDao() {
		return new PaisDao();
	}

	
}
