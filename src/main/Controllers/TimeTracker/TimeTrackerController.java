package main.Controllers.TimeTracker;

import main.Models.timetracker.interfaces.IAttivit�;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITrackable;

public interface TimeTrackerController extends ITrackable {
	
	public void aggiungiProgetto(IProgetto p);
	
	public void modificaProgetto(IProgetto p1, IProgetto p2);
	
	public void eliminaProgetto(IProgetto p);
	
	public void aggiungiAttivit�(IAttivit� a);
	
	public void modificaAttivit�(IAttivit� a1, IAttivit� a2);
	
	public void eliminaAttivit�(IAttivit� a);

	public void incrementaPagina();
	
	public void decrementaPagina();
	
	public int getPagina();
	
	public void avviaTracker(String nome, IProgetto p);
	
	public void terminaTracker();
	
	public void scegliCronometro();

	public void scegliPomodoro();
	
	public void impostaPomodoroTimer(int sessione, int pausaBreve, int pausaLunga);
	
}
