
package model;

import java.util.HashMap;

public class GameSession implements Runnable{

	private HashMap<Integer, GameUser> users = new HashMap<Integer, GameUser>();
	private String name;
	private int maxPlayers;
	private int ownerID;
	private int sessionid;
	private boolean isPrivate;
	private String password; 
	
	public GameSession(int index, String name, int max, int userid, boolean isPrivate, String password) {
		setSessionid(index);
		setName(name);
		setMaxPlayers(max);
		setOwnerID(userid);
		setPrivate(isPrivate);
		if(isPrivate()){
			setPassword(password);
		} else {
			setPassword("");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Adds a new user to this game session.
	 * @param username	It's name
	 * @param userID	A unique user identifier
	 * @param userIP	The IP address from where the user client is connected
	 */
	public boolean join(String username, int userID, String userIP) {
		if(!users.containsKey(username)){
			users.put(userID, new GameUser(username, userIP));
			return true;
		} else {
			// User already exists
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public int getPlayerCount() {
		return users.size();
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}

	public int getSessionid() {
		return sessionid;
	}

	public boolean isFull() {
		return users.size() >= getMaxPlayers(); 
	}
	
}
