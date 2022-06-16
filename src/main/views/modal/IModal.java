package main.views.modal;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Apre un overlay al di sopra della schermata.
 */
public interface IModal  {

	/**
	 * Apre il modal e attende che l'utente faccia un'operazione
	 * @return
	 * @throws IOException
	 */
	public ButtonType showAndWait() throws IOException;
	
	/**
	 * Chiude il Modal
	 * @param stage
	 */
	public void close(Stage stage);
	
	/**
	 * 
	 * @param titolo
	 */
	public void setTitolo(String titolo);
	
	/**
	 * 
	 * @param larghezza
	 * @param altezza
	 */
	public void setDimensioni(int larghezza, int altezza);
	
	/**
	 * Restituisce uno dei pulsanti del Modal
	 */
	public HBox getBtnLookup(ButtonType tipo);
	
	/**
	 * 
	 * @param tipo
	 * @return
	 */
	public Button getButton(ButtonType tipo);
}
