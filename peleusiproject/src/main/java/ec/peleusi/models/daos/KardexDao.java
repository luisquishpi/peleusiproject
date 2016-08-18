package ec.peleusi.models.daos;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.Kardex;
import ec.peleusi.utils.HibernateUtil;

public class KardexDao extends GenericDao<Kardex, Integer> {

	public KardexDao() {
		super(Kardex.class);
	}

	@SuppressWarnings("unchecked")
	public List<Kardex> KardexList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from Kardex T WHERE CONCAT(T.id,T.producto,T.activo,T.fecha,T.detalle,T.cantidadEntrada,T.precioUnitarioEntrada,T.precioTotalEntrada,T.cantidadSalida,T.precioUnitarioSalida,T.precioTotalSalida,T.cantidadSaldo,T.precioUnitarioSaldo,T.precioTotalSaldo) LIKE CONCAT('%', :parametro, '%')");
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
