package main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.model.accountmanager.interfacce.IAccountManager;
import main.views.LoaderRisorse;
import main.views.accountmanager.classi.PageView;

public class Main extends Application { 
    
	/*
	 * Data di ultimo accesso all'applicazione
	 */
    public static LocalDate dataUltimoAccesso;
    
    /*
     * Istanza dell'AccountManager
     */
    public IAccountManager app;
    
    @Override
    public void start(Stage primaryStage) {
        try { 
            // Carica la pagina di login.
        	FXMLLoader fxmlLoader = new FXMLLoader();
        	fxmlLoader.setLocation(LoaderRisorse.getFXML("accountmanager", "Login"));
        	Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 650, 600);
            
            // passa l'istanza di app al controller delle pagine di accesso
            PageView controller = fxmlLoader.getController();
            
            // Aggiunge l'icona.
            primaryStage.getIcons().add(LoaderRisorse.getImg("logo.png"));
            scene.getStylesheets().add(LoaderRisorse.globalCss);
            
            primaryStage.setResizable(false);
            primaryStage.setTitle("BusyRabbit");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // alla chiusura dell'app, salvare i dati nel database
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					//app.salvaDati();
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