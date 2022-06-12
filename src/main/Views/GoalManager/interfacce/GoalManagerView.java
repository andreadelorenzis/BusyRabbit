package main.views.goalmanager.interfacce;

import java.util.List;

import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IObiettivo;

public interface GoalManagerView {
	public void aggiorna(List<IObiettivo> obiettivi);
	public void errore(String message);
	public void successo(String message);
	public void info(String message);
}
