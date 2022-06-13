package main.views.accountmanager.interfacce;

import main.views.IView;

public interface IPageView extends IView {
	
	public void erroreConfermaPassword();
	
	public void erroreEmailEsistente();
	
	public void erroreCredenzialiSbagliate();
}
