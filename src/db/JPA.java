package db;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author Scott Faria
 */
public final class JPA {

	// -------------------- Private Statics --------------------

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
	static {
		ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("test");
	}

	// -------------------- Public Static Methods --------------------

	public static final <T> T execute(F<EntityManager, T> function, Interceptor interceptor) {
		EntityManager em = createEntityManager(interceptor);
		EntityTransaction transaction = em.getTransaction();
		try {
			T result = function.apply(em);
			em.close();
			return result;
		} catch (Exception ex) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			em.close();
			throw new RuntimeException(ex);
		}
	}

	public static final <T> T execute(F<EntityManager, T> function) {
		return execute(function, null);
	}

	// -------------------- Private Methods --------------------

	private static EntityManager createEntityManager(Interceptor interceptor) {
		SessionFactory sf = ENTITY_MANAGER_FACTORY.unwrap(SessionFactory.class);
		return sf.withOptions()
				.interceptor(interceptor)
				.openSession();
	}

	// -------------------- Inner Classes --------------------

	public interface F<EntityManager, T> {
		T apply(EntityManager em) throws Exception;
	}

	// -------------------- Constructors --------------------

	private JPA() {}
}
