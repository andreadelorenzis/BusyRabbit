/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.Dashboard;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import main.Controllers.HabitTracker.AbitudineDemo;
import main.Controllers.HabitTracker.GiornoCompletamento;
import main.Controllers.HabitTracker.ItemDemo;

/**
 *
 * @author andre
 */
public class ReportAbitudiniController implements Initializable {
    
    @FXML
    private TilePane tilePane;
    
    @FXML
    private HBox settimanaBox;
    
    @FXML
    private VBox giornoBox;
    
    @FXML
    private Label giornoLabel;
    
    
    private ArrayList<GiornoCompletamento> annoAbitudini;
    private ArrayList<GiornoCompletamento> settimanaAbitudini;
    private ArrayList<AbitudineDemo> listaAbitudini;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // ----> DEMO
        
        
        
        ArrayList<AbitudineDemo> listaAbitudiniGiorno = new ArrayList<>();
        listaAbitudiniGiorno.add(new AbitudineDemo("Fare doccia fredda", "", new ArrayList<String>(), new ArrayList<ItemDemo>()));
        listaAbitudiniGiorno.add(new AbitudineDemo("Fare allenamento", "", new ArrayList<String>(), new ArrayList<ItemDemo>()));
        listaAbitudiniGiorno.add(new AbitudineDemo("Mangiare sano", "", new ArrayList<String>(), new ArrayList<ItemDemo>()));
        
        // Anno
        
        this.annoAbitudini = new ArrayList<>();
        for(int i = 0; i < 300; i++) {
            GiornoCompletamento giorno = new GiornoCompletamento(new Date(), 3, listaAbitudiniGiorno);
            this.annoAbitudini.add(giorno);
        }
        
        // Settimana
        
        this.settimanaAbitudini = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            GiornoCompletamento giorno = new GiornoCompletamento(new Date(), 3, listaAbitudiniGiorno);
            this.settimanaAbitudini.add(giorno);
        }
        
        // <---- DEMO
        
        this.visualizzaDatiAnno();
        this.visualizzaDatiSettimana();
        this.visualizzaDatiGiorno(this.annoAbitudini.get(0));
    }
    
    private void visualizzaCerchio() {
        
        AnchorPane container = new AnchorPane();
        container.setPrefWidth(100);
        container.setPadding(new Insets(0, 20, 0, 0));
        
        AnchorPane pane = new AnchorPane();
        BorderPane borderPane = new BorderPane();
        
        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setFill(Color.web("#272e52"));
        
        Arc arc = new Arc();
        arc.setRadiusX(50.0f);
        arc.setRadiusY(50.0f);
        arc.setFill(Color.web("#00E396"));
        arc.setStartAngle(45.0f);
        arc.setLength(20.0f);
        arc.setType(ArcType.ROUND);
        arc.setCenterX(50.0f);
        arc.setCenterY(50.0f);
        
        Circle circle2 = new Circle();
        circle2.setRadius(35);
        circle2.setFill(Color.web("#0e1726"));
        circle2.setCenterX(50.0f);
        circle2.setCenterY(50.0f);
        
        Label label = new Label("10%");
        label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18");
        label.setLayoutX(30);
        label.setLayoutY(35);
        label.setAlignment(Pos.CENTER);
        
        AnchorPane.setBottomAnchor(circle, 0.0);
        AnchorPane.setTopAnchor(circle, 0.0);
        AnchorPane.setLeftAnchor(circle, 0.0);
        AnchorPane.setRightAnchor(circle, 0.0);
        
        AnchorPane.setBottomAnchor(label, 28.0);
        AnchorPane.setTopAnchor(label, 22.0);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        
        pane.getChildren().add(circle);
        pane.getChildren().add(arc);
        pane.getChildren().add(circle2);
        pane.getChildren().add(label);
        
        Label label2 = new Label("Apr 02");
        label2.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
        label2.setPadding(new Insets(10, 0, 0, 0));
        label2.setAlignment(Pos.CENTER);
        label2.setMinWidth(100);
        
        borderPane.setCenter(pane);
        borderPane.setBottom(label2);
        
        container.getChildren().add(borderPane);
        this.settimanaBox.getChildren().add(container);
    }
    
    private void visualizzaDatiSettimana() {
        this.settimanaBox.getChildren().clear();
        for(int i = 0; i < 7; i++) {
            this.visualizzaCerchio();
        }
    }
    
    private void visualizzaDatiAnno() {
        double dimCaselle = 13;
        
        this.tilePane.getChildren().clear();
        
        int conteggioGiorni = 0;
        
        // Visualizza i giorni monitorati.
        for(int i = 0; i < this.annoAbitudini.size(); i++) {
            GiornoCompletamento giorno = this.annoAbitudini.get(i);
            
            HBox container = new HBox();
            HBox hBox = new HBox();
            hBox.setMinHeight(dimCaselle);
            hBox.setMinWidth(dimCaselle);
            
            double percentuale = (giorno.getAbitudiniCompletate().size() / giorno.getNumTotaleAbitudini()) * 100.0;
            System.out.println(percentuale);
            
            String colore = "#272E52";
            
            if(percentuale == 100.0) colore = "#00E396";
            else if (percentuale >= 75.0) colore = "#08C081";
            else if (percentuale >= 50.0) colore = "#058D5E";
            else if (percentuale >= 25.0) colore = "#04563A";
            
            hBox.setStyle("-fx-background-color: " + colore + ";");
            container.getChildren().add(hBox);
            container.setPadding(new Insets(0, 2, 2, 0));
            this.tilePane.getChildren().add(container);
            conteggioGiorni++;
            
            // Evento per gestire il click di un giorno.
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    visualizzaDatiGiorno(giorno);
                }
            };
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler); 
            
        }
        
        // Visualizza i giorni non monitorati del resto dell'anno.
        for(int i = conteggioGiorni; i < 365; i++) {
            HBox container = new HBox();
            HBox hBox = new HBox();
            hBox.setMinHeight(dimCaselle);
            hBox.setMinWidth(dimCaselle);
            hBox.setStyle("-fx-background-color: #272E52");
            container.getChildren().add(hBox);
            container.setPadding(new Insets(0, 2, 2, 0));
            this.tilePane.getChildren().add(container);
        }
        
    }
    
    private void visualizzaDatiGiorno(GiornoCompletamento giorno) {
        this.giornoBox.getChildren().clear();
        
        this.giornoLabel.setText("Apr 14, 2021");
        this.giornoLabel.setVisible(true);
        
        Label label = new Label("Svolto " + giorno.getAbitudiniCompletate().size() + " abitudini su " + (int) giorno.getNumTotaleAbitudini());
        label.setStyle("-fx-text-fill: #888EA8; -fx-font-size: 16;");
        label.setPadding(new Insets(0, 0, 15, 0));
        
        this.giornoBox.getChildren().add(label);
        
        for(AbitudineDemo abitudine : giorno.getAbitudiniCompletate()) {
            HBox hBox = new HBox();
            Circle circle = new Circle();
            circle.setRadius(5);
            circle.setFill(Color.web("#BAC4CA"));
            Label label2 = new Label(abitudine.getNome());
            label2.setStyle("-fx-text-fill: #BAC4CA;");
            label2.setPadding(new Insets(0, 0, 0, 7));
            hBox.getChildren().addAll(circle, label2);
            hBox.setPadding(new Insets(0, 0, 15, 0));
            this.giornoBox.getChildren().add(hBox);
        }
    }

}
