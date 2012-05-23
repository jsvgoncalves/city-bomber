package cityBomber.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.ServerRecord;
import Model.SessionRecord;

public class Controller {

	private static  boolean error = false;
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
				s = new ServerRecord(serverArray.getJSONObject(i).getString("name").toString(), serverArray.getJSONObject(i).getString("ip").toString(),Integer.toString((serverArray.getJSONObject(i).getInt("port"))));
				servers.add(s);				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/*String server = "Server", ip="127.0.0.", port = "1500";
		for(int i = 0; i < 10; i++)
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
				s = new SessionRecord(serverArray.getJSONObject(i).getString("name").toString(), serverArray.getJSONObject(i).getInt("maxplayers"), serverArray.getJSONObject(i).getInt("joined"), serverArray.getJSONObject(i).getInt("sid"), serverArray.getJSONObject(i).getBoolean("private"));
				System.out.println("SYSTEM: " + s.getSessionName());
				sessions.add(s);				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/*String server = "Session", max="50", joined = "25";
		for(int i = 0; i < 10; i++)
		{
			sessions.add(new SessionRecord(server + i, max, joined));
		}*/

		return sessions;

	}
	public static boolean isError(JSONObject jObject)
	{

		try {

			if(jObject.has("error"))
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}

		return false;

	}


	public static String getSessionCreateResponse(String ServerResult)
	{
		try {
			JSONObject jObject = new JSONObject(ServerResult);
			if(isError(jObject))
			{
				setError(true);
				return jObject.getString("error");
			}
			else
			{
				setError(false);
				return jObject.getString("session");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		setError(true);
		return "An error ocurred";

	}

	public static boolean isError() {
		return error;
	}

	public static void setError(boolean error2) {
		error = error2;
	}

}
