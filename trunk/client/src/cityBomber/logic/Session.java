package cityBomber.logic;

import Model.ServerRecord;

public class Session {

	private static ServerRecord server;

	public static ServerRecord getServer() {
		return server;
	}

	public static void setServer(ServerRecord server) {
		Session.server = server;
	}
	
}
