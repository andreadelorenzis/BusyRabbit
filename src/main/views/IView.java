package main.views;

import main.controller.IController;

/**
 * View base
 */
public interface IView {

	public void setController(IController c);
	
	public IController getController();
	
	public void successo(String m);
	
	public void errore(String m);
	
	public void info(String m);
	
}

