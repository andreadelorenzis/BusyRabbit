package main.Views.AccountManager.interfacce;

import main.Views.View;

public interface IPageView extends View {
	public void erroreConfermaPassword();
	public void erroreEmailEsistente();
	public void erroreCredenzialiSbagliate();
}
