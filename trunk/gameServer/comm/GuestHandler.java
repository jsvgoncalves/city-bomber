package comm;
import java.io.IOException;
import java.util.Random;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles requests to retrieve all sessions.
 * 	/getid
 */
public class GuestHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange h) throws IOException {
		System.out.println("Guest login " + h.getRequestURI().getQuery());
		
		int userid = Math.abs(new Random(System.currentTimeMillis()).nextInt());
		
		JSONObject guest = new JSONObject();
		guest.put("uid", userid);
		Comm.reply(h, guest.toString());
	}
}
