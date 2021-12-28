/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.Models.timetracker.classes.Progetto;

/**
 *
 * @author andre
 */
public class EditorProgettoController {
    
    @FXML
    private ChoiceBox<String> colore;
    
    @FXML 
    private TextField nome;
    
    private Progetto progetto;
    
    @FXML
    private void initialize() {
        ObservableList<String> list = colore.getItems();
        list.add("Rosso");
        list.add("Verde");
        list.add("Blu");
        colore.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    progetto.setColore((String) colore.getItems().get((Integer) new_val));
        });
    }
    
    @FXML
    private void handleTextField(KeyEvent event) {
        this.progetto.setNome(nome.getText());
    }
    
    // Chiamato dal TimeTrackerController
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
        colore.setValue(progetto.getColore());
        nome.setText(progetto.getNome());
    }
    
    public Progetto getProgetto() {
        return this.progetto;
    }
    
}
