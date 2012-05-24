package model;

public class GameUser {
	
	private String ip;
	private String name;
	private int latitude;
	private int longitude;
	
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
	
	
}
