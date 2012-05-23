package cityBomber.logic;

import java.util.HashMap;

import Model.ServerRecord;

public class Session {


	
	private static ServerRecord server;
	private static HashMap<String, String> lang = new HashMap<String, String>();;
			
	public static ServerRecord getServer() {
		return server;
	}

	public static void setServer(ServerRecord server) {
		Session.server = server;
	}

	public static HashMap<String, String> getLang() {
		return lang;
	}

	public static void addLanguageElement(String key, String value)
	{
		lang.put(key, value);
	}
	
	public static void setLang(HashMap<String, String> lang) {
		
		Session.lang = lang;
	}
	
}
