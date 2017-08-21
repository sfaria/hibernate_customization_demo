package hibernate;

import entities.ExampleEntityWithAnnotations;
import org.hibernate.HibernateException;

import java.io.Serializable;

/**
 * @author Scott Faria
 */
public interface Handler {
	Serializable handle(ExampleEntityWithAnnotations e) throws HibernateException;
}
