package entities;

import hibernate.Handler;
import hibernate.PersistenceHook;
import org.hibernate.HibernateException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Scott Faria
 */
@Entity
@Table(name="ENTITY")
public class ExampleEntityWithAnnotations {

	// -------------------- Private Variables --------------------

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ENTITY_ID", unique=true, nullable=false)
	private Long id;

	@PersistenceHook(handlerClass=UUIDHandler.class)
	@Column(name="UUID", nullable=false, unique=true)
	private String uuid;

	@PersistenceHook(handlerClass=BooleanHandler.class)
	@Column(name="A_BOOLEAN", nullable=false)
	private boolean aBoolean;

	@PersistenceHook(handlerClass=LastUpdateHandler.class)
	@Column(name="LAST_UPDATE_DATE", nullable=false)
	private Date lastUpdate;

	@PersistenceHook(handlerClass=CreationDateHandler.class)
	@Column(name="CREATION_DATE", nullable=false)
	private Date creationDate;

	// -------------------- Inner Classes --------------------

	private static final class UUIDHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return e.uuid == null ? UUID.randomUUID().toString() : e.uuid;
		}
	}

	private static final class BooleanHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return e.aBoolean ? "T" : "F";
		}
	}

	private static final class LastUpdateHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return new Date();
		}
	}

	private static final class CreationDateHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return e.creationDate == null ? new Date() : e.creationDate;
		}
	}
}
