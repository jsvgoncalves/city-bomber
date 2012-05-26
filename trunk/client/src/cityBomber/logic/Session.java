package cityBomber.logic;

import java.util.ArrayList;
import java.util.HashMap;
import Model.LanguageRecord;
import Model.ServerRecord;

public class Session {

	private static String language = "English";
	private static boolean sound = true;
	private static boolean animations = true;
	private static boolean satellite = true;
	private static ArrayList<LanguageRecord> languages = new ArrayList<LanguageRecord>();	
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

}
