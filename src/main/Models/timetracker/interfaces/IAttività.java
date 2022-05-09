package main.Models.timetracker.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * Attività monitorata dall'utente. Ogni attività deve fare parte di un progetto.
 *
 */
public interface IAttività {
    
	/**
	 * 
	 * @param nome dell'attività
	 */
    public void setNome(String nome);
    
    /**
     * 
     * @param durata durata in secondi
     */
    public void setDurata(long durata);
    
    /**
     * 
     * @param id del progetto di cui fa parte questa attività
     */
    public void setProgettoPadre(IProgetto progetto);
    
    /**
     * 
     * @return nome dell'attività
     */
    public String getNome();
    
    /**
     * 
     * @return data di inizio svolgimento
     */
    public LocalDate getData();
    
    /**
     * 
     * @return ora di inizio dell'attività
     */
    public LocalTime getOraInizio();
    
    /**
     * 
     * @return l'ora di fine attività
     */
    public LocalTime getOraFine();
    
    /**
     * 
     * @return durata in secondi
     */
    public long getDurata();
    
    /**
     * 
     * @return identificativo
     */
    public String getId();
    
    /**
     * 
     * @return id del progetto di cui fa parte questa attività
     */
    public IProgetto getProgetto();
        
}
