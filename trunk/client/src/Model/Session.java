package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Session {

	private static String language, username,  userid="-1", email, name;
	private static boolean sound;
	private static boolean animations;
	private static boolean satellite;
	private static ArrayList<LanguageRecord> languages = new ArrayList<LanguageRecord>();	
	private static ServerRecord server;
	private static SessionRecord session;
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

	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String language) {
		Session.language = language;
	}

	public static boolean isSound() {
		return sound;
	}

	public static void setSound(boolean sound) {
		Session.sound = sound;
	}

	public static boolean isAnimations() {
		return animations;
	}

	public static void setAnimations(boolean animations) {
		Session.animations = animations;
	}

	public static boolean isSatellite() {
		return satellite;
	}
	
	public static String getUsername()
	{
		return username;
	}
	
	
	public static void setSessionId(int id)
	{
		Session.session.setId(id);
	}
	
	

	public static void setSatellite(boolean satellite) {
		Session.satellite = satellite;
	}

	public static ArrayList<LanguageRecord> getLanguages() {
		return languages;
	}

	public static void setLanguages(ArrayList<LanguageRecord> languages) {
		Session.languages = languages;
	}

	public static void addLanguageRecordElement(LanguageRecord r)
	{
		languages.add(r);
	}

	public static void clearCurrentLanguage()
	{
		lang.clear();
	}
	public static String getFilePath(String val)
	{
		int size = languages.size();
		for(int i = 0; i < size; i++)
		{
			if(languages.get(i).getLanguage_name().equals(val))
				return languages.get(i).getFile_path();
		}
		return val;

	}
	public static String getIconPath(String val)
	{
		int size = languages.size();
		for(int i = 0; i < size; i++)
		{
			if(languages.get(i).getLanguage_name().equals(val))
				return languages.get(i).getImage_path();
		}
		return val;

	}

	public static SessionRecord getSession() {
		return session;
	}

	public static void setSession(SessionRecord session) {
		Session.session = session;
	}

	public static void setUsername(String user) {
		// TODO Auto-generated method stub
		username=user;
	}
	
	public static void setUserId(String id1)
	{
		userid=id1;
	}

	public static String getUserId() {
		// TODO Auto-generated method stub
		return userid;
	}

	public static void setEmail(String string) {
		// TODO Auto-generated method stub
		email=string;
	}
	
	
	public static String getEmail()
	{
		return email;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Session.name = name;
	}
	
	

}
