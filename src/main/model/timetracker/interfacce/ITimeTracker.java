package main.model.timetracker.interfacce;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import main.model.timetracker.classi.TrackerType;

/**
 * 
 * Contiene funzionalit� per:
 * - creare nuove attivit�
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
	 * Termina il tracker, aggiungendo l'attivit� con la durata giusta
	 */
	public void terminaTracker();
	
	/**
	 * Sceglie quale tracker attivare, se il PomodoroTimer o il Cronometro
	 * 
	 * @param t lo strumento da attivare
	 */
	public void scegliTracker(TrackerType t);
    
	/**
	 * Attivit� da aggiungere. Contiene l'id del progetto in cui inserirla
	 * (se il progetto non esiste va nel progetto di default).
	 * 
	 * @param attivit�
	 */
    public void aggiungiAttivit�(IActivity attivit�);
    
    /**
     * 
     * @param idAttivit�
     */
    public void eliminaAttivit�(IActivity a);
    
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
     * Restituisce i giorni di monitoraggio, ossia tutte le attivit� svolte divise per giorni.
     * Restituisce 10 giorni di monitoraggio, scegliendo quali restituire in base all'indice di paginazione.
     * I giorni sono restituiti in ordine cronologia inverso: dal pi� recente al meno recente.
     * 
     * @param indice di paginazione
     * @return lista di giorni
     */
    public List<List<IActivity>> getGiorniAttivit�(int pagina);
    
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
     * @return la lista di tutte le attivit�
     */
    public List<IActivity> getAttivit�();
    
    /**
     * 
     * @return il time tracker attualmente utilizzato
     */
    public ITracker getTracker();
    
    /**
     * 
     * @return l'attivit� attualmente monitorata
     */
    public IActivity getAttivit�Corrente();
}
