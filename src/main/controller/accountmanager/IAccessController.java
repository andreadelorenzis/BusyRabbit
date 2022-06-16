package main.controller.accountmanager;

import main.controller.IController;

/**
 * Controller collegato alle View di login e registrazione
 */
public interface IAccessController extends IController {
	
	public boolean accedi(String email, String password);
	
	public boolean registraAccount(String nome, String email, String password, String confirmPass);
}
