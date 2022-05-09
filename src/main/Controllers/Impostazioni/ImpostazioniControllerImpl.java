package main.Controllers.Impostazioni;

import main.Models.accountmanager.classes.AccountManager;
import main.Models.accountmanager.classes.ExistingAccountException;
import main.Models.accountmanager.classes.WrongCredentialsException;
import main.Models.accountmanager.interfaces.IAccountManager;
import main.Views.Impostazioni.interfaces.ImpostazioniView;
import main.Views.Notifications.Notification;
import main.Views.Notifications.NotificationType;

public class ImpostazioniControllerImpl implements ImpostazioniController {

	private ImpostazioniView view;
	
	private IAccountManager app;
	
	public ImpostazioniControllerImpl(ImpostazioniView view) {
		this.view = view;
		app = AccountManager.getInstance();
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
			new Notification("La password non è corretta", NotificationType.ERROR).show();
		}
	}

	@Override
	public void eliminaAccount(String pass) {
		try {
			app.eliminaAccount(app.getEmail(), pass);
			view.accountEliminato();
		} catch (WrongCredentialsException e) {
			new Notification("La password non è corretta", NotificationType.ERROR).show();
		}
	}
	
	
	
}
