package main.views.impostazioni.interfacce;

import main.views.IView;

public interface IImpostazioniView extends IView {
	
	public void emailCambiata();
	
	public void passwordCambiata();
	
	public void accountEliminato();
	
	public void errore(String s);
	
}
