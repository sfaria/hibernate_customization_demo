package hibernate;

import entities.ExampleEntityWithAnnotations;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Scott Faria
 */
public final class ExampleAnnotationBasedInterceptor extends EmptyInterceptor {

	// -------------------- Overridden Methods --------------------

	@Override
	public final boolean onSave(final Object entity, final Serializable id, final Object[] state, final String[] propertyNames, final Type[] types) {
		if (entity instanceof ExampleEntityWithAnnotations) {
			ExampleEntityWithAnnotations e = (ExampleEntityWithAnnotations) entity;
			return handleEvent(state, propertyNames, e);
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}



	@Override
	public final boolean onFlushDirty(final Object entity, final Serializable id, final Object[] currentState, final Object[] previousState, final String[] propertyNames, final Type[] types) {
		if (entity instanceof ExampleEntityWithAnnotations) {
			ExampleEntityWithAnnotations e = (ExampleEntityWithAnnotations) entity;
			return handleEvent(currentState, propertyNames, e);
		}
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	// -------------------- Private Methods --------------------

	private boolean handleEvent(final Object[] state, final String[] propertyNames, final ExampleEntityWithAnnotations entity) throws HibernateException {
		try {
			Map<String, Field> fieldsByName = Arrays.stream(entity.getClass().getDeclaredFields())
					.collect(Collectors.toMap(Field::getName, Function.identity()));

			boolean modified = false;
			for (int index = 0; index < propertyNames.length; index++) {
				String propertyName = propertyNames[index];
				Field f = fieldsByName.get(propertyName);
				if (f != null) {
					PersistenceHook annotation = f.getAnnotation(PersistenceHook.class);
					if (annotation != null) {
						Class<? extends Handler> cls = annotation.handlerClass();
						Handler handler = cls.newInstance();
						Serializable newState = handler.handle(entity);
						if (newState != state[index]) {
							modified = true;
							state[index] = newState;
						}
					}
				}
			}
			return modified;
		} catch (IllegalAccessException | InstantiationException e) {
			throw new HibernateException("Unexpected failure.", e);
		}
	}

}
