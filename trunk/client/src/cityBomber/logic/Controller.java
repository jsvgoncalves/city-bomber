package cityBomber.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.ServerRecord;
import Model.SessionRecord;

public class Controller {

	public static ArrayList<ServerRecord> getServerList(String ServerResult)
	{
		ArrayList<ServerRecord> servers = new ArrayList<ServerRecord>();
		
		try {
			JSONObject jObject = new JSONObject(ServerResult);
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
		/*String server = "Server", ip="127.0.0.", port = "1500";
		for(int i = 0; i < 500; i++)
		{
			servers.add(new ServerRecord(server + i, ip +i, port+i));
		}*/
				
		return servers;
		
	}

	public static ArrayList<SessionRecord> getSessionList(String ServerResult)
	{
		ArrayList<SessionRecord> sessions = new ArrayList<SessionRecord>();
		
		try {
			JSONObject jObject = new JSONObject(ServerResult);
			JSONArray serverArray = jObject.getJSONArray("sessionList"); 
			int arraysize = serverArray.length();
			SessionRecord s;
			for(int i = 0; i < arraysize; i++)
			{
				s = new SessionRecord(serverArray.getJSONObject(i).getString("session").toString(), serverArray.getJSONObject(i).getString("max").toString(), serverArray.getJSONObject(i).getString("joined").toString());
				sessions.add(s);				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String server = "Session", max="50", joined = "25";
		for(int i = 0; i < 500; i++)
		{
			sessions.add(new SessionRecord(server + i, max, joined));
		}
				
		return sessions;
		
	}
	
}
