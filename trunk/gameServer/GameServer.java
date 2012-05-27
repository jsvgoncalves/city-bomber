import comm.Comm;
import comm.RemindAuthServer;


public class GameServer{
	
	public static final int default_port = 8081;
	public static final String auth_server_host = "http://paginas.fe.up.pt/~ei09136/bomber/auth/api/";
	public static final String serverName = "Apollo";
	static RemindAuthServer reminder;
	
	// http://paginas.fe.up.pt/~ei09136/bomber/auth/api/registerserver.php?name=Banana&ip=123&port=1234
	
	public static void main(String[] args) {
		
		reminder = new RemindAuthServer(auth_server_host, serverName, default_port);// Every hour
		// Comm.setConnection(auth_server_host, serverName, default_port);
		Comm.setupContext(default_port);
	}
}
