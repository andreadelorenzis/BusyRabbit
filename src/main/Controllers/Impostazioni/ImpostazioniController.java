package main.Controllers.Impostazioni;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
				System.out.println("Perfavore inserisci una email.");
			} else if(passField1.getText().isBlank()) {
				System.out.println("Perfavore inserisci la password");
			}
			return;
		}
		try {
			app.cambiaEmail(email, password);
		} catch (ExistingAccountException | WrongCredentialsException e) {
			e.printStackTrace();
		}
	}
	
	public void cambiaPassword() {
		String vecchia = oldPassField.getText();
		String nuova = newPassField.getText();
		
		if(vecchia.isBlank() || nuova.isBlank()) {
			if(oldPassField.getText().isBlank()) {
				System.out.println("Perfavore inserisci la vecchia password da cambiare.");
			} else if(newPassField.getText().isBlank()) {
				System.out.println("Perfavore inserisci la nuova password.");
			}
			return;
		}
		try {
			app.cambiaPassword(vecchia, nuova);
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminaAccount() {
		String password = passField2.getText();
		
		if(password.isBlank()) {
			System.out.println("Perfavore inserisci la password dell'account.");
		}
		try {
			app.eliminaAccount(app.getEmail(), password);
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
	}
}
