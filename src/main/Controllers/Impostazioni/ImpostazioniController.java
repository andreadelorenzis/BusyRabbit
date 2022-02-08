package main.Controllers.Impostazioni;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Controllers.Notifications.Notification;
import main.Controllers.Notifications.NotificationType;
import main.Models.accountmanager.classes.ExistingAccountException;
import main.Models.accountmanager.classes.WrongCredentialsException;
import main.Models.accountmanager.interfaces.IApp;

public class ImpostazioniController {
	@FXML
	private TextField oldPassField;
	@FXML
	private TextField newPassField;
	@FXML
	private TextField newEmailField;
	@FXML
	private TextField passField1;
	@FXML
	private TextField passField2;
	@FXML
	private Button changeBtn1;
	@FXML
	private Button changeBtn2;
	@FXML
	private Button deleteBtn;
	
	private IApp app;
	
	public void setApp(IApp app) {
		this.app = app;
	}
	
	public void cambiaEmail() {
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
		try {
			app.cambiaEmail(email, password);
		} catch (ExistingAccountException | WrongCredentialsException e) {
			if(e instanceof ExistingAccountException) {
				new Notification("E' già presente un'altro account con questa email.", NotificationType.ERROR).show();
			}
			
		}
	}
	
	public void cambiaPassword() {
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
		try {
			app.cambiaPassword(vecchia, nuova);
		} catch (WrongCredentialsException e) {
			new Notification("La password non è corretta", NotificationType.ERROR).show();
		}
	}
	
	public void eliminaAccount() {
		String password = passField2.getText();
		
		if(password.isBlank()) {
			new Notification("Perfavore inserisci la password dell'account", NotificationType.ERROR).show();
		}
		try {
			app.eliminaAccount(app.getEmail(), password);
		} catch (WrongCredentialsException e) {
			new Notification("La password non è corretta", NotificationType.ERROR).show();
		}
	}
}
