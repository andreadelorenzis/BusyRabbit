package main.Controllers.Impostazioni;

public interface ImpostazioniController {
	public void cambiaEmail(String email, String pass);
	
	public void cambiaPassword(String vecchia, String nuova);
	
	public void eliminaAccount(String pass);
}
