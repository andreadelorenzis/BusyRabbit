package main;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Controllers.AppController;
import main.Controllers.Notifications.Notification;
import main.Controllers.Notifications.NotificationType;
import main.Models.accountmanager.classes.ExistingAccountException;
import main.Models.accountmanager.classes.WrongCredentialsException;
import main.Models.accountmanager.interfaces.IApp;

/**
 * 
 * Controller per le view di login, registrazione e schermata principale.
 *
 */
public class PageController {
	public static AnchorPane appContainer = null;
    
    private Stage stage;
    
    private Scene scene;
    
    @FXML
    private TextField emailLogField;
    @FXML 
    private TextField passLogField;
    @FXML
    private TextField emailRegField;
    @FXML
    private TextField nameRegField;
    @FXML
    private TextField passRegField;
    @FXML
    private TextField confPassRegField;
    
    private IApp app;
    
    public void setApp(IApp app) {
    	this.app = app;
    }
    
    @FXML
    private void accedi(ActionEvent event) throws IOException {
    	String email = emailLogField.getText();
    	String password = passLogField.getText();
    	if(/*!email.isBlank() && !password.isBlank()*/true) {
    		try {
				app.accedi("newemail@gmail.com", "pass123");
				apriSchermataPrincipale(event);
			} catch (WrongCredentialsException e) {
				new Notification("Email o password incorrette.", NotificationType.ERROR).show();
			}
    	}
    }
    
    @FXML
    private void registrati(ActionEvent event) throws IOException {
    	String nome = nameRegField.getText();
    	String email = emailRegField.getText();
    	String password = passRegField.getText();
    	String confirmation = confPassRegField.getText();
    	if(!email.isBlank() && !password.isBlank() && !nome.isBlank() && !confirmation.isBlank()) {
    		try {
				app.registraAccount(nome, email, password, confirmation);
				apriSchermataPrincipale(event);
			} catch (WrongCredentialsException | ExistingAccountException e) {
				if(e instanceof WrongCredentialsException) {
					new Notification("Le due password non coincidono.", NotificationType.ERROR).show();
				} else if(e instanceof ExistingAccountException) {
					new Notification("L'email è già utilizzata da un altro account.", NotificationType.ERROR).show();
				}
			}
    	}
    }
    
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
    
    private void apriSchermataPrincipale(ActionEvent event) throws IOException {
    	if(app.getAccessoEffettuato()) {
        	// carica la schermata principale
        	FXMLLoader loader = new FXMLLoader();
        	URL fileUrl = Main.class.getResource("/main/Views/App.fxml");
        	loader.setLocation(fileUrl);
        	Pane root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            root.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
            
            AnchorPane pane = (AnchorPane) root;
            appContainer = pane;
            
            // passa l'istanza di app con i dati al controller dell'app
            AppController controller = loader.getController();
            controller.setAppData(app);
            
            //scene.getStylesheets().add(getClass().getResource("Registrazione.css").toExternalForm());
            String css = this.getClass().getResource("Globall.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
            
            stage.setScene(scene);
            stage.setWidth(dimSchermo.getWidth());
            stage.setHeight(dimSchermo.getHeight());
            stage.setX(dimSchermo.getMinX());
            stage.setY(dimSchermo.getMinY());
            stage.setResizable(true);
            stage.show();
    	}
    }
    
}
