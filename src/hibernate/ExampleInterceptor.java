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
	public final boolean onSave(final Object entity, final Serializable id, final Object[] state, final String[] propertyNames, final Type[] types) {
		if (entity instanceof ExampleEntityWithInterceptor) {
			return handleEvent(state, propertyNames);
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public final boolean onFlushDirty(final Object entity, final Serializable id, final Object[] currentState, final Object[] previousState, final String[] propertyNames, final Type[] types) {
		if (entity instanceof ExampleEntityWithInterceptor) {
			return handleEvent(currentState, propertyNames);
		}
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	// -------------------- Private Methods --------------------

	private boolean handleEvent(final Object[] state, final String[] propertyNames) {
		boolean modified = false;
		for (int index = 0; index < state.length; index++) {
			Object currentState = state[index];
			String property = propertyNames[index];
			switch (property) {
				case "creationDate":
					if (currentState == null) {
						state[index] = new Date();
						modified = true;
					}
					break;
				case "lastUpdate":
					state[index] = new Date();
					modified = true;
					break;
				case "uuid":
					if (currentState == null) {
						state[index] = UUID.randomUUID().toString();
						modified = true;
					}
					break;
			}

		}
		return modified;
	}
}
