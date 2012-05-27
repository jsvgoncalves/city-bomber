package cityBomber.logic;

import java.util.Vector;



public class GameInfo {

	private Vector<String> userids=new Vector<String>(), bombsids=new Vector<String>(), bombuo=new Vector<String>(), usernames=new Vector<String>() ;
	private Vector<Integer> lat=new Vector<Integer>(), lng=new Vector<Integer>(), status=new Vector<Integer>(), maxbomb=new Vector<Integer>(), bombr=new Vector<Integer>()
			, latb=new Vector<Integer>(), lngb=new Vector<Integer>(), kills=new Vector<Integer>(), deaths=new Vector<Integer>();
	public void addUser(String id,String name, int lat, int lon, int stat, int max, int kill, int death) {
		// TODO Auto-generated method stub
		userids.add(id);
		this.lat.add(lat);
		lng.add(lon);
		status.add(stat);
		maxbomb.add(max);
		usernames.add(name);
		kills.add(kill);
		deaths.add(death);
	}
	
	public Vector<Integer> getKills() {
		return kills;
	}

	public void setKills(Vector<Integer> kills) {
		this.kills = kills;
	}

	public Vector<Integer> getDeaths() {
		return deaths;
	}

	public void setDeaths(Vector<Integer> deaths) {
		this.deaths = deaths;
	}

	public void addBomb(String id, int lat, int lng, int r,
			String uid) {
		// TODO Auto-generated method stub
		bombsids.add(id);
		latb.add(lat);
		lngb.add(lng);
		bombr.add(r);
		bombuo.add(uid);
	}
	
	
	public Vector<String> getUserids() {
		return userids;
	}
	public void setUserids(Vector<String> userids) {
		this.userids = userids;
	}
	public Vector<String> getBombsids() {
		return bombsids;
	}
	public void setBombsids(Vector<String> bombsids) {
		this.bombsids = bombsids;
	}
	public Vector<String> getBombuo() {
		return bombuo;
	}
	public void setBombuo(Vector<String> bombuo) {
		this.bombuo = bombuo;
	}
	public Vector<String> getUsernames() {
		return usernames;
	}
	public void setUsernames(Vector<String> usernames) {
		this.usernames = usernames;
	}
	public Vector<Integer> getLat() {
		return lat;
	}
	public void setLat(Vector<Integer> lat) {
		this.lat = lat;
	}
	public Vector<Integer> getLng() {
		return lng;
	}
	public void setLng(Vector<Integer> lng) {
		this.lng = lng;
	}
	public Vector<Integer> getStatus() {
		return status;
	}
	public void setStatus(Vector<Integer> status) {
		this.status = status;
	}
	public Vector<Integer> getMaxbomb() {
		return maxbomb;
	}
	public void setMaxbomb(Vector<Integer> maxbomb) {
		this.maxbomb = maxbomb;
	}
	public Vector<Integer> getBombr() {
		return bombr;
	}
	public void setBombr(Vector<Integer> bombr) {
		this.bombr = bombr;
	}
	public Vector<Integer> getLatb() {
		return latb;
	}
	public void setLatb(Vector<Integer> latb) {
		this.latb = latb;
	}
	public Vector<Integer> getLngb() {
		return lngb;
	}
	public void setLngb(Vector<Integer> lngb) {
		this.lngb = lngb;
	}
	
	
	
	
	
	


}
