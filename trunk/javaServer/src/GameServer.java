import comm.Comm;


public class GameServer{
	
	public static int default_port = 8081;
	
	public static void main(String[] args) {
		Comm.setupContext(default_port);
	}

}
