package Model;

public class GameInfoRecord {

	private String player_name;
	private int deaths;
	private int kills;
			
	public GameInfoRecord(String player_name, int deaths, int kills) {
		this.player_name = player_name;
		this.deaths = deaths;
		this.kills = kills;
	}

	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	
}
