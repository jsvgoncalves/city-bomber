package comm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;


public class Comm {
	
	/**
	 * 
	 */
	private final static int timeout = 2000;
	
	/**
	 * This is the maximum number of queued incoming connections to
	 * allow on the listening socket. Queued TCP connections exceeding
	 * this limit may be rejected by the TCP implementation.
	 */
	private final static int backlog = 20;
	
	/**
	 * Setups an HTTP request. Used in "client mode" for sending GET requests.
	 * @param url
	 * @return Returns the response from server or an empty byte[] if there was an error.
	 */
	public static byte[] setConnection(String host, String serverName, int default_port){
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			String url = host + "registerserver.php?" +
					"name=" + serverName +
					"&ip=" + ip +
					"&port=" + default_port;
			System.err.println(url);
			URL reqUrl = new URL(url);
	        URLConnection uc = reqUrl.openConnection();
	        uc.setConnectTimeout(timeout);
	        InputStream is = uc.getInputStream();
	        byte[] b = new byte[255];
	        is.read(b,0, 255);
	        is.close();
	        System.err.println(new String(b).trim());
	        return b;
        
		} catch (IOException e) {
			return new byte[0];
		}
	}
	
	/**
	 * Creates contextes for connections and setups their respective handlers.
	 * @param port	Port to which the connection will be bind.
	 */
	public static void setupContext(int port){
		try {
			
			HttpServer s = HttpServer.create(new InetSocketAddress(port), backlog);
			s.createContext("/getid", new GuestHandler());
			s.createContext("/joinsession", new JoinHandler());
			s.createContext("/getsessions", new GetHandler());
			s.createContext("/createsession", new CreateHandler());
			s.createContext("/update", new UpdateHandler());
			s.start();

		} catch (Exception e) {
			System.err.println("The port " + port + " is already bind to another process.");
		}
	}

	/**
	 * 
	 * @param h
	 * @param response
	 * @throws IOException
	 */
	public static void reply(HttpExchange h, String response) throws IOException {
		h.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
		OutputStream o = h.getResponseBody();
		o.write(response.getBytes());
		o.close();
	}

	public static HashMap<String, String> getQuery(HttpExchange h) {
		String[] query = h.getRequestURI().getQuery().split("&");
		HashMap<String, String> map = new HashMap<String,String>();
		
		for (int i = 0; i < query.length; i++) {
			String[] split = query[i].split("=");
			map.put(split[0], split[1]);
		}
		return map;
	}
}
