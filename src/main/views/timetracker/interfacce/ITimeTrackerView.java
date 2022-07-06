package main.views.timetracker.interfacce;

import java.util.List;
import main.model.timetracker.interfacce.IActivity;
import main.views.IView;

/**
 * View a cui Ë collegato il GoalManagerController
 */
public interface ITimeTrackerView extends IView {
	
	public void aggiornaView(List<List<IActivity>> giorni, int pagina);
	
	public void progettoAggiunto();
	
	public void attivit‡Aggiunta();
	
	public void visualizzaOrologio(int ore, int min, int sec);

	public void trackerTerminato();
	
}
