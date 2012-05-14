package cityBomber.network;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import Model.ServerRecord;


public class Communication {
	
	String URL ="";
	String result = "";
	private JSONObject jObject;
	
	public Communication(String url)
	{
		this.URL = url;
	}
	public String getServerResponse()
	{
		ArrayList<ServerRecord> servers = new ArrayList<ServerRecord>();
		HttpParams httpParams = new BasicHttpParams();
		final int timeout = 2000; //ms
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		
		HttpClient httpclient = new DefaultHttpClient(httpParams);
		HttpGet request = new HttpGet(URL);
		ResponseHandler<String> handler = new BasicResponseHandler();
		
		try {
			result = httpclient.execute(request, handler);
			httpclient.getConnectionManager().shutdown();
			jObject = new JSONObject(result);
			JSONArray serverArray = jObject.getJSONArray("srvList"); 
			int arraysize = serverArray.length();
			ServerRecord s;
			for(int i = 0; i < arraysize; i++)
			{
				s = new ServerRecord(serverArray.getJSONObject(i).getString("name").toString(), serverArray.getJSONObject(i).getString("ip").toString(), serverArray.getJSONObject(i).getString("port").toString());
				servers.add(s);				
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return result;		
	}
	

}
