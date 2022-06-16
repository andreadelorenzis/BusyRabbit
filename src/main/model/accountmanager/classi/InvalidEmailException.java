package main.model.accountmanager.classi;

/**
 * Eccezione lanciata quando l'email inserita non ha un formato valido
 */
public class InvalidEmailException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidEmailException(String message) {
		super(message);
	}

}
