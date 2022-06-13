package main.views.modal;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public interface IModal  {

	public ButtonType show() throws IOException;
	
	public void close(Stage stage);
	
	public void setTitolo(String titolo);
	
	public void setDimensioni(int larghezza, int altezza);
	
	/**
	 * Restituisce uno dei pulsanti del Modal
	 */
	public HBox getBtnLookup(ButtonType tipo);
}
