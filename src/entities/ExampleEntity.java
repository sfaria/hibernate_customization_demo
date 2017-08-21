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
public class ExampleEntity {

	// -------------------- Private Variables --------------------

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ENTITY_ID", unique=true, nullable=false)
	private Long id;

	@Column(name="ENTITY_GUID", nullable=false, unique=true)
	private String guid;

	@Column(name="A_BOOLEAN", nullable=false)
	private Boolean aBoolean;

	@Column(name="LAST_UPDATE_DATE", nullable=false)
	private Date lastUpdate;

	@Column(name="CREATION_DATE", nullable=false)
	private Date creationDate;


}
