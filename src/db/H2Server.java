package db;

import org.h2.tools.Server;

import java.io.File;

/**
 * @author Scott Faria
 */
public final class H2Server {

	// -------------------- Private Static Methods --------------------

	private static void setupDBObjects() {

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
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(1);
		}

	}
}
