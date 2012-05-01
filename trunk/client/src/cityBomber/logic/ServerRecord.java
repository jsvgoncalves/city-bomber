package cityBomber.logic;

public class ServerRecord {

	private String serverName;
	private String ip;
	private String port;
	
	public ServerRecord()
	{
		
	}
	
	public ServerRecord(String server, String ip, String port)
	{
		this.setServerName(server);
		this.setIp(ip);
		this.setPort(port);
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
