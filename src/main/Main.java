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
import main.model.accountmanager.classi.AccountManager;
import main.model.accountmanager.interfacce.IAccountManager;
import main.views.LoaderRisorse;

/**
 * Classe principale da cui parte l'esecuzione dell'applicazione
 */
public class Main extends Application { 
    
	/*
	 * Data di ultimo accesso all'applicazione
	 */
    public static LocalDate dataUltimoAccesso;
    
    /*
     * Se l'account è stato eliminato, nel qual caso non salvare i dati 
     */
    public static boolean accountEliminato = false;
    
    /*
     * Istanza dell'AccountManager
     */
    public IAccountManager app = AccountManager.getInstance();
    
    @Override
    public void start(Stage primaryStage) {
        try { 
            // carica la pagina di login.
        	FXMLLoader fxmlLoader = new FXMLLoader();
        	fxmlLoader.setLocation(LoaderRisorse.getFXML("accountmanager", "Login"));
        	Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 650, 600);
            
            // aggiunge l'icona della finestra
            primaryStage.getIcons().add(LoaderRisorse.getImg("logo.png"));
            scene.getStylesheets().add(LoaderRisorse.globalCss);
            
            // dimensiona la finestra e imposa il titolo
            primaryStage.setResizable(false);
            primaryStage.setTitle("BusyRabbit");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // salva i dati nel database all'atto della chiusura
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					if(!accountEliminato) 
						app.salvaDati();
				}
            });
        } catch (IOException ex) {
            Logger.getLogger(FXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}