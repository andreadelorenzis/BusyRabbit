package main.Controllers.TimeTracker;

import main.Colori;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;
import main.Models.timetracker.interfaces.ITrackable;

public interface TimeTrackerController extends ITrackable {
	
	public void aggiungiProgetto(IProgetto p);
	
	public void modificaProgetto(IProgetto p1, IProgetto p2);
	
	public void eliminaProgetto(IProgetto p);
	
	public void aggiungiAttività(IAttività a);
	
	public void modificaAttività(IAttività a1, IAttività a2);
	
	public void eliminaAttività(IAttività a);

	public void incrementaPagina();
	
	public void decrementaPagina();
	
	public void avviaTracker(String nome);
	
	public void terminaTracker();
	
	public void scegliCronometro();

	public void scegliPomodoro();
	
	public void impostaPomodoroTimer(int sessione, int pausaBreve, int pausaLunga);
	
}
