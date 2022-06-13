package main.views.timetracker.classi;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.model.timetracker.interfacce.IProgetto;
import main.views.Colore;

public class EditorProgetto {
    
    @FXML
    private ChoiceBox<String> colorChoice;
    
    @FXML 
    private TextField nameField;
    
    /*
     * Colore del progetto
     */
    private Colore colore;
    
    @FXML
    private void initialize() {
        ObservableList<String> list = colorChoice.getItems();
        list.add("Rosso");
        list.add("Verde");
        list.add("Blu");
        list.add("Giallo");
        list.add("Viola");
        colorChoice.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                	String stringColore = (String) colorChoice.getItems().get((Integer) new_val);
                    colore = Colore.valueOf(stringColore);
        });
    }
    
    public void setProgetto(IProgetto progetto) {
    	nameField.setText(progetto.getNome());
        colorChoice.setValue(progetto.getColore().toString());
        colore = progetto.getColore();
    }
    
    public String getNome() {
    	return nameField.getText();
    }
    
    public Colore getColore() {
    	return colore;
    }
    
}
