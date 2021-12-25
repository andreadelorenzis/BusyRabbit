/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TimeTrackerController {
    
    // Scelte del menù a tendina.
    private ObservableList<String> scelteTimeTracker = FXCollections.observableArrayList("Cronometro", "Pomodoro", "Manuale");
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private VBox listaGiorniAttività;
    
    @FXML 
    private ChoiceBox timeChoice;
    
    @FXML 
    private void initialize() {
        timeChoice.setItems(this.scelteTimeTracker);
        timeChoice.setValue("Cronometro");
    }
    
    /**
     * Aggiunge alla View un giorno di attività.
     */
    @FXML
    private void aggiungiGiornoAttività() {
        
        // Crea un BorderPane container.
        BorderPane giornoAttivitàContainer = new BorderPane();
        giornoAttivitàContainer.setMinHeight(120);
        giornoAttivitàContainer.setStyle("-fx-border-radius: 14; -fx-background-radius: 12;");
        giornoAttivitàContainer.setPadding(new Insets(0, 0, 20, 0));
        
        // Crea un VBox da mettere nel BorderPane.
        VBox giornoAttività = new VBox();
        giornoAttività.setStyle("-fx-background-color: #0E1726; -fx-border-radius: 14; -fx-background-radius: 12;");
        giornoAttività.setMinHeight(80);
        
        // Crea un BorderPane che fungerà da header.
        BorderPane header = creaHeaderGiornoAttività();
        header.setMinHeight(40);
        
        // Crea un VBox come lista per le attività svolte durante il giorno.
        VBox listaAttività = new VBox();
        header.setMinHeight(30);
        
        // Crea un'attività e la aggiunge alla lista.
        BorderPane attività = creaAttività();
        listaAttività.getChildren().add(attività);
        attività.setMinHeight(40);
        
        // Aggiunge header e lista attività al VBox.
        giornoAttività.getChildren().add(header);
        giornoAttività.getChildren().add(listaAttività);
        
        // Mette il giorno attività nel container.
        giornoAttivitàContainer.setCenter(giornoAttività);
        
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #122C49;");
        scroll.getStyleClass().add("edge-to-edge");
      
        // Inserisce l'oggetto creato nella view.
        this.listaGiorniAttività.getChildren().add(giornoAttivitàContainer);
        
    }
    
    /**
     * Crea e restituisce il pulsante di aggiunta Progetto.
     * 
     * @return HBox che rappresenta il pulsante di aggiunta progetto.
     */
    private HBox creaBtnProgetto() {
        HBox btnProgetto = new HBox();
        btnProgetto.setAlignment(Pos.CENTER);
        ImageView plus = new ImageView();
        plus.setFitHeight(15);
        plus.setFitWidth(15);
        plus.setImage(new Image(getClass().getResource("/main/risorse/plus.png").toString()));
        Label label6 = new Label("Progetto");
        label6.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14;");
        label6.setPadding(new Insets(0, 0, 0, 10));
        btnProgetto.getChildren().add(plus);
        btnProgetto.getChildren().add(label6);
        return btnProgetto;
    }
    
    /**
     * Crea e restituisce l'header del giorno di attività.
     * 
     * @return BorderPane che rappresenta l'header del giorno di attività.
     */
    private BorderPane creaHeaderGiornoAttività() {
        
        // Crea un BorderPane.
        BorderPane header = new BorderPane();
        header.setStyle("-fx-background-color: #191E3A; -fx-pref-height: 50; -fx-alignment: center; -fx-border-radius: 12 12 0 0; -fx-background-radius: 12 12 0 0;");
        header.setPadding(new Insets(0, 20, 0, 20));
        
        // Crea parte destra del BorderPane.
        HBox headerDestra = new HBox();
        headerDestra.setAlignment(Pos.CENTER);
        Label label1 = new Label("Total:");
        label1.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: 800;");
        headerDestra.getChildren().add(label1);     
        Label label2 = new Label("04:53:23");
        label2.setStyle("-fx-text-fill: #888ea8; -fx-font-size: 14; -fx-font-weight: 800;");
        label2.setPadding(new Insets(0, 20, 0, 10));
        headerDestra.getChildren().add(label2);
        ImageView dots = new ImageView();
        dots.setFitHeight(18);
        dots.setFitWidth(6);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        headerDestra.getChildren().add(dots);
        
        // Crea parte sinistra del BorderPane.
        HBox headerSinistra = new HBox();
        headerSinistra.setAlignment(Pos.CENTER);
        Label label3 = new Label("Nov 17, 2020");
        label3.setStyle("-fx-text-fill: #BAC4CA; -fx-alignment: center; -fx-font-size: 14;");
        headerSinistra.getChildren().add(label3);
        
        // Imposta parte sinistra e destra del BorderPane.
        header.setLeft(headerSinistra);
        header.setRight(headerDestra);
        
        return header;
    }
    
    /**
     * Crea e restituisce una nuova Attività.
     * 
     * @return BorderPane che rappresenta l'attività.
     */
    private BorderPane creaAttività() {
        
        // Crea un BorderPane.
        BorderPane attività = new BorderPane();
        attività.setStyle("-fx-border-radius: 14; -fx-background-radius: 12;");
        attività.setPadding(new Insets(0, 20, 0, 20));
        attività.setMinHeight(60);
        
        // Crea la parte sinistra del BorderPane.
        HBox attivitàSinistra = new HBox();
        attivitàSinistra.setAlignment(Pos.CENTER);
        attivitàSinistra.setFillHeight(true);
        Label label1 = new Label("3");
        label1.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #E7515A; ");
        label1.setPadding(new Insets(0, 10, 0, 10));
        Label label2 = new Label("Studiare");
        label2.setStyle("-fx-font-size: 14; -fx-text-fill: #888EA8;");
        label2.setPadding(new Insets(0, 20, 0, 20));
        HBox btnProgetto = creaBtnProgetto();
        attivitàSinistra.getChildren().add(label1);
        attivitàSinistra.getChildren().add(label2);
        attivitàSinistra.getChildren().add(btnProgetto);
        
        // Crea la parte destra del BorderPane.
        HBox attivitàDestra = new HBox();
        attivitàDestra.setAlignment(Pos.CENTER);
        attivitàDestra.setFillHeight(true);
        Label label3 = new Label("12:52  -  8:56");
        label3.setStyle("-fx-font-size: 14; -fx-text-fill: #888EA8; -fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 0 3 0 3;");
        label3.setPadding(new Insets(20, 25, 20, 25));
        Label label4 = new Label("05:53:23");
        label4.setStyle("-fx-text-fill: #888ea8; -fx-font-size: 14; -fx-font-weight: 800; -fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 0 3 0 0;");
        label4.setPadding(new Insets(20, 25, 20, 25));
        ImageView dots = new ImageView();
        dots.setStyle("-fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 0 3 0 0;");
        dots.setFitHeight(18);
        dots.setFitWidth(6);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0, 25, 0, 0));
        container.getChildren().add(label3);
        container.getChildren().add(label4);
        attivitàDestra.getChildren().add(container);
        attivitàDestra.getChildren().add(dots);
        
        
        
        // Imposta parte sinistra e destra del BorderPane.
        attività.setLeft(attivitàSinistra);
        attività.setRight(attivitàDestra);
        
        return attività;
    }
    
}
