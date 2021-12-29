/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    
    @FXML 
    private HBox btnProgetto;
    
    @FXML
    private VBox listaProgetti;
    
    @FXML
    private BorderPane menuProgetti;
    
    private Progetto progettoAssociato;
    
    private Attività attività;
    
    /*
    *
    * !!!! DEMO !!!!
    *
    */
    private ArrayList<Progetto> progetti;
    
    @FXML
    private void initialize() {
       
    }
    
    private void visualizzaListaProgetti() {
        for(int i = 0; i < progetti.size(); i++) {
            this.visualizzaProgetto(progetti.get(i));
        }
    }
    
    private void visualizzaProgetto(Progetto progetto) {
       // Crea nuovo progetto
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(0, 20, 0, 10));
        pane.setMinHeight(60);
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setFill(Color.web(progetto.getColore()));
        
        Label label1 = new Label(progetto.getNome());
        label1.setGraphic(circle);
        label1.setStyle("-fx-text-fill: " + progetto.getColore() +";");
        pane.setLeft(label1);
        
        // Collega un event handler per la modifica del progetto
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                cambiaProgetto(progetto);
            }
        };
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        
        this.listaProgetti.getChildren().add(pane);
    }
    
    /**
     * Cambia il progett
     */
    private void cambiaProgetto(Progetto progetto) {
        
        // Crea nuovo progetto
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setFill(Color.web(progetto.getColore()));
        Label label = new Label(progetto.getNome());
        label.setGraphic(circle);
        label.setStyle("-fx-text-fill: " + progetto.getColore() +";");
        
        
        // Associa il progetto all'attività;
        this.progettoAssociato = progetto;
        this.attività.setProgettoPadre(this.progettoAssociato);
        
        // Aggiorna il pulsante nella view.
        this.btnProgetto.getChildren().clear(); 
        this.btnProgetto.getChildren().add(label);
        
        // Chiudi menù
        this.toggleMenuProgetti();
        
    }
    
    @FXML
    private void handleTextField(KeyEvent event) {
        this.attività.setNome(nome.getText());
    }
    
    @FXML
    private void toggleMenuProgetti() {
        if(menuProgetti.isVisible()) 
            menuProgetti.setVisible(false);
        else
            menuProgetti.setVisible(true);  
    }
    
    // Chiamato dal TimeTrackerController
    public void setAttività(Attività attività) {
        this.attività = attività;
        nome.setText(attività.getNome());
        this.progettoAssociato = attività.getProgetto();
        this.cambiaProgetto(attività.getProgetto());
        this.toggleMenuProgetti();
    }
    
    public Attività getProgetto() {
        return this.attività;
    }
    
    public void setListaProgetti(ArrayList<Progetto> progetti) {
        this.progetti = progetti;
        System.out.println(this.progetti.get(1).getNome());
        this.visualizzaListaProgetti();
    }
    
}
