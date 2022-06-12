package main.controller;

import main.views.View;

public interface Controller {
	
	public void setView(View v);
	
	public View getView();
}
