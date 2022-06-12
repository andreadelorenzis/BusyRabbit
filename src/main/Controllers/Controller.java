package main.Controllers;

import main.Views.View;

public interface Controller {
	
	public void setView(View v);
	
	public View getView();
}
