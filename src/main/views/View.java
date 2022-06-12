package main.views;

import main.controller.Controller;

public interface View {
	
	public void setController(Controller c);
	
	public Controller getController();
	
}

