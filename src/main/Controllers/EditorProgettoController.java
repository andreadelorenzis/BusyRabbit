/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 *
 * @author andre
 */
public class EditorProgettoController {
    
    // Scelte del menù a tendina.
    private ObservableList<String> scelteColori = FXCollections.observableArrayList("Rosso", "Verde", "Blu");
    
    @FXML
    private ChoiceBox colore;
    
    @FXML 
    private TextField nome;
    
    @FXML
    private void initialize() {
        colore.setItems(this.scelteColori);
        colore.setValue("Rosso");
    }
    
    // Chiamato dal TimeTrackerController
    public void setProgetto(String nomeProgetto) {
        nome.setText(nomeProgetto);
    }
    
    public String getProgetto() {
        return nome.getText();
    }
    
}
