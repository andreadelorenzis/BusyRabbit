/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.TimeTracker;

import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> valoreColori = new HashMap<String, String>();
        valoreColori.put("Rosso", "#FF4560");
        valoreColori.put("Giallo", "#FEB019");
        valoreColori.put("Verde", "#00E396");
        valoreColori.put("Blu", "#008FFB");
        valoreColori.put("Viola", "#9C27B0");
        
        ObservableList<String> list = colore.getItems();
        list.add("Rosso");
        list.add("Verde");
        list.add("Blu");
        list.add("Giallo");
        list.add("Viola");
        colore.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    progetto.setColore(valoreColori.get((String) colore.getItems().get((Integer) new_val)));
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
