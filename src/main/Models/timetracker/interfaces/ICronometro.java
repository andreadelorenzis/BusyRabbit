package main.Models.timetracker.interfaces;

/**
 * 
 * Un semplice cronometro che permette di monitorare attività ad oltranza. Viene fatto partire su un'attività,
 * una volta che il cronometro termina, la durata di quell'attività viene modificata, e l'attività viene aggiunta
 * al TimeTracker.
 *
 */
public interface ICronometro extends ITracker {
	
	/**
	 * 
	 * @return il periodo di tempo monitorato fino al momento attuale, in secondi
	 */
	public long getTempoAttuale();
	
}
