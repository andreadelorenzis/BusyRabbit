/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.Models.timetracker.classes.Attività;
import main.Models.timetracker.classes.Progetto;

/**
 *
 * @author andre
 */
public class EditorAttivitàController {
    
    @FXML
    private TextField nome;
    
    @FXML
    private ChoiceBox progetto;
    
    @FXML
    private DatePicker data;
    
    private Attività attività;
    
    /*
    *
    * !!!! DEMO !!!!
    *
    */
    private ArrayList<Progetto> listaProgetti;
    
    @FXML
    private void initialize() {
        /*
        *
        * !!!! DEMO !!!!
        *
        */
        this.listaProgetti = new ArrayList<Progetto>();
        listaProgetti.add(new Progetto("progetto 1", "Verde"));
        listaProgetti.add(new Progetto("progetto 2", "Verde"));
        listaProgetti.add(new Progetto("progetto 3", "Verde"));
        
        ObservableList<String> list = progetto.getItems();
        for(int i = 0; i < listaProgetti.size(); i++) {
            list.add(listaProgetti.get(i).getNome());
        }
        progetto.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    Progetto progettoScelto = null;
                    for(int i = 0; i < listaProgetti.size(); i++) {
                        if(listaProgetti.get(i).getNome() == (String) progetto.getItems().get((Integer) new_val)) 
                            progettoScelto = listaProgetti.get(i);
                    }
                    attività.setProgettoPadre(progettoScelto);
           });
    };
    
    @FXML
    private void handleTextField(KeyEvent event) {
        this.attività.setNome(nome.getText());
    }
    
    // Chiamato dal TimeTrackerController
    public void setAttività(Attività attività) {
        this.attività = attività;
        progetto.setValue(attività.getProgetto());
        nome.setText(attività.getNome());
    }
    
    public Attività getProgetto() {
        return this.attività;
    }
    
}
