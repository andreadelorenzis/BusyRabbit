package main.Controllers.App;

import main.Views.App.interfaces.AppView;

public class AppControllerImpl implements AppController {
	
	private AppView view;

	public AppControllerImpl(AppView view) {
		this.view = view;
	}
	
}
