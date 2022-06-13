package main.controller.accountmanager;

import main.controller.IController;

public interface IAccessController extends IController {
	
	public boolean accedi(String email, String password);
	
	public boolean registraAccount(String nome, String email, String password, String confirmPass);
}
