package main.controller.timetracker;

import main.controller.IController;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITrackable;

public interface ITimeTrackerController extends ITrackable, IController {
	
	public void aggiungiProgetto(IProgetto p);
	
	public void modificaProgetto(IProgetto p1, IProgetto p2);
	
	public void eliminaProgetto(IProgetto p);
	
	public void aggiungiAttività(IAttività a);
	
	public void modificaAttività(IAttività a1, IAttività a2);
	
	public void eliminaAttività(IAttività a);

	public void incrementaPagina();
	
	public void decrementaPagina();
	
	public int getPagina();
	
	public void avviaTracker(String nome, IProgetto p);
	
	public void terminaTracker();
	
	public void scegliCronometro();

	public void scegliPomodoro();
	
	public void impostaPomodoroTimer(int sessione, int pausaBreve, int pausaLunga);
	
}
