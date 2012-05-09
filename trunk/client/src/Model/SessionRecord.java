package Model;

public class SessionRecord {

	//“session” : “sala1”,  “max” : “16”, “joined” : “6”
	private String sessionName, maxPlayers, joinedPlayers;



	public SessionRecord(String sessionName, String max, String joined)
	{
		this.setSessionName(sessionName);
		this.setMaxPlayers(max);
		this.setJoinedPlayers(joined);
	}



	public String getSessionName() {
		return sessionName;
	}



	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}



	public String getMaxPlayers() {
		return maxPlayers;
	}



	public void setMaxPlayers(String maxPlayers) {
		this.maxPlayers = maxPlayers;
	}



	public String getJoinedPlayers() {
		return joinedPlayers;
	}



	public void setJoinedPlayers(String joinedPlayers) {
		this.joinedPlayers = joinedPlayers;
	}

	
}
