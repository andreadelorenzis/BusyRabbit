package main;

import java.io.IOException;
import static java.lang.Thread.sleep;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public class PageController {
    
    private Stage stage;
    
    private Scene scene;
    
    @FXML
    private void apriPaginaLogin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Views/Account/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        //scene.getStylesheets().add(getClass().getResource("Registrazione.css").toExternalForm());
        String css = this.getClass().getResource("Globall.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void apriPaginaRegistrazione(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Views/Account/Registrazione.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        //scene.getStylesheets().add(getClass().getResource("Registrazione.css").toExternalForm());
        String css = this.getClass().getResource("Globall.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
    } 
    
    @FXML
    private void apriSchermataPrincipale(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Views/App.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        //scene.getStylesheets().add(getClass().getResource("Registrazione.css").toExternalForm());
        String css = this.getClass().getResource("Globall.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
        
        stage.setScene(scene);
        stage.setWidth(dimSchermo.getWidth());
        stage.setHeight(dimSchermo.getHeight());
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(true);
        stage.show();
    }
    
}
