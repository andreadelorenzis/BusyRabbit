package main.controller;

import main.views.IView;

/**
 * Controller base
 */
public interface IController {
	
	public void setView(IView v);
	
	public IView getView();
}
