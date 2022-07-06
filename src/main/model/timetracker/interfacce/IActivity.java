package main.model.timetracker.interfacce;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * Attività monitorata dall'utente. Ogni attività deve fare parte di un progetto.
 *
 */
public interface IActivity {
    
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
     * @param data dell'attività
     */
    public void setData(LocalDate data);
    
    /**
     * 
     * @param ora orario dell'attività
     */
    public void setOra(LocalTime ora);
    
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
