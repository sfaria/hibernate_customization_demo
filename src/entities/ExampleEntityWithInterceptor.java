package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
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
	@Temporal(value= TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@Column(name="CREATION_DATE", nullable=false)
	@Temporal(value= TemporalType.TIMESTAMP)
	private Date creationDate;

	// -------------------- Getters --------------------

	public Long getId() {
		return id;
	}

	// -------------------- Setters --------------------

	public void setABoolean(final Boolean aBoolean) {
		this.aBoolean = aBoolean ? "Y" : "N";
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

	@Override
	public final String toString() {
		final StringBuilder sb = new StringBuilder("ExampleEntityWithInterceptor {\n");
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
