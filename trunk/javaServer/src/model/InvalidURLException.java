package model;

public class InvalidURLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8643744378771583716L;
	
	public InvalidURLException(){
		super("Invalid URL Exception");
	}

	public String getJson() {
		return "{'error':'Invalid URL'}";
	}
	
	
}
