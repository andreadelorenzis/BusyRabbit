package main.Views.GoalManager.interfaces;

import java.util.List;

import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IObiettivo;

public interface GoalManagerView {
	public void aggiorna(List<IObiettivo> obiettivi);
	public void errore(String message);
	public void successo(String message);
	public void info(String message);
}
