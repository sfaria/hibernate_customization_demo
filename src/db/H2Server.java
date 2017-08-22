package db;

import entities.ExampleEntity;
import entities.ExampleEntityWithAnnotations;
import entities.ExampleEntityWithHooks;
import entities.ExampleEntityWithInterceptor;
import hibernate.ExampleAnnotationBasedInterceptor;
import hibernate.ExampleInterceptor;
import org.h2.tools.Server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

/**
 * @author Scott Faria
 */
public final class H2Server {

	// -------------------- Static Initializer --------------------

	static {
		try {
			Class.forName("org.h2.Driver");
			java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	// -------------------- Private Statics --------------------

	private static final String CONNECT_STRING = "jdbc:h2:tcp://localhost/./db";

	// -------------------- Private Static Methods --------------------

	private static void setupDBObjects() throws SQLException {
		try (Connection conn = createConnection(); Statement stmt = conn.createStatement()) {
			//language=H2
			stmt.execute("" +
				"create table entity (\n" +
				"  entity_id integer not null primary key,\n" +
				"  uuid varchar2(36),\n" +
				"  a_boolean varchar2(1),\n" +
				"  last_update_date date not null,\n" +
				"  creation_date date not null\n" +
				")");
		}
	}

	private static void runSimpleQueries() {
		ExampleEntity entity = JPA.execute(em -> {
			em.getTransaction().begin();
			ExampleEntity e = new ExampleEntity();

			e.setABoolean("Y");
			e.setCreationDate(new Date());
			e.setLastUpdate(new Date());
			e.setUuid(UUID.randomUUID().toString());

			em.persist(e);
			em.getTransaction().commit();
			em.refresh(e);
			return e;
		});

		System.err.println("====================== ExampleEntity ======================");
		System.err.println(entity);
		System.err.println("===========================================================");
	}

	private static void runQueriesWithHooks() {
		ExampleEntityWithHooks entity = JPA.execute(em -> {
			em.getTransaction().begin();
			ExampleEntityWithHooks e = new ExampleEntityWithHooks();

			e.setABoolean("Y");

			em.persist(e);
			em.getTransaction().commit();
			em.refresh(e);
			return e;
		});

		System.err.println("====================== ExampleEntityWithHooks ======================");
		System.err.println(entity);
		System.err.println("====================================================================");
	}

	private static void runQueriesWithInterceptor() {
		ExampleEntityWithInterceptor entity = JPA.execute(em -> {
			em.getTransaction().begin();
			ExampleEntityWithInterceptor e = new ExampleEntityWithInterceptor();

			e.setABoolean(true);

			em.persist(e);
			em.getTransaction().commit();
			em.refresh(e);
			return e;
		}, new ExampleInterceptor());

		System.err.println("====================== ExampleEntityWithInterceptor ======================");
		System.err.println(entity);
		System.err.println("==========================================================================");
	}

	private static void runQueriesWithAnnotations() {
		ExampleEntityWithAnnotations entity = JPA.execute(em -> {
			em.getTransaction().begin();
			ExampleEntityWithAnnotations e = new ExampleEntityWithAnnotations();

			e.setABoolean(true);

			em.persist(e);
			em.getTransaction().commit();
			em.refresh(e);
			return e;
		}, new ExampleAnnotationBasedInterceptor());

		System.err.println("====================== ExampleEntityWithAnnotations ======================");
		System.err.println(entity);
		System.err.println("==========================================================================");
	}

	private static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(CONNECT_STRING, "test", "");
	}

	private static void tryShutdown() {
		try {
			Server.shutdownTcpServer("tcp://localhost", "", true, true);
		} catch (Throwable ignored) {} finally {
			File dbFile = new File("db.mv.db");
			if (dbFile.exists()) {
				dbFile.delete();
			}
		}
	}

	// -------------------- Main --------------------

	public static void main(String[] args) {
		try {
			tryShutdown();
			Server.createTcpServer("-tcpAllowOthers").start();
			setupDBObjects();
			runSimpleQueries();
			runQueriesWithHooks();
			runQueriesWithInterceptor();
			runQueriesWithAnnotations();
			System.exit(0);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
		} finally {
			tryShutdown();
			System.exit(1);
		}
	}

}
