package exception;

public class IllegalBombException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5510556331727753397L;

	/**
	 * 
	 */
	@Override
	public String getMessage() {
		return "No bombs left";
	}
}
