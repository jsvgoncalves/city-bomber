
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import exception.*;

public class GameSession implements Runnable{

	private HashMap<Integer, GameUser> users = new HashMap<Integer, GameUser>();
	private HashMap<Integer, GameBomb> bombs = new HashMap<Integer, GameBomb>();
	private HashMap<Integer, BombTimer> timers = new HashMap<Integer, BombTimer>();
	
	private int lastBombId = 0;
	
	private String name;
	private int maxPlayers;		// Max number of players connected to the same session
	private int ownerID;		// ID of the user who created this session
	private int sessionid;		
	private boolean isPrivate;	// If private, this session demands a password
	private String password;
	private int status;			// Session may be in Standby (0), Mid-game (1), or finished (2)
	
	private int latitute;
	private int longitude;
	private int radLatitude;
	private int radLongitude;

	public GameSession(int index, String name, int max, int userid, boolean isPrivate,
			String password, int latitute, int longitude, int radLatitude, int radLongitude) {
		setSessionid(index);
		setName(name);
		setMaxPlayers(max);
		setOwnerID(userid);
		setPrivate(isPrivate);
		setLatitute(latitute);
		setLongitude(longitude);
		setRadLatitude(radLatitude);
		setRadLongitude(radLongitude);
		
		if(isPrivate()){
			setPassword(password);
		} else {
			setPassword("");
		}
		setStandby();
	}

	@Override
	public void run() {
		System.out.println();
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
			// Then user already exists
			return false;
		}
	}
	
	public boolean hasUserID(int userID) {
		return users.containsKey(userID);
	}

	public void updateUser(int userid, int latitude,
			int longitude) throws InvalidURLException {
		// FIXME: fix concurrency issues
		if(users.containsKey(userid)){
			users.get(userid).setPos(latitude, longitude);
		} else {
			throw new InvalidURLException("Invalid user id");
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
	
	/**
	 * Sets status to 0
	 */
	public void setStandby(){
		status = 0;
	}
	
	/**
	 * Sets status to 1
	 */
	public void setRunning(){
		status = 1;
	}
	
	/**
	 * Sets status to 2
	 */
	public void setFinished(){
		status = 2;
	}

	public int getStatus() {
		return status;
	}
	
	public int getLatitute() {
		return latitute;
	}

	public void setLatitute(int latitute) {
		this.latitute = latitute;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getRadLatitude() {
		return radLatitude;
	}

	public void setRadLatitude(int radLatitude) {
		this.radLatitude = radLatitude;
	}

	public int getRadLongitude() {
		return radLongitude;
	}

	public void setRadLongitude(int radLongitude) {
		this.radLongitude = radLongitude;
	}

	public void addBomb(int latitude, int longitude, int userid) throws Exception {
		//FIXME: concurrent hash insertion (index can't be the same)
		if(!users.containsKey(userid))
			throw InvalidURLException("Invalid user id");
		
		if(!users.get(userid).canPutBomb())
			return;
//			throw new IllegalBombException();
		
		int id = nextBombId();
		int bombRadius = users.get(userid).getBombRadius();
		bombs.put(id, new GameBomb(id, latitude, longitude, bombRadius, userid));
		int fuseTime = bombs.get(id).getFuseTime();
		timers.put(id, new BombTimer(fuseTime, id, getSessionid()));
	}

	private Exception InvalidURLException(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private int nextBombId() {
		return lastBombId++;
	}

	public List<JSONObject> getUsersJson() {
		List<JSONObject> usersJson = new ArrayList<JSONObject>();
		
		for (GameUser user : users.values()) {
			JSONObject json = new JSONObject();
			json.put("id", user.getUserid());
			json.put("name", user.getName());
			json.put("lat", user.getLatitude());
			json.put("lon", user.getLongitude());
			json.put("stat", user.getStatus());
			json.put("max", user.getBombsLeft());
			json.put("deaths", user.getDeaths());
			json.put("kills", user.getKills());
			usersJson.add(json);
		}
		
		return usersJson;
	}

	public List<JSONObject> getBombsJson() {
		List<JSONObject> bombsJson = new ArrayList<JSONObject>();
		
		for (GameBomb bomb : bombs.values()) {
			JSONObject json = new JSONObject();
			json.put("id", bomb.getBombId());
			json.put("lat", bomb.getLatitude());
			json.put("lon", bomb.getLongitude());
			json.put("rad", bomb.getRadius());
			json.put("uid", bomb.getUserid());
			bombsJson.add(json);
		}
		return bombsJson;
	}

	public List<JSONObject> getItemsJson() {
		List<JSONObject> itemsJson = new ArrayList<JSONObject>();
		
//		for (GameItem item : bombs.values()) {
//			JSONObject json = new JSONObject();
//			json.put("id", bomb.getBombId());
//			json.put("lat", bomb.getLatitude());
//			json.put("lon", bomb.getLongitude());
//			json.put("radius", bomb.getRadius());
//			json.put("uid", bomb.getUserid());
//			bombsJson.add(json);
//		}
		return itemsJson;
	}

	public void explodeBomb(int bombid) {
		users.get(bombs.get(bombid).getUserid()).incrementBombs();
		int latitude = bombs.get(bombid).getLatitude();
		int longitude = bombs.get(bombid).getLongitude();
		int radius = bombs.get(bombid).getRadius();
		int owner = bombs.get(bombid).getUserid();
		
		for (GameUser user : users.values()) {
			if( colides(latitude, longitude, radius, user.getLatitude(), user.getLongitude())){
				if (owner != user.getUserid()){	// Checks if noob killed himself
					users.get(owner).incKills();
				}
				user.incDeaths();
				System.err.println("User " + user.getName() + " died a blazing, horrible, death...");
			}
		}
		
		bombs.remove(bombid);
		for (GameBomb bomb : bombs.values()) {
			// TODO: calc colisions for bombs
		}
	}

	/**
	 * Calculates if the object 
	 * @param latitude
	 * @param longitude2
	 * @param radius
	 * @param latitude2
	 * @param longitude3
	 * @return
	 */
	private boolean colides(int centerLat, int centerLon, int radius,
			int targetLat, int targetLon) {
		
		double dist = Math.sqrt((centerLat - targetLat)*(centerLat - targetLat) +
				(centerLon - targetLon)*(centerLon - targetLon));
		if (dist < radius){
			return true;
		}
		return false;
	}
	
}
