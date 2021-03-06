package main.views.goalmanager.interfacce;

import java.util.List;
import main.model.goalmanager.interfacce.IObiettivo;
import main.views.IView;

/**
 * View a cui ? collegato il GoalManagerController
 */
public interface IGoalManagerView extends IView {
	
	public void aggiornaObiettivi(List<IObiettivo> obiettivi);
	
	public void errore(String message);
	
	public void successo(String message);
	
	public void info(String message);
	
}
