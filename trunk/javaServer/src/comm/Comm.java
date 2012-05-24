package comm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;


public class Comm {
	
	public static int timeout = 2000;
	
	/**
	 * Setups an HTTP request.
	 * @param url
	 * @return Returns the response from server or an empty byte[] if there was an error.
	 */
	public static byte[] setConnection(String url){
		try {
			URL reqUrl = new URL(url);
	        URLConnection uc = reqUrl.openConnection();
	        uc.setConnectTimeout(timeout);
	        InputStream is = uc.getInputStream();
	        byte[] b = new byte[255];
	        is.read(b,0, 255);
	        is.close();
	        return b;
        
		} catch (IOException e) {
			return new byte[0];
		}
	}
	
	public static void setupContext(int port){
		try {
			HttpServer s = HttpServer.create(new InetSocketAddress(port), 10);
			s.createContext("/joinsession", new JoinHandler());
			s.createContext("/getsessions", new GetHandler());
			s.createContext("/createsession", new CreateHandler());
			s.createContext("/setPosition", new PositionHandler());
			s.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reply(HttpExchange h, String response) throws IOException {
		h.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
		OutputStream o = h.getResponseBody();
		o.write(response.getBytes());
		o.close();
	}
}
