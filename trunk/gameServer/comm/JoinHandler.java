package comm;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import model.ServerSessions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This classes handles new users joining game sessions.
 *
 */
public class JoinHandler implements HttpHandler{

	/**
	 * Setups the handler for HTTP requests
	 * /joinsession?name=<name>&userid=<uid>&sessionid=<sid>&pass=<password>
	 * 
	 * @param h
	 * @throws IOException
	 */
	@Override
	public void handle(HttpExchange h) throws IOException  {
		
		/* FIXME DEBUG clean this up */ System.out.println("Join session: " + h.getRequestURI().getQuery());
		HashMap<String, String> map = Comm.getQuery(h);
		
		JSONObject json = new JSONObject();
		try {
			String username = map.get("name");
			int userid = Integer.valueOf(map.get("userid"));
			int sessionid = Integer.valueOf(map.get("sessionid"));
			String userip =  h.getRemoteAddress().getAddress().getHostAddress();
			ServerSessions.joinSession(username, userip,userid, sessionid);
			
			json = ServerSessions.getMapJson(sessionid, userid);
			
			JSONObject reply = new JSONObject();
			reply.put("session", json);
			Comm.reply(h, reply.toString());
			
		} catch (NumberFormatException e) {
			/* FIXME DEBUG clean this up */ System.err.println("Invalid number format");
			json = new JSONObject();
			json.put("error", "Join: Invalid number format");
			Comm.reply(h, json.toString());
		} catch (Exception e) {
			/* FIXME DEBUG clean this up */
			System.err.println("Join: " + e.getMessage());
			//e.printStackTrace();
			json = new JSONObject();
			json.put("error", e.getMessage());
		}
		Comm.reply(h, json.toString());
		
	}

}
