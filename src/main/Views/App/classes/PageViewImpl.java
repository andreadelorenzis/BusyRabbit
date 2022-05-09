package main.Views.App.classes;

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
import main.Main;
import main.Controllers.App.PageController;
import main.Controllers.App.PageControllerImpl;
import main.Models.accountmanager.classes.ExistingAccountException;
import main.Models.accountmanager.classes.WrongCredentialsException;
import main.Models.accountmanager.interfaces.IAccountManager;
import main.Views.LoaderRisorse;
import main.Views.App.interfaces.PageView;
import main.Views.Notifications.Notification;
import main.Views.Notifications.NotificationType;

public class PageViewImpl implements PageView {
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
    
    private PageController controller;
    private IAccountManager app;
    
    @FXML
    private void initialize() {
    	controller = new PageControllerImpl(this);
    }
    
    public void setApp(IAccountManager app) {
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
					new Notification("L'email � gi� utilizzata da un altro account.", NotificationType.ERROR).show();
				}
			}
    	}
    }
    
    @FXML
    private void apriPaginaLogin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoaderRisorse.login);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(LoaderRisorse.globalCss);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void apriPaginaRegistrazione(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoaderRisorse.registrazione);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(LoaderRisorse.globalCss);
        stage.setScene(scene);
        stage.show();
    } 
    
    private void apriSchermataPrincipale(ActionEvent event) throws IOException {
    	if(app.getAccessoEffettuato()) {
        	// carica la schermata principale
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(LoaderRisorse.app);
        	Pane root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            root.getStylesheets().add(LoaderRisorse.globalCss);
            
            AnchorPane pane = (AnchorPane) root;
            appContainer = pane;
            
            // passa l'istanza di app con i dati al controller dell'app
            AppViewImpl controller = loader.getController();
            controller.setAppData(app);

            scene.getStylesheets().add(LoaderRisorse.globalCss);
            
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
