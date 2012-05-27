package exception;

public class SessionFullException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4007196723265374155L;

	public SessionFullException(){
		super("Session is full exception");
	}

	public String getJson() {
		return "{'error':'Session is full'}";
	}

	/**
	 * 
	 */
	@Override
	public String getMessage() {
		return "Session is full";
	}
}
