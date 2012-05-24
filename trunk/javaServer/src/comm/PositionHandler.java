package comm;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles player position updated during game
 *
 */
public class PositionHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange h) throws IOException {
		String response = "";
		
		
		Comm.reply(h, response.toString());
	}

}
