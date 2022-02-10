package main;



import java.awt.event.ActionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

// Demo

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.Models.accountmanager.classes.App;
import main.Models.accountmanager.interfaces.IApp;
import main.Views.Notifications.Notification;
import main.Views.Notifications.NotificationType;

/**
 *
 * @author Mars_DB
 */
public class Main extends Application { 
    
    public static LocalDate dataUltimoAccesso;
    public IApp app;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Carica la pagina di login.
        	FXMLLoader fxmlLoader = new FXMLLoader();
        	URL fileUrl = Main.class.getResource("/main/Views/Account/Login.fxml");
        	fxmlLoader.setLocation(fileUrl);
        	Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 650, 600);
            
            // passa l'istanza di app al controller delle pagine di accesso
            PageController controller = fxmlLoader.getController();
            app = App.getInstance();
            controller.setApp(app);
            
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
            
            // alla chiusura dell'app, salvare i dati nel database
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					app.salvaDati();
				}
            });
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