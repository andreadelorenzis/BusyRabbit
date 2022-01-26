package main.Models.timetracker.interfaces;

/**
 * 
 * Modella un generico strumento per monitorare il tempo.
 *
 */
public interface ITracker {
	
	/**
	 * Inizia a monitorare il tempo con il tracker
	 */
	public void avvia();
	
	/**
	 * Termina il monitoraggio del tempo
	 * 
	 * @return il periodo di tempo totale monitorato, in secondi
	 */
	public long termina();
	
	/**
	 * 
	 * @return se il tracker � attualmente avviato o no
	 */
	public boolean getAvviato();
	
    /**
     * 
     * @param ascoltatore che verra informato quando una sessione del timer termina
     */
    public void setAscoltatore(ITrackable ascoltatore);
	
}
