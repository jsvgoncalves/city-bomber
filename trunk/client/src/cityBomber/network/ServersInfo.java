package cityBomber.network;

import Model.Session;

public class ServersInfo {

	
	private static String AuthServer = "http://paginas.fe.up.pt/~ei09136/bomber/auth/api/getservers.php";
	private static String Register = "http://paginas.fe.up.pt/~ei09136/bomber/auth/api/registeruser.php";
	private static String Login = "http://paginas.fe.up.pt/~ei09136/bomber/auth/api/userlogin.php";
	
	public static String getAuthServer() {
		return AuthServer;
	}
	public static String getSessions() {
		return "http://" + Session.getServer().getIp() +":"+ Session.getServer().getPort() + "/getsessions";
	}
	public static String getRegister() {
		return Register;
	}
	public static String getLogin() {
		return Login;
	}


}
