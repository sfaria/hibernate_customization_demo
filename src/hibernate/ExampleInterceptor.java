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
			boolean modified = false;
			for (int index = 0; index < state.length; index++) {
				Object currentState = state[index];
				String property = propertyNames[index];
				switch (property) {
					case "creationDate":
						if (currentState == null) {
							state[index] = new Date();
						}
						modified = state[index] != currentState;
						break;
					case "lastUpdate":
						state[index] = new Date();
						modified = true;
						break;
					case "uuid":
						if (currentState == null) {
							state[index] = UUID.randomUUID().toString();
						}
						modified = state[index] != currentState;
						break;
				}

			}
			return modified;
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}
}
