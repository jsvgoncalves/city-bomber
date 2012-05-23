package Model;

public class SessionRecord {

	//�session� : �sala1�,  �max� : �16�, �joined� : �6�
	private String sessionName;
	private int id, maxPlayers, joinedPlayers;
	private boolean privat;



	public SessionRecord(String sessionName, int max, int joined, int id, boolean privat)
	{
		this.setSessionName(sessionName);
		this.setMaxPlayers(max);
		this.setJoinedPlayers(joined);
		this.setId(id);
		this.privat = privat;
	}



	public String getSessionName() {
		return sessionName;
	}



	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}



	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getMaxPlayers() {
		return maxPlayers;
	}



	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}



	public int getJoinedPlayers() {
		return joinedPlayers;
	}



	public void setJoinedPlayers(int joinedPlayers) {
		this.joinedPlayers = joinedPlayers;
	}



	public boolean isPrivat() {
		return privat;
	}



	public void setPrivat(boolean privat) {
		this.privat = privat;
	}

	
}
