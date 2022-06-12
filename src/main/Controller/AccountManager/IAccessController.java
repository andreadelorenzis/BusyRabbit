package main.controller.accountmanager;

import main.Controller.Controller;

public interface IAccessController extends Controller {
	
	public boolean accedi(String email, String password);
	
	public boolean registraAccount(String nome, String email, String password, String confirmPass);
}
