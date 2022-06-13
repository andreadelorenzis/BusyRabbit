package main.views;

import main.controller.IController;

public interface IView {

	public void setController(IController c);
	
	public IController getController();
	
}

