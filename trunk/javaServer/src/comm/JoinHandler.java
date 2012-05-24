package comm;
import java.io.IOException;
import java.util.HashMap;

import model.InvalidURLException;
import model.ServerSessions;
import model.SessionFullException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This classes handles new users joining game sessions.
 *
 */
public class JoinHandler implements HttpHandler{

	/**
	 * Setups the handler for HTTP requests
	 * @param h
	 * @throws IOException
	 */
	@Override
	public void handle(HttpExchange h) throws IOException  {
		
		System.out.println(h.getRequestBody());
		String[] query = h.getRequestURI().getQuery().split("&");
		HashMap<String, String> map = new HashMap<String,String>();
		
		for (int i = 0; i < query.length; i++) {
			String[] split = query[i].split("=");
			map.put(split[0], split[1]);
		}
		
		String response = "";
		
		try {
			ServerSessions.joinSession(map.get("name"), map.get("userip"),
					Integer.valueOf(map.get("userid")), Integer.valueOf(map.get("sessionid")));
			response = "{'session':'" + map.get("name") + "'}";
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SessionFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidURLException e) {
			response = "{'error':'Invalid URL'}";
		}
			
		Comm.reply(h, response.toString());
	}

}
