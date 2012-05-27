package cityBomber.network;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


public class Communication {

	String URL ="";
	String result = "";

	public Communication(String url)
	{
		this.URL = url;
	}
	public String getServerResponse()
	{
		try {
			HttpParams httpParams = new BasicHttpParams();
			final int timeout = 10000; //ms
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			HttpClient httpclient = new DefaultHttpClient(httpParams);
			this.URL = this.URL.replaceAll(" ", "%20");
			HttpGet request = new HttpGet(URL);
			ResponseHandler<String> handler = new BasicResponseHandler();
			result = httpclient.execute(request, handler);
			httpclient.getConnectionManager().shutdown();		
		} catch (Exception e) {
			System.out.println("CONNECTION TIMED OUT");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return result;		
	}


}
