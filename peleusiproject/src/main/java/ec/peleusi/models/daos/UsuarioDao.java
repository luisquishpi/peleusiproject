package ec.peleusi.models.daos;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import ec.peleusi.models.entities.Usuario;
import ec.peleusi.utils.HibernateUtil;

public class UsuarioDao extends GenericDao<Usuario, Integer> {

	public UsuarioDao() {
		super(Usuario.class);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> UsuarioList(String parametro) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"from Usuario T WHERE CONCAT(T.id,T.nombres,T.apellidos,T.usuario,T.contrasenia,T.tipoUsuario) LIKE CONCAT('%', :parametro, '%')");
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
