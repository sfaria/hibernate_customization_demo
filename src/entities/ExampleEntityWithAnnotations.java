package entities;

import hibernate.BooleanType;
import hibernate.Handler;
import hibernate.PersistenceHook;
import org.hibernate.HibernateException;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

	@Column(name="A_BOOLEAN", nullable=false)
	@Type(type=BooleanType.NAME)
	private Boolean aBoolean;

	@PersistenceHook(handlerClass=LastUpdateHandler.class)
	@Column(name="LAST_UPDATE_DATE", nullable=false)
	@Temporal(value= TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@PersistenceHook(handlerClass=CreationDateHandler.class)
	@Column(name="CREATION_DATE", nullable=false)
	@Temporal(value= TemporalType.TIMESTAMP)
	private Date creationDate;

	// -------------------- Getters --------------------

	public Long getId() {
		return id;
	}
	// -------------------- Setters --------------------

	public void setABoolean(final Boolean aBoolean) {
		this.aBoolean = aBoolean;
	}

	// -------------------- Inner Classes --------------------

	public static final class UUIDHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return e.uuid == null ? UUID.randomUUID().toString() : e.uuid;
		}
	}

	public static final class LastUpdateHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return new Date();
		}
	}

	public static final class CreationDateHandler implements Handler {
		@Override
		public final Serializable handle(final ExampleEntityWithAnnotations e) throws HibernateException {
			return e.creationDate == null ? new Date() : e.creationDate;
		}
	}

	// -------------------- Overridden Methods --------------------

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ExampleEntityWithAnnotations that = (ExampleEntityWithAnnotations) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ExampleEntityWithAnnotations {\n");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sb.append("\tid=").append(id);
		sb.append("\n\t").append("uuid='").append(uuid).append('\'');
		sb.append("\n\t").append("aBoolean='").append(aBoolean).append('\'');
		sb.append("\n\t").append("lastUpdate=").append(formatter.format(lastUpdate));
		sb.append("\n\t").append("creationDate=").append(formatter.format(creationDate)).append("\n");
		sb.append('}');
		return sb.toString();
	}
}
