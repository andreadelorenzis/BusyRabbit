package main.model.timetracker.interfacce;

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
	 * @return se il tracker è attualmente avviato o no
	 */
	public boolean getAvviato();
	
    /**
     * 
     * @param ascoltatore
     */
    public void registraAscoltatore(ITrackable ascoltatore);
    
    /**
     * 
     * @param indice ascoltatore
     */
    public void cancellaAscoltatore(int i);
   
    /**
     * Notifica tutti gli ascoltatori dell'evento di passaggio di un secondo
     */
	public abstract void notificaAscoltatoriSecondoPassato();
	
	/**
     * Notifica tutti gli ascoltatori dell'evento di terminazione
     */
	public abstract void notificaAscoltatoriTrackerTerminato();
	
	/**
	 * 
	 * @return numero di ascoltatori collegati
	 */
	public int getNumAscoltatori();
	
}
