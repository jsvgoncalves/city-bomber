package cityBomber.network;

public class ServersInfo {

	private static String AuthServer = "http://192.168.1.89:8080/bomberman/authServer/api/getservers.php";

	public static String getAuthServer() {
		return AuthServer;
	}
	
}
