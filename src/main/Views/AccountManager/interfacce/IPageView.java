package main.views.accountmanager.interfacce;

import main.views.View;

public interface IPageView extends View {
	public void erroreConfermaPassword();
	public void erroreEmailEsistente();
	public void erroreCredenzialiSbagliate();
}
