package main.model.timetracker.interfacce;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import main.model.timetracker.classi.TrackerType;

/**
 * 
 * Contiene funzionalità per:
 * - creare nuove attività
 * - creare nuovi progetti
 * - far partire e terminare gli strumenti di monitoraggio del tempo (Pomodoro/Cronometro/ecc..)
 *
 */
public interface ITimeTracker {
	
	/**
	 * Fa partire il tracker (PomodoroTimer o Cronometro)
	 */
	public void avviaTracker(IActivity a);
	
	/**
	 * Termina il tracker, aggiungendo l'attività con la durata giusta
	 */
	public void terminaTracker();
	
	/**
	 * Sceglie quale tracker attivare, se il PomodoroTimer o il Cronometro
	 * 
	 * @param t lo strumento da attivare
	 */
	public void scegliTracker(TrackerType t);
    
	/**
	 * Attività da aggiungere. Contiene l'id del progetto in cui inserirla
	 * (se il progetto non esiste va nel progetto di default).
	 * 
	 * @param attività
	 */
    public void aggiungiAttività(IActivity attività);
    
    /**
     * 
     * @param idAttività
     */
    public void eliminaAttività(IActivity a);
    
    /**
     * 
     * @param progetto
     */
    public void aggiungiProgetto(IProgetto progetto);
    
    /**
     * 
     * @param idProgetto
     */
    public void eliminaProgetto(String idProgetto);
    
    /**
     * Restituisce i giorni di monitoraggio, ossia tutte le attività svolte divise per giorni.
     * Restituisce 10 giorni di monitoraggio, scegliendo quali restituire in base all'indice di paginazione.
     * I giorni sono restituiti in ordine cronologia inverso: dal più recente al meno recente.
     * 
     * @param indice di paginazione
     * @return lista di giorni
     */
    public List<List<IActivity>> getGiorniAttività(int pagina);
    
    /**
     * Restituisce il totale di tutti i giorni salvati
     */
    public int getNumGiorni();
    
    /**
     * 
     * @return lista di progetti creati
     */
    public List<IProgetto> getProgetti();
    
    /**
     * 
     * @return la lista di tutte le attività
     */
    public List<IActivity> getAttività();
    
    /**
     * 
     * @return il time tracker attualmente utilizzato
     */
    public ITracker getTracker();
    
    /**
     * 
     * @return l'attività attualmente monitorata
     */
    public IActivity getAttivitàCorrente();
}
