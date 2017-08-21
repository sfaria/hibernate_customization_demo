package entities;

import hibernate.ExampleEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Scott Faria
 */
@Entity
@Table(name="ENTITY")
@EntityListeners(value=ExampleEntityListener.class)
public class ExampleEntityWithListener {

	// -------------------- Private Variables --------------------

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="ENTITY_ID", unique=true, nullable=false)
	private Long id;

	@Column(name="UUID", nullable=false, unique=true)
	private String uuid;

	@Column(name="A_BOOLEAN", nullable=false)
	private Boolean aBoolean;

	@Column(name="LAST_UPDATE_DATE", nullable=false)
	private Date lastUpdate;

	@Column(name="CREATION_DATE", nullable=false)
	private Date creationDate;

	// -------------------- Public Methods --------------------

	public void setUUID(final String uuid) {
		this.uuid = uuid;
	}

	public void setaBoolean(final Boolean aBoolean) {
		this.aBoolean = aBoolean;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	// -------------------- Overridden Methods --------------------

	@Override
	public final boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ExampleEntityWithListener that = (ExampleEntityWithListener) o;
		return id.equals(that.id);
	}

	@Override
	public final int hashCode() {
		return id.hashCode();
	}
}
