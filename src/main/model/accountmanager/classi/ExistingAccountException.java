package main.model.accountmanager.classi;

/**
 * Eccezione lanciata quando l'email inserita è già stata usata
 */
public class ExistingAccountException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExistingAccountException(String message) {
		super(message);
	}
}
