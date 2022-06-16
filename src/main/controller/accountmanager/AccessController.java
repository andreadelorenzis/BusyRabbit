package main.controller.accountmanager;

import main.model.accountmanager.classi.AccountManager;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.InvalidEmailException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.views.IView;

public class AccessController implements IAccessController {
	
	private IView view; 
	
	private AccountManager app = AccountManager.getInstance();

	@Override
	public void setView(IView v) {
		this.view = v;
	}

	@Override
	public IView getView() {
		return view;
	}
	
	@Override
    public boolean accedi(String email, String password) {
		IView pageView = view;
		try {
			app.accedi("mariorossi@gmail.com", "password123");
			//app.accedi(email, password);
			return true;
		} catch (WrongCredentialsException e) {
			pageView.errore("Email o password non sono corrette");
			return false;
		}
    }

	@Override
	public boolean registraAccount(String nome, String email, String password, String confirmation) {
		IView pageView = view;
		try {
			app.registraAccount(nome, email, password, confirmation);
			return true;
		} catch (WrongCredentialsException | ExistingAccountException | InvalidEmailException e) {
			if(e instanceof WrongCredentialsException) {
				pageView.errore("Le due password non coincidono");
			} else if(e instanceof ExistingAccountException) {
				pageView.errore("L'email è già utilizzata da un altro account");
			} else if(e instanceof InvalidEmailException) {
				pageView.errore("Email non valida");
			}
			return false;
		}
	}

}
