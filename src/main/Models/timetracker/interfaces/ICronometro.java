package main.Models.timetracker.interfaces;

/**
 * 
 * Un semplice cronometro che permette di monitorare attivit� ad oltranza. Viene fatto partire su un'attivit�,
 * una volta che il cronometro termina, la durata di quell'attivit� viene modificata, e l'attivit� viene aggiunta
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
