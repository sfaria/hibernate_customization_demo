package hibernate;

import entities.ExampleEntityWithInterceptor;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Scott Faria
 */
public final class ExampleInterceptor extends EmptyInterceptor {

	// -------------------- Overridden Methods --------------------

	@Override
	public boolean onSave(final Object entity, final Serializable id, final Object[] state, final String[] propertyNames, final Type[] types) {
		if (entity instanceof ExampleEntityWithInterceptor) {
			ExampleEntityWithInterceptor e = (ExampleEntityWithInterceptor) entity;
			e.setLastUpdate(new Date());
			if (e.getUuid() == null) {
				e.setUUID(UUID.randomUUID().toString());
			}
			if (e.getCreationDate() == null) {
				e.setCreationDate(new Date());
			}
			return true;
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}
}
