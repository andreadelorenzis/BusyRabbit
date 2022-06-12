package main.model.timetracker.interfacce;

/**
 * Interfaccia che permette ad un oggetto di essere notificato alla fine di un timer
 */
public interface ITrackable {

	/**
	 * Avvisa l'ascoltatore che il timer è terminato.
	 * 
	 * @param tempo monitorato in secondi
	 */
	void timerTerminato(long tempo);
	
	/**
	 * Avvisa l'ascoltatore che è passato un secondo.
	 * 
	 * @param o ore del tracker
	 * @param m minuti del tracker
	 * @param s secondi del tracker
	 */
	void secondoPassato(int o, int m, int s);
	
}
