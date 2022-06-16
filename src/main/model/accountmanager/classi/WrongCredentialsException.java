package main.model.accountmanager.classi;

/**
 * Eccezione lanciata quando le credenziali inserite non sono corrette.
 * Es: password errata, password non combaciano, email non esistente.
 */
public class WrongCredentialsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public WrongCredentialsException(String message) {
		super(message);
	}
	
}
