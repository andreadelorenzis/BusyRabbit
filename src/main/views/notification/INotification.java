package main.views.notification;

import javafx.stage.Stage;

/**
 * Crea una notifica a scomparsa, che si chiude da sola dopo un certo tempo o che
 * può essere chiusa subito.
 */
public interface INotification {

	/**
	 * Apre la notifica
	 */
	public void show();
	
	/**
	 * Chiude la notifica
	 * 
	 * @param stage
	 */
	public void close(Stage stage);
}
