package main.Views.TimeTracker.interfaces;

import java.util.List;

import main.Controllers.TimeTracker.TimeTrackerController;
import main.Models.timetracker.interfaces.IAttivit‡;
import main.Models.timetracker.interfaces.IProgetto;

public interface TimeTrackerView {
	public void aggiornaView(List<List<IAttivit‡>> giorni, int pagina);
	
	public void progettoAggiunto();
	
	public void attivit‡Aggiunta();
	
	public void visualizzaOrologio(int ore, int min, int sec);

	public void trackerTerminato();
	
}
