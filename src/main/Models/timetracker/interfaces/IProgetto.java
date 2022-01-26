package main.Models.timetracker.interfaces;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import main.Colori;
import main.Mese;

/**
 * 
 * Un progetto con un nome ed un colore a cui associare delle attività. Ogni progetto contiene l'informazione
 * circa la durata totale annuale (divisa per mesi), e per ogni anno quella mensile (divisa per giorni). 
 *
 */
public interface IProgetto {
        
	/**
	 * Aggiunge la durata dell'attività ai tempi del progetto
	 * 
	 * @param attività 
	 */
    public void aggiungiDurata(IAttività attività);
    
    /**
     * Rimuove la durata dell'attività dai tempi del progetto
     * 
     * @param attività
     */
    public void eliminaDurata(IAttività attività);
    
    /**
     * Ottiene il tempo totale del progetto (somme delle durate delle attività) per ogni mese in un anno specifico.
     * 
     * @param anno in cui effettuare il calcolo
     * @return una map le cui chiavi sono i mesi (1-12) e i cui valori sono il tempo totale del progetto 
     * 		   durante quei mesi, in secondi
     */
    public Map<Integer, Long> getAnnoProgetto(int anno);
    
    /**
     * Ottiene il tempo totale del progetto (somme delle durate delle attività) per ogni giorno in un mese specifico.
     * 
     * @param mese in cui effettuare il calcolo
     * @return una map le cui chiavi sono i giorni e i cui valori sono il tempo totale del progetto in
     * 		   quei giorni, in secondi
     */
    public Map<Integer, Long> getMeseProgetto(int anno, Month mese);
    
    /**
     * 
     * @param nome del progetto
     */
    public void setNome(String nome);
    
    /**
     * 
     * @return nome del progetto
     */
    public String getNome();
     
    /**
     * 
     * @param colore del progetto
     */
    public void setColore(Colori colore);
    
    /**
     * 
     * @return colore del progetto
     */
    public Colori getColore();
    
    /**
     * 
     * @return identificativo del progetto
     */
    public String getId();
   
}
