package main.views.timetracker.interfacce;

import java.util.List;

import main.Controller.TimeTracker.TimeTrackerController;
import main.model.timetracker.interfacce.IAttivit‡;
import main.model.timetracker.interfacce.IProgetto;

public interface TimeTrackerView {
	public void aggiornaView(List<List<IAttivit‡>> giorni, int pagina);
	
	public void progettoAggiunto();
	
	public void attivit‡Aggiunta();
	
	public void visualizzaOrologio(int ore, int min, int sec);

	public void trackerTerminato();
	
}
