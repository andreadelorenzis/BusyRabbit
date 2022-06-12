package main.Controllers.AccountManager;

import main.Controllers.Controller;

public interface IAccessController extends Controller {
	
	public void accedi(String email, String password);
	
	public void registraAccount(String email, String password, String confirmPass);
}
