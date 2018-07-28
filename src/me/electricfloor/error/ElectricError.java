package me.electricfloor.error;


public class ElectricError extends Error {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3771972976607247619L;

	public ElectricError() {
		
	}
	
	public ElectricError(String message) {
		super(message);
	}
	
	public ElectricError(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ElectricError(Throwable cause) {
		super(cause);
	}
	
	

}
