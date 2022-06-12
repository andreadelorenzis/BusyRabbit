package main.Views;

import main.Controllers.Controller;

public interface View {
	
	public void setController(Controller c);
	
	public Controller getController();
	
}

