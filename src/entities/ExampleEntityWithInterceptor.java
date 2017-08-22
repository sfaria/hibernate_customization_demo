package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class ExampleEntityWithInterceptor {

	// -------------------- Private Variables --------------------

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="ENTITY_ID", unique=true, nullable=false)
	private Long id;

	@Column(name="UUID", nullable=false, unique=true)
	private String uuid;

	@Column(name="A_BOOLEAN", nullable=false)
	private String aBoolean;

	@Column(name="LAST_UPDATE_DATE", nullable=false)
	private Date lastUpdate;

	@Column(name="CREATION_DATE", nullable=false)
	private Date creationDate;

	// -------------------- Getters --------------------

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public Boolean getABoolean() {
		return "Y".equals(aBoolean);
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	// -------------------- Setters --------------------

	public void setABoolean(final Boolean aBoolean) {
		this.aBoolean = aBoolean ? "Y" : "N";
	}

	public void setUUID(final String uuid) {
		this.uuid = uuid;
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
		final ExampleEntityWithInterceptor that = (ExampleEntityWithInterceptor) o;
		return id.equals(that.id);
	}

	@Override
	public final int hashCode() {
		return id.hashCode();
	}
}
