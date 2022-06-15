package main.views.accountmanager.classi;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.controller.IController;
import main.controller.accountmanager.AccessController;
import main.model.accountmanager.classi.AccountManager;
import main.views.IView;
import main.views.LoaderRisorse;
import main.views.notification.Notification;
import main.views.notification.NotificationType;

public class PageView implements IView {
	public static AnchorPane appContainer = null;
    
    public static Stage stage;
    
    public static Scene scene;
    
    @FXML
    private TextField emailLogField;
    @FXML 
    private PasswordField passLogField;
    @FXML
    private TextField emailRegField;
    @FXML
    private TextField nameRegField;
    @FXML
    private PasswordField passRegField;
    @FXML
    private PasswordField confPassRegField;
    
    private IController controller;
    
    private AccountManager app = AccountManager.getInstance();
    
    @FXML
    private void initialize() {
        setController(new AccessController());
    }
    
    @FXML
    private void accedi(ActionEvent event) throws IOException {
    	AccessController accessController = (AccessController) controller;
    	String email = emailLogField.getText();
    	String password = passLogField.getText();
    	if(/*!email.isBlank() && !password.isBlank()*/ true) {
	    	boolean result = accessController.accedi(email, password);
	    	if(result) {
	    		apriSchermataPrincipale(event);	
	    	}
    	} else {
    		new Notification("Perfavore, compila tutti i campi.", NotificationType.ERROR).show();
    	}
    }
    
    @FXML
    private void registrati(ActionEvent event) throws IOException {
    	AccessController accessController = (AccessController) controller;
    	String nome = nameRegField.getText();
    	String email = emailRegField.getText();
    	String password = passRegField.getText();
    	String confirmation = confPassRegField.getText();
    	if(!nome.isBlank() && !email.isBlank() && !password.isBlank() && !confirmation.isBlank()) {
        	boolean result = accessController.registraAccount(nome, email, password, confirmation);
        	if(result) {
        		apriSchermataPrincipale(event);
        	} 
    	} else {
    		new Notification("Perfavore, compila tutti i campi.", NotificationType.ERROR).show();
    	}
    }
    
    @FXML
    public void apriPaginaLogin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoaderRisorse.getFXML(LoaderRisorse.AM, "Login"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(LoaderRisorse.globalCss);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void apriPaginaRegistrazione(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoaderRisorse.getFXML(LoaderRisorse.AM, "Registrazione"));
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
        	loader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.AM, "App"));
        	Pane root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            root.getStylesheets().add(LoaderRisorse.globalCss);
            root.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.AM, "App"));
            
            AnchorPane pane = (AnchorPane) root;
            appContainer = pane;
            
            // passa l'istanza di app con i dati al controller dell'app
            AppView controller = loader.getController();
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
    
	@Override
	public void setController(IController c) {
		this.controller = c;
		controller.setView(this);
	}

	@Override
	public IController getController() {
		return this.controller;
	}
	
	@Override
	public void successo(String m) {
		new Notification(m, NotificationType.SUCCESS).show();
	}
	
	@Override
	public void errore(String s) {
		new Notification(s, NotificationType.ERROR).show();
	}

	@Override
	public void info(String m) {
		new Notification(m, NotificationType.INFO).show();
	}
    
}
