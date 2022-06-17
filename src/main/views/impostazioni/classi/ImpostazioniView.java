package main.views.impostazioni.classi;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.controller.IController;
import main.controller.impostazioni.IImpostazioniController;
import main.controller.impostazioni.ImpostazioniController;
import main.controller.timetracker.ITimeTrackerController;
import main.controller.timetracker.TimeTrackerController;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.model.habittracker.classi.AbitudineScomponibile;
import main.model.habittracker.classi.AbitudineSessione;
import main.model.habittracker.interfacce.IAbitudine;
import main.views.LoaderRisorse;
import main.views.accountmanager.classi.AppView;
import main.views.accountmanager.classi.PageView;
import main.views.habittracker.classi.EditorAbitudine;
import main.views.impostazioni.interfacce.IImpostazioniView;
import main.views.modal.Modal;
import main.views.notification.Notification;
import main.views.notification.NotificationType;

public class ImpostazioniView implements IImpostazioniView {
	
	//-------------------------------- CAMPI ----------------------------------- 
	@FXML
	private PasswordField oldPassField;
	@FXML
	private PasswordField newPassField;
	@FXML
	private TextField newEmailField;
	@FXML
	private PasswordField passField1;
	@FXML
	private PasswordField passField2;
	@FXML
	private Button changeBtn1;
	@FXML
	private Button changeBtn2;
	@FXML
	private Button deleteBtn;
	
	private IImpostazioniController controller;
	
	@FXML
	private void initialize() {
        IImpostazioniController controller = new ImpostazioniController();
        setController(controller);
	}

	//--------------------------- METODI PUBBLICI ------------------------------
	@Override
	public void setController(IController c) {
		this.controller = (IImpostazioniController) c;
		this.controller.setView(this);
	}

	@Override
	public IController getController() {
		return this.controller;
	}

	@Override
	public void accountEliminato() {
		new Notification("Account eliminato", NotificationType.INFO).show();
		
		// chiude schermata app e apre pagina login
        Parent root = null;
		try {
			root = FXMLLoader.load(LoaderRisorse.getFXML(LoaderRisorse.AM, "Login"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        PageView.scene = new Scene(root);
        PageView.scene.getStylesheets().add(LoaderRisorse.globalCss);
        PageView.stage.setScene(PageView.scene);
        Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
        PageView.stage.setWidth(650);
        PageView.stage.setHeight(600);
        PageView.stage.setX(dimSchermo.getMaxX()/2 - 325);
        PageView.stage.setY(dimSchermo.getMaxY()/2 - 300);
        PageView.stage.show();
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
	
	//---------------------------- METODI PRIVATI ------------------------------
	private void pulisciCampi() {
		this.oldPassField.setText("");
		this.newEmailField.setText("");
		this.passField1.setText("");
		this.passField2.setText("");
		this.newPassField.setText("");
	}
	
	@FXML
	private void cambiaEmail() {
		String email = newEmailField.getText();
		String password = passField1.getText();
		
		if(email.isBlank() || password.isBlank()) {
			if(newEmailField.getText().isBlank()) {
				new Notification("Perfavore inserisci una email.", NotificationType.ERROR).show();
			} else if(passField1.getText().isBlank()) {
				new Notification("Perfavore inserisci la password", NotificationType.ERROR).show();
			}
			return;
		}
		controller.cambiaEmail(email, password);
		this.pulisciCampi();
	}
	
	@FXML
	private void cambiaPassword() {
		String vecchia = oldPassField.getText();
		String nuova = newPassField.getText();
		
		if(vecchia.isBlank() || nuova.isBlank()) {
			if(oldPassField.getText().isBlank()) {
				new Notification("Perfavore inserisci la vecchia password da cambiare.", NotificationType.ERROR).show();
			} else if(newPassField.getText().isBlank()) {
				new Notification("Perfavore inserisci la nuova password.", NotificationType.ERROR).show();
			}
			return;
		}
		controller.cambiaPassword(vecchia, nuova);
		this.pulisciCampi();
	}
	
	@FXML
	private void eliminaAccount() throws IOException {
		// controlla che i campi non siano vuoti
		String password = passField2.getText();
		if(password.isBlank()) {
			new Notification("Perfavore inserisci la password dell'account", NotificationType.ERROR).show();
		} else {			
			// crea l'alert
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.IMPOSTAZIONI, "Alert"));
			AnchorPane editor = fxmlLoader.load();
			Modal modal = new Modal(editor, "Attenzione!");
			modal.setDimensioni(635, 400);
			Button okBtn = modal.getButton(ButtonType.OK);
			okBtn.setText("SI");
			okBtn.setStyle("-fx-background-color: #E7515A");
			modal.getButton(ButtonType.CANCEL).setText("NO");
			
			// apre il dialog e attende
			ButtonType btnCliccato = modal.showAndWait();
			if(btnCliccato == ButtonType.OK) {
				this.controller.eliminaAccount(password);
			}
		}
	}
	
}
