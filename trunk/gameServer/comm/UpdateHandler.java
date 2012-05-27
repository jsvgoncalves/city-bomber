package comm;

import java.io.IOException;
import java.util.HashMap;

import model.ServerSessions;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles player position updated during game
 * 
 * 	/update?sid=<session>&uid=<userid>&ukey=<userkey>&lat=<latitude>&lon=<longitude>&b=<hasBomb>
 *
 */
public class UpdateHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange h) throws IOException {
		/* FIXME DEBUG */ System.out.println("Update: " + h.getRequestURI().getQuery());
		HashMap<String, String> map = Comm.getQuery(h);
		JSONObject json = new JSONObject();
		
		try {
			int sessionid = Integer.valueOf(map.get("sid"));
			int userid = Integer.valueOf(map.get("uid"));
//			String userKey = map.get("ukey");
			int latitude = Integer.valueOf(map.get("lat"));
			int longitude = Integer.valueOf(map.get("lon"));
			boolean hasBomb = Boolean.valueOf(map.get("b"));
			
			ServerSessions.update(sessionid, userid, latitude, longitude, hasBomb);
			JSONObject state = ServerSessions.getState(sessionid);
			json.put("update", state);
			
		} catch (NumberFormatException e) {
			/* FIXME DEBUG */ System.err.println("Invalid number format");
			json = new JSONObject();
			json.put("error", "Invalid number format");
			Comm.reply(h, json.toString());
		} catch (Exception e) {
			/* FIXME DEBUG */ System.err.println(e.getMessage());
			// e.printStackTrace();
			json = new JSONObject();
			json.put("error", e.getMessage());
		}
		
		Comm.reply(h, json.toString());
	}

}
