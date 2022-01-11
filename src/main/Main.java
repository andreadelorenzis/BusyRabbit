package main;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

// Demo

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Mars_DB
 */
public class Main extends Application {
    
    public static LocalDate dataUltimoAccesso;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Crea la finestra principale.
            Parent root = FXMLLoader.load(getClass().getResource("/main/Views/Account/Login.fxml"));
            Scene scene = new Scene(root, 650, 600);
            
            // Aggiunge l'icona.
            Image icon = new Image("/main/logo.png");
            primaryStage.getIcons().add(icon);
            
            //scene.getStylesheets().add(getClass().getResource("Registrazione.css").toExternalForm());
            String css = this.getClass().getResource("/main/Globall.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            primaryStage.setResizable(false);
            primaryStage.setTitle("BusyRabbit");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main.dataUltimoAccesso = LocalDate.now();
        launch(args);
    }
    
}