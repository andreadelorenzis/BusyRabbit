

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */



import java.io.IOException;
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
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Crea la finestra principale.
            Parent root = FXMLLoader.load(getClass().getResource("main/Views/Login.fxml"));
            Scene scene = new Scene(root, 650, 600);
            
            // Aggiunge l'icona.
            Image icon = new Image("logo.png");
            primaryStage.getIcons().add(icon);
            
            //scene.getStylesheets().add(getClass().getResource("Registrazione.css").toExternalForm());
            String css = this.getClass().getResource("Globall.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            primaryStage.setResizable(true);
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
        launch(args);
    }
    
}