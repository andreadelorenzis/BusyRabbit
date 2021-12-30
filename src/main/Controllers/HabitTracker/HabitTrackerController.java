/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.HabitTracker;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author andre
 */
public class HabitTrackerController {
    
    @FXML
    private VBox abitudiniBox;
    
    @FXML
    private VBox infoBox;
    
    @FXML
    private void initialize() {
        for(int i = 0; i < 5; i++)
            this.visualizzaAbitudine();
        
        this.visualizzaInfoAbitudine();
    }
    
    @FXML
    private void visualizzaAbitudine() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 0, 10, 0));
        
        CheckBox check = new CheckBox();
        check.setText("Fare una doccia fredda");
        check.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14;");
        
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        
        Label label = new Label("INFO");
        label.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14;");
        
        HBox imgContainer = new HBox();
        ImageView dots = new ImageView();
        dots.setFitHeight(22);
        dots.setFitWidth(8);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        imgContainer.getChildren().add(dots);
        imgContainer.setPadding(new Insets(0, 20, 0, 30));
        
        hBox.getChildren().add(label);
        hBox.getChildren().add(imgContainer);
        
        pane.setLeft(check);
        pane.setRight(hBox);
        
        this.abitudiniBox.getChildren().add(pane);
    }
    
    @FXML
    private void visualizzaInfoAbitudine() {
        Label label = new Label("Arrivare a correre 20 km di fila");
        Label label2 = new Label("Descrizione:");
        Label label3 = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor "
                + "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation "
                + "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate");
        label3.setMaxWidth(500);
        label3.setWrapText(true);
        Label label4 = new Label("Serie attuale:");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        Label label5 = new Label("Come stai andando fino ad ora.");
        Label label6 = new Label("7");
        label6.setStyle("-fx-background-color: red; -fx-text-fill: #ffffff; -fx-border-radius: 50%; -fx-background-radius: 50%;");
        label6.alignmentProperty().set(Pos.CENTER);
        vBox.getChildren().add(label5);
        vBox.getChildren().add(label6);
        hBox.getChildren().add(vBox);
        
        Label label9 = new Label("Serie Record:");
        HBox hBox2 = new HBox();
        hBox.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox();
        Label label7 = new Label("Il tuo record personale.");
        Label label8 = new Label("7");
        vBox2.getChildren().add(label7);
        vBox2.getChildren().add(label8);
        hBox2.getChildren().add(vBox2);
        
        this.infoBox.getChildren().add(label);
        this.infoBox.getChildren().add(label2);
        this.infoBox.getChildren().add(label3);
        this.infoBox.getChildren().add(label4);
        this.infoBox.getChildren().add(hBox);
        this.infoBox.getChildren().add(label9);
        this.infoBox.getChildren().add(hBox2);
    }
    
}
