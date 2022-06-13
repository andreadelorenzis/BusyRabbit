package main.controller.impostazioni;

import main.model.accountmanager.classi.AccountManager;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.model.accountmanager.interfacce.IAccountManager;
import main.views.IView;
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
