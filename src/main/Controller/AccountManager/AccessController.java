package main.controller.accountmanager;

import java.io.IOException;

import javafx.event.ActionEvent;
import main.model.accountmanager.classi.AccountManager;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.model.accountmanager.interfacce.IAccountManager;
import main.views.View;
import main.views.AccountManager.interfacce.IPageView;
import main.views.Notifications.Notification;
import main.views.Notifications.NotificationType;

public class AccessController implements IAccessController {
	
	private View view; 
	
	private AccountManager app = AccountManager.getInstance();

	@Override
	public void setView(View v) {
		this.view = v;
	}

	@Override
	public View getView() {
		return view;
	}
	
    public boolean accedi(String email, String password) {
		IPageView pageView = (IPageView) view;
		try {
			app.accedi("newemail@gmail.com", "pass123");
			return true;
		} catch (WrongCredentialsException e) {
			pageView.erroreCredenzialiSbagliate();
			return false;
		}
    }

	@Override
	public boolean registraAccount(String nome, String email, String password, String confirmation) {
		IPageView pageView = (IPageView) view;
		try {
			app.registraAccount(nome, email, password, confirmation);
			return true;
		} catch (WrongCredentialsException | ExistingAccountException e) {
			if(e instanceof WrongCredentialsException) {
				pageView.erroreConfermaPassword();
			} else if(e instanceof ExistingAccountException) {
				pageView.erroreEmailEsistente();
			}
			return false;
		}
	}

}
