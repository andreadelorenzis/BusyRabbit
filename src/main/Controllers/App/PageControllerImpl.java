package main.Controllers.App;

import main.Views.App.interfaces.PageView;

public class PageControllerImpl implements PageController {
	private PageView view;
	
	public PageControllerImpl(PageView view) {
		this.view = view;
	}
}
