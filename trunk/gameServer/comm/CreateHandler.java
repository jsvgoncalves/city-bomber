package comm;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import model.ServerSessions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import exception.InvalidURLException;

/**
 * Handles creation of new game sessions
 * /createsession?name=<name>&max=<max>&userid=<userid>&private=<isprivate>
 * 	&password=<password>&lat=<latitude>&lon=<longitude>&rlat=<radiusLatitude>&rlon=<radiusLongitude>
 */
public class CreateHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange h) throws IOException {
		// FIXME DEBUG
		System.out.println("Create: " + h.getRequestURI().getQuery());

		HashMap<String, String> map = Comm.getQuery(h);
		
		JSONObject json = new JSONObject();
		try {
			String name = map.get("name");
			int maxPlayers = Integer.valueOf(map.get("max"));
			int userid = Integer.valueOf(map.get("userid"));
			String username = map.get("uname");
			String userip = h.getRemoteAddress().getAddress().getHostAddress();
			boolean isPrivate = Boolean.valueOf(map.get("private"));
			String password = map.get("password");
			int latitude= Integer.valueOf(map.get("lat"));
			int longitude= Integer.valueOf(map.get("lon"));
			int radLatitude = Integer.valueOf(map.get("rlat"));
			int radLongitude = Integer.valueOf(map.get("rlon"));
			
			int sessionid = ServerSessions.newSession(name, maxPlayers, userid, username,
					userip, isPrivate, password, latitude, longitude, radLatitude, radLongitude);
			json.put("session", sessionid);
			
		} catch (InvalidURLException e) {
			json.put("error", e.getMessage());
			System.err.println(json.toString());
		} catch (NumberFormatException e) {
			json.put("error", "Invalid number format");
			System.err.println(json.toString());
		}
		
		Comm.reply(h, json.toString());
	}
}


