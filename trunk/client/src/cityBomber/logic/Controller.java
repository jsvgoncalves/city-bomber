package cityBomber.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.GameInfoRecord;
import Model.ServerRecord;
import Model.Session;
import Model.SessionRecord;

public class Controller {

	private static  boolean error = false;
	private static String errorid = "";
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
	public static void login(String ServerResult)
	{
		try {
			JSONObject jObject = new JSONObject(ServerResult);
			JSONObject userinfo = jObject.getJSONObject("loginUser"); 
			Session.setUserId(""+userinfo.getInt("id"));
			Session.setUsername(userinfo.getString("username"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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

	public static ArrayList<GameInfoRecord> getPlayersList(String ServerResult)
	{
		ArrayList<GameInfoRecord> players = new ArrayList<GameInfoRecord>();

		try {
			/*JSONObject jObject = new JSONObject(ServerResult);
			JSONArray serverArray = jObject.getJSONArray("sessionList"); 
			int arraysize = serverArray.length();
			SessionRecord s;
			for(int i = 0; i < arraysize; i++)
			{
				s = new SessionRecord(serverArray.getJSONObject(i).getString("name").toString(), serverArray.getJSONObject(i).getInt("maxplayers"), serverArray.getJSONObject(i).getInt("joined"), serverArray.getJSONObject(i).getInt("sid"), serverArray.getJSONObject(i).getBoolean("private"));
				players.add(s);				
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String server = "Player";
		int max=50, joined = 25;
		for(int i = 0; i < 10; i++)
		{
			players.add(new GameInfoRecord(server + i, max, joined));
		}

		return players;

	}

	public static Integer[] getSessionInfo(String ServerResult)
	{
		Integer[] map = new Integer[4];

		try {
			JSONObject jObject = new JSONObject(ServerResult);
			JSONObject objt = jObject.getJSONObject("session");
			map[0]= objt.getInt("lat");
			map[1]= objt.getInt("lon");
			map[2]= objt.getInt("rlat");
			map[3]= objt.getInt("rlon");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return map;

	}

	public static boolean isError(JSONObject jObject)
	{

		try {

			if(jObject.has("error"))
			{
				setErrorid(jObject.getString("error"));
				return true;

			}

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
				System.out.println(ServerResult);
				return jObject.getString("error");
			}
			else
			{
				setError(false);
				Session.setSessionId(jObject.getInt("session"));
				return "" + jObject.getInt("session");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println(ServerResult);
		return "An error ocurred";

	}
	
	public static GameInfo getgameinfo(String ServerResult)
	{
		
		try {
			JSONObject json = new JSONObject(ServerResult);
			if(isError(json))
			{
				setError(true);
				return null;
			}
			else
			{
				setError(false);
				GameInfo gmi=new GameInfo();
				JSONArray users = json.getJSONObject("update").getJSONArray("users"); 
				int size=users.length();
				JSONObject user;
				for(int i=0;i<size;i++)
				{
					user= users.getJSONObject(i);
					gmi.addUser(user.getString("id"),user.getString("name"),user.getInt("lat"),user.getInt("lon"),user.getInt("stat"),user.getInt("max"),user.getInt("kills"),user.getInt("deaths"));
				}
				JSONArray bombs = json.getJSONObject("update").getJSONArray("bombs"); 
				size=bombs.length();
				JSONObject bomb;
				for(int i=0;i<size;i++)
				{
					bomb= bombs.getJSONObject(i);
					gmi.addBomb(bomb.getString("id"),bomb.getInt("lat"),bomb.getInt("lon"),bomb.getInt("rad"),bomb.getString("uid"));
				}
				
				return gmi;
				
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		setError(true);
		
		return null;
	}

	public static boolean isError() {
		return error;
	}

	public static void setError(boolean error2) {
		error = error2;
	}

	public static String getErrorid() {
		return errorid;
	}

	public static void setErrorid(String errorid2) {
		errorid = errorid2;
	}

}
