package main.views.goalmanager.classi;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditorItem {
    @FXML
    private TextField itemField;
    
    public String getNome() {
    	return itemField.getText();
    }
}
