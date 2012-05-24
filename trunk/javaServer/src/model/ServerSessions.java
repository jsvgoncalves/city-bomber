package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

/**
 * Groups all sessions and methods relevant to all sessions.
 *
 */
public class ServerSessions {
	
	static HashMap<Integer, GameSession> sessions = new HashMap<Integer, GameSession>();
	static int lastIndex = 0; // Index of the last session created.

	/**
	 * Adds a new user to a session.
	 * @param username	User's name
	 * @param userIP	User's IP address
	 * @param userID	User's ID
	 * @param sessionID	The ID of the session to witch the user will connect to.
	 * @return 
	 * @throws SessionFullException 	If the number of users reach maxPlayers in this session
	 * @throws InvalidURLException 		If any String parameters is empty (password is opional)
	 */
	public static void joinSession(String username, String userIP, int userID, int sessionID)
	throws SessionFullException, InvalidURLException {
		if(sessions.get(sessionID).isFull()){
			throw new SessionFullException();
		}
		if(userIP == null || username == null){
			throw new InvalidURLException();
		}
		sessions.get(sessionID).join(username, Integer.valueOf(userID), userIP);
	}

	public static HashMap<Integer, GameSession> getSessions() {
		return sessions;
	}

	/**
	 * Adds a new session. Calls compareNames(name) to prevent same name sessions.
	 * @param name		Session name
	 * @param max		Max number of players allowed
	 * @param userid	ID of the owner user
	 * @param isPrivate	Whether this session is password protected
	 * @param password	Password used if the session is private
	 * @throws InvalidURLException	If any String parameters is empty (password is opional)
	 */
	public static void newSession(String name, int max, int userid,
			boolean isPrivate, String password) throws InvalidURLException {
		if(name.equals(""))
			throw new InvalidURLException();

		int index = 1 + lastIndex++;
		sessions.put(index, new GameSession(index,
				compareNames(name), max, userid, isPrivate, password)).run();
		
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

}
