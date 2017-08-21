package db;

import org.h2.tools.Server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Scott Faria
 */
public final class H2Server {

	// -------------------- Private Statics --------------------

	private static final String CONNECT_STRING = "jdbc:h2:tcp://localhost/./db";

	// -------------------- Private Static Methods --------------------

	private static void setupDBObjects() throws SQLException {
		try (Connection conn = createConnection(); Statement stmt = conn.createStatement()) {
			//language=H2
			stmt.execute("" +
				"create table entity (\n" +
				"  entity_id integer not null primary key,\n" +
				"  uuid varchar2(100),\n" +
				"  a_boolean varchar2(1),\n" +
				"  last_update_date date not null,\n" +
				"  creation_date date not null\n" +
				")");
		}
	}

	private static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(CONNECT_STRING, "test", "");
	}

	// -------------------- Main --------------------

	public static void main(String[] args) {
		try {
			Class.forName("org.h2.Driver");
			try {
				Server.shutdownTcpServer("tcp://localhost", "", true, true);
			} catch (Throwable ignored) {} finally {
				File dbFile = new File("db.h2.db");
				if (dbFile.exists()) {
					dbFile.delete();
				}
			}
			Server.createTcpServer("-tcpAllowOthers").start();
			setupDBObjects();
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(1);
		}

	}
}
