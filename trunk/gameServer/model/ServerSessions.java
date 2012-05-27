package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

import exception.InvalidURLException;
import exception.SessionFullException;

/**
 * Groups all sessions and methods relevant to all sessions.
 *
 */
public class ServerSessions {
	
	// FIXME: prevent concurrent updates in hashmap
	static HashMap<Integer, GameSession> sessions = new HashMap<Integer, GameSession>();
	static int lastIndex = 0; // Index of the last session created.

	/**
	 * Adds a new user to a session.
	 * @param username	User's name
	 * @param userIP	User's IP address
	 * @param userID	User's ID
	 * @param sessionID	The ID of the session to witch the user will connect to.
	 * @return Session name
	 * @throws SessionFullException 	If the number of users reach maxPlayers in this session
	 * @throws InvalidURLException 		If any String parameters is empty (password is opional)
	 */
	public static String joinSession(String username, String userIP, int userID, int sessionID)
	throws SessionFullException, InvalidURLException {
		if(sessions.containsKey(sessionID)){
			if(sessions.get(sessionID).isFull()){
				throw new SessionFullException();
			}
			if(userIP == null || username == null){ // || sessions.get(sessionID).hasUserID(userID)){
				throw new InvalidURLException("Invalid URL");
			}
			sessions.get(sessionID).join(username, Integer.valueOf(userID), userIP);
			return sessions.get(sessionID).getName();
		} else {
			throw new InvalidURLException("Invalid session id");
		}
	}

	public static GameSession getSession(int sessionid) {
		return sessions.get(sessionid);
	}

	/**
	 * Adds a new session. Calls compareNames(name) to prevent same name sessions.
	 * @param name		Session name
	 * @param max		Max number of players allowed
	 * @param userid	ID of the owner user
	 * @param isPrivate	Whether this session is password protected
	 * @param password	Password used if the session is private
	 * @param radLongitude 	Longitude of the border point
	 * @param radLatitude  	Latitude of the border point
	 * @param longitude		Longitude of the center point
	 * @param latitute  	Latitude of the border point
	 * @return Returns the newly created session's ID
	 * @throws InvalidURLException	If any String parameters is empty (password is opional)
	 */
	public static int newSession(String name, int max, int userid, String username, String userip, boolean isPrivate,
			String password, int latitute, int longitude, int radLatitude, int radLongitude)
	throws InvalidURLException {
		if(name.equals(""))
			throw new InvalidURLException("Invalid URL");

		int index = 1 + lastIndex++;
		sessions.put(index, new GameSession(index, compareNames(name), max, userid,
				isPrivate, password, latitute, longitude, radLatitude, radLongitude));
		sessions.get(index).run();
		sessions.get(index).join(username, userid, userip);
		
		return index;
	}
	
	/**
	 * Virtually prevents two sessions from having the same name
	 * by adding a random integer to the original name if the name 
	 * is already being used.
	 * @param name
	 * @return
	 */
	public static String compareNames(String name){
		Collection<GameSession> gamesessions = sessions.values();
		for (GameSession s : gamesessions) {
			if(s.getName().equals(name)){
				return name + "_" + (new Random()).nextInt(100);
			}
		}
		return name;
	}

	/**
	 * Updates player information (position, new bombs)
	 * @param sessionid
	 * @param userid
	 * @param userKey
	 * @param latitude
	 * @param longitude
	 * @param hasBomb 
	 * @throws InvalidURLException
	 */
	public static void update(int sessionid, int userid,
			int latitude, int longitude, boolean hasBomb) throws Exception {
		
		if(!sessions.containsKey(sessionid))
			throw new InvalidURLException("Invalid session id");
			
		//FIXME: fix concurrency issues in hashmap
		sessions.get(sessionid).updateUser(userid, latitude, longitude);
		if (hasBomb) {
			sessions.get(sessionid).addBomb(latitude, longitude, userid);
		}
	}
	
	/**
	 * Explodes the bomb with bombid in session with sessionid.
	 * This method:
	 *  - removes the bomb from the bomb list;
	 *  - finds colisions with users
	 *  - takes a chance at spawning a random item
	 * @param sessionid
	 * @param bombid
	 */
	public static void explodeBomb(int sessionid, int bombid) {
		System.err.println("THE FUCKING BOMB HAS EXPLODED, BITCH."); //TODO: no need for procrastination
		sessions.get(sessionid).explodeBomb(bombid);
	}

	public static Collection<GameSession> getSessions() {
		return sessions.values();
	}

	public static JSONObject getState(int sessionid) {
		JSONObject json = new JSONObject();
		
		json.put("users", getUsersJson(sessionid));
		json.put("bombs", getBombsJson(sessionid));
		json.put("items", getItemsJson(sessionid));
		
		return json;
	}

	public static List<JSONObject> getUsersJson(int sessionid) {
		return sessions.get(sessionid).getUsersJson();
	}

	public static List<JSONObject> getBombsJson(int sessionid) {
		return sessions.get(sessionid).getBombsJson();
	}

	public static List<JSONObject> getItemsJson(int sessionid) {
		return sessions.get(sessionid).getItemsJson();
	}

	public static JSONObject getMapJson(int sessionid, int uid) {
		JSONObject map = new JSONObject();
		map.put("lat", sessions.get(sessionid).getLatitute());
		map.put("lon", sessions.get(sessionid).getLongitude());
		map.put("rlat", sessions.get(sessionid).getRadLatitude());
		map.put("rlon", sessions.get(sessionid).getRadLongitude());
		return map;
	}

}
