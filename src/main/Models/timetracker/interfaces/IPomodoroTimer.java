package main.Models.timetracker.interfaces;

/**
 * 
 * Un timer ad intervalli che funziona secondo la tecnica del pomodoro: si alternano sessioni di lavori e periodi di pausa. 
 * Dopo ogni sessione di lavoro c'è una pausa breve. Dopo N ripetizioni, c'è una pausa lunga. 
 * Di default il timer ha sessione 30M, pausa breve 5M, pausa lunga 10M, 3 cicli (sessione - pausa breve) dopo il quale inizia
 * una pausa lunga.
 * I tempi sono impostati in millisecondi.
 *
 */
public interface IPomodoroTimer extends ITracker {
	
	/**
	 * Ogni volta che termina una sessione notifica l'ascoltatore, inviandogli la durata di tempo monitorata
	 */
	public void terminaSessione();
	
	/**
	 * Imposta la durata della sessione di lavoro
	 * 
	 * @param durata
	 */
    public void setDurataSessione(int durata);
    
    /**
     * Imposta la durata della pausa breve
     * 
     * @param durata
     */
    public void setDurataPausaBreve(int durata);
  
    /**
     * Imposta la durata della pausa lunga
     * 
     * @param durata
     */
    public void setDurataPausaLunga(int durata);
  
    /**
     * Imposta i cicli (sessione-pausaBreve) dopo i quali inizia una pausa lunga
     * 
     * @param nCicli cicli prima di una pausa lunga
     */
    public void setNCicli(int nCicli);
    
    /**
     * 
     * @return durata di una sessione di lavoro
     */
    public int getDurataSessione();
    
    /**
     * 
     * @return durata di una pausa breve
     */
    public int getDurataPausaBreve();
    
    /**
     * 
     * @return durata di una pausa lunga in secondi
     */
    public int getDurataPausaLunga();
    
    /**
     * 
     * @return cicli prima di una pausa lunga
     */
    public int getNCicli();
     
}
