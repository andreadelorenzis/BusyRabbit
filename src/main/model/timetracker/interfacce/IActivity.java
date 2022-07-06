package main.model.timetracker.interfacce;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * Attivit� monitorata dall'utente. Ogni attivit� deve fare parte di un progetto.
 *
 */
public interface IActivity {
    
	/**
	 * 
	 * @param nome dell'attivit�
	 */
    public void setNome(String nome);
    
    /**
     * 
     * @param durata durata in secondi
     */
    public void setDurata(long durata);
    
    /**
     * 
     * @param id del progetto di cui fa parte questa attivit�
     */
    public void setProgettoPadre(IProgetto progetto);
    
    /**
     * 
     * @param data dell'attivit�
     */
    public void setData(LocalDate data);
    
    /**
     * 
     * @param ora orario dell'attivit�
     */
    public void setOra(LocalTime ora);
    
    /**
     * 
     * @return nome dell'attivit�
     */
    public String getNome();
    
    /**
     * 
     * @return data di inizio svolgimento
     */
    public LocalDate getData();
    
    /**
     * 
     * @return ora di inizio dell'attivit�
     */
    public LocalTime getOraInizio();
    
    /**
     * 
     * @return l'ora di fine attivit�
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
     * @return id del progetto di cui fa parte questa attivit�
     */
    public IProgetto getProgetto();
        
}
