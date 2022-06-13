package main.controller;

import main.views.IView;

public interface IController {
	
	/**
	 * Imposta la View collegata al Controller
	 */
	public void setView(IView v);
	
	/**
	 * Ottiene la View collegata al Controller
	 */
	public IView getView();
}
