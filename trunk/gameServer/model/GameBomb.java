package model;

public class GameBomb {
	
	private int bombId;
	private int latitude;
	private int longitude;
	private int fuseTime = 5000;	// Time before the bomb explodes
	private int radius = 1;			// Explosion size
	private int userid;
	private boolean alive = true;

	
	public GameBomb(int bombId, int latitude, int longitude, int radius, int userid) {
		super();
		this.bombId = bombId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.userid = userid;
	}

	public int getBombId() {
		return bombId;
	}

	public int getLatitude() {
		return latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public int getFuseTime() {
		return fuseTime;
	}

	public int getRadius() {
		return radius;
	}

	public int getUserid() {
		return userid;
	}

	/**
	 * 
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}
	
}
