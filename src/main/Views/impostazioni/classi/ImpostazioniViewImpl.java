package main.views.impostazioni.classi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.controller.impostazioni.ImpostazioniController;
import main.controller.impostazioni.ImpostazioniControllerImpl;
import main.controller.timetracker.TimeTrackerController;
import main.controller.timetracker.TimeTrackerControllerImpl;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.views.impostazioni.interfacce.ImpostazioniView;
import main.views.notification.Notification;
import main.views.notification.NotificationType;

public class ImpostazioniViewImpl implements ImpostazioniView {
	
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
	
	private ImpostazioniController controller;
	
	@FXML
	private void initialize() {
        ImpostazioniController controller = new ImpostazioniControllerImpl(this);
        this.controller = controller;
	}

	@Override
	public void emailCambiata() {
		new Notification("Email cambiata con successo", NotificationType.SUCCESS).show();
	}

	@Override
	public void passwordCambiata() {
		new Notification("Password cambiata con successo", NotificationType.SUCCESS).show();
	}

	@Override
	public void accountEliminato() {
		new Notification("Account eliminato", NotificationType.INFO).show();
	}

	@Override
	public void errore(String s) {
		new Notification(s, NotificationType.ERROR).show();
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
	}
	
	@FXML
	private void eliminaAccount() {
		String password = passField2.getText();
		
		if(password.isBlank()) {
			new Notification("Perfavore inserisci la password dell'account", NotificationType.ERROR).show();
		}
		controller.eliminaAccount(password);
	}
}
