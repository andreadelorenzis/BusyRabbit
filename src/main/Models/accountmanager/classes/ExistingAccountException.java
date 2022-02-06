package main.Models.AccountManager.classes;

public class ExistingAccountException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingAccountException(String message) {
		super(message);
	}
}
