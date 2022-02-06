package main.Models.accountmanager.classes;

public class ExistingAccountException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingAccountException(String message) {
		super(message);
	}
}
