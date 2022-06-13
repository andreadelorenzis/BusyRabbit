package main.controller.impostazioni;

import main.controller.IController;

public interface IImpostazioniController extends IController {
	public void cambiaEmail(String email, String pass);
	
	public void cambiaPassword(String vecchia, String nuova);
	
	public void eliminaAccount(String pass);
}
