package main.controller.impostazioni;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.model.accountmanager.classi.AccountManager;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.model.accountmanager.interfacce.IAccountManager;
import main.views.IView;
import main.views.LoaderRisorse;
import main.views.accountmanager.classi.AppView;
import main.views.accountmanager.classi.PageView;
import main.views.impostazioni.interfacce.IImpostazioniView;
import main.views.notification.Notification;
import main.views.notification.NotificationType;

public class ImpostazioniController implements IImpostazioniController {

	private IImpostazioniView view;
	
	private IAccountManager app = AccountManager.getInstance();;
	
	@Override
	public void setView(IView v) {
		this.view = (IImpostazioniView) v;
	}

	@Override
	public IView getView() {
		return (IView) this.view;
	}

	@Override
	public void cambiaEmail(String email, String pass) {
		try {
			app.cambiaEmail(email, pass);
			view.emailCambiata();
		} catch (ExistingAccountException | WrongCredentialsException e) {
			if(e instanceof ExistingAccountException) {
				view.errore("E' già presente un'altro account con questa email.");
			} else if(e instanceof WrongCredentialsException) {
				view.errore("La password non è corretta.");
			}
		}
	}

	@Override
	public void cambiaPassword(String vecchia, String nuova) {
		try {
			app.cambiaPassword(vecchia, nuova);
			view.passwordCambiata();
		} catch (WrongCredentialsException e) {
			view.errore("La password non è corretta.");
		}
	}

	@Override
	public void eliminaAccount(String pass) {
		try {
			boolean eliminato = app.eliminaAccount(app.getEmail(), pass);
			if(eliminato)
				view.accountEliminato();
			else 
				view.errore("Non è stato possibile elimare l'account.");
		} catch (WrongCredentialsException e) {
			view.errore("La password non è corretta.");
		}
	}	
	
}
