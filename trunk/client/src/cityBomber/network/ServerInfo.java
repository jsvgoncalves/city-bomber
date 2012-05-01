package cityBomber.network;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cityBomber.logic.ServerRecord;

public class ServerInfo {
	
	String URL = "http://192.168.1.88:8080/getservers.php";
	String result = "";
	private JSONObject jObject;
	
	public ServerInfo()
	{
		
	}
	public ArrayList<ServerRecord> callWebService()
	{
		ArrayList<ServerRecord> servers = new ArrayList<ServerRecord>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL);
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		try {
			result = httpclient.execute(request, handler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		
		try {
			
			jObject = new JSONObject(result);
			JSONArray serverArray = jObject.getJSONArray("srvList"); 
			int arraysize = serverArray.length();
			ServerRecord s;
			for(int i = 0; i < arraysize; i++)
			{
				s = new ServerRecord();
				s.setServerName(serverArray.getJSONObject(i).getString("name").toString());
				s.setIp(serverArray.getJSONObject(i).getString("ip").toString());
				s.setPort(serverArray.getJSONObject(i).getString("port").toString());
				servers.add(s);				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return servers;
		
		
		
	}
	

}
