package main.views.timetracker.interfacce;

import java.util.List;

import main.controller.timetracker.ITimeTrackerController;
import main.model.timetracker.interfacce.IAttivit‡;
import main.model.timetracker.interfacce.IProgetto;
import main.views.IView;

public interface ITimeTrackerView extends IView {
	
	public void aggiornaView(List<List<IAttivit‡>> giorni, int pagina);
	
	public void progettoAggiunto();
	
	public void attivit‡Aggiunta();
	
	public void visualizzaOrologio(int ore, int min, int sec);

	public void trackerTerminato();
	
}
