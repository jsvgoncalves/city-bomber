package comm;

import java.io.IOException;
import java.util.HashMap;

import model.InvalidURLException;
import model.ServerSessions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CreateHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange h) throws IOException {
		System.out.println("Create: " + h.getRequestURI().getQuery());
		String[] query = h.getRequestURI().getQuery().split("&");
		HashMap<String, String> map = new HashMap<String,String>();
		
		for (int i = 0; i < query.length; i++) {
			String[] split = query[i].split("=");
			map.put(split[0], split[1]);
		}
		
		String response = "";
		
		try {
			ServerSessions.newSession(map.get("name"), Integer.valueOf(map.get("max")),
					Integer.valueOf(map.get("userid")), Boolean.valueOf(map.get("private")), map.get("password"));				response = "{'session':'" + map.get("name") + "'}";
		} catch (InvalidURLException e) {
			response = e.getJson();
		} catch (NumberFormatException e) {
			response =  "{'error':'Invalid number format.'}";
		}
		
		Comm.reply(h, response.toString());
	}
}


