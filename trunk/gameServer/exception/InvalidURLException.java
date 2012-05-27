package exception;

public class InvalidURLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8643744378771583716L;
	
	public InvalidURLException(String message){
		super(message);
	}
}
