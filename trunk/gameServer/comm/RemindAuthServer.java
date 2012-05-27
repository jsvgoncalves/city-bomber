package comm;
 
import java.util.Timer;
import java.util.TimerTask;
 
public class RemindAuthServer {
 
	private String auth_server_host;
	private String serverName;
	private int default_port;
	Timer timer;

      
     public RemindAuthServer(String auth_server_host, String serverName, int default_port) {
		super();
		this.auth_server_host = auth_server_host;
		this.serverName = serverName;
		this.default_port = default_port;
		timer = new Timer();
		timer.schedule(new RemindTask(), 0, 3600000);
	}


	public void stopTimer() 
     { 
          if(timer!=null) 
               timer.cancel(); 
     } 
      
 
     class RemindTask extends TimerTask { 
          public void run() { 
        	  System.out.println("I'm alive");
        	  Comm.setConnection(auth_server_host, serverName, default_port);
          } 
     }
 
}