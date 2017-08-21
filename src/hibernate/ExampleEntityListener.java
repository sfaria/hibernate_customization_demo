package hibernate;

import entities.ExampleEntityWithListener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.UUID;

/**
 * @author Scott Faria
 */
public class ExampleEntityListener {

	// -------------------- Listener Methods --------------------

	@PrePersist
	public void beforePersist(ExampleEntityWithListener e) {
		e.setUUID(UUID.randomUUID().toString());
	}

	@PreUpdate
	public void beforeUpdate(ExampleEntityWithListener e) {
		e.setLastUpdate(new Date());
	}

}
