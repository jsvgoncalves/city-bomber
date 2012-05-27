package model;

public class GameUser {
	
	private String ip;
	private String name;
	private int userid;
	private int latitude;
	private int longitude;
	
	private int deaths;
	private int kills;
	
	/**
	 * 0: alive;
	 * 1: dead
	 */
	private int status;
	
	private int bombsSemaphore = 0;
	private int bombsLeft = 1;
	public int getBombsLeft() {
		return bombsLeft;
	}

	public void setBombsLeft(int bombsLeft) {
		this.bombsLeft = bombsLeft;
	}

	private int bombRadius;
	
	public GameUser(String username, String userIP) {
		this.setName(username);
		this.setIp(userIP);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setPos(int latitude, int longitude) {
		setLatitude(latitude);
		setLongitude(longitude);
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getUserid() {
		return userid;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return 0 if alive, 1 if dead.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Checks if this this user can place another bomb
	 * @return True if the number of bombs left is greater that zero. False otherwise.
	 */
	public boolean canPutBomb() {
		while(bombsSemaphore > 0); // prevents concurrent bombsLeft update
		
		bombsSemaphore++;
		if(bombsLeft > 0) {
			bombsLeft--;
			bombsSemaphore--;
			return true;
		}
		
		return false;		
	}
	
	/**
	 * After an explosion, 
	 */
	public void notifyExplosion(){
		while(bombsSemaphore > 0); // prevents concurrent bombsLeft update
		
		bombsSemaphore++;
		bombsLeft++;
		bombsSemaphore--;
	}

	public int getBombRadius() {
		return bombRadius;
	}

	public void incrementBombs() {
		bombsLeft++;
		
	}

	public void incDeaths() {
		this.deaths++;
	}

	public int getDeaths() {
		return deaths;
	}

	public void incKills() {
		this.kills++;;
	}

	public int getKills() {
		return kills;
	}
}
