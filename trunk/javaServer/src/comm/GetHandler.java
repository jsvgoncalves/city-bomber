package comm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import model.GameSession;
import model.ServerSessions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles requests to retrieve all sessions
 *
 */
public class GetHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange h) throws IOException {
		System.out.println("Get: " + h.getRequestURI().getQuery());
		List<JSONObject> sessions = new ArrayList<JSONObject>();
		
		for (GameSession session : ServerSessions.getSessions().values()) {
			JSONObject json = new JSONObject();
			json.put("name", session.getName());
			json.put("sid", session.getSessionid());
			json.put("maxplayers", session.getMaxPlayers());
			json.put("private", session.isPrivate());
			sessions.add(json);
		}
		
		JSONObject response = new JSONObject();
		response.put("sessionList", sessions);
		
		Comm.reply(h, response.toString());
	}

}
