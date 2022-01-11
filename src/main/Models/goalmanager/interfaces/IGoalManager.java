package main.Models.goalmanager.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Gestore degli obiettivi.
 */
public interface IGoalManager {
    
    /**
     * Aggiunge un nuovo obiettivo scomponibile
     * 
     * @param obiettivo 
     */
    public void aggiungiObiettivoScomponibile(IObiettivo obiettivo);
    
    /**
     * Aggiunge un nuovo ObiettivoAzione
     * 
     * @param obiettivo
     */
    public void aggiungiObiettivoAzione(IObiettivo obiettivo);
    
    /**
     * 
     * @return la lista di tutti gli obiettivi 
     */
    public List<IObiettivo> getObiettivi();
    
    /**
     * Verifica se la data di oggi coincide con quella di raggiungimento di qualche obiettivo
     * salvato e, in caso affermativo, fa fallire tali obiettivi
     * 
     * @param data la data di oggi
     * @return la lista degli obiettivi da completare oggi
     */
    public void calcolaScadenzeObiettivi(LocalDate data);

    /**
     * Calcola l'elenco delle azioni collegate da svolgere nella data specificata.
     * 
     * @param data la data del giorno
     * @return la lista degli obiettivi da completare oggi
     */
    public List<IObiettivo> calcolaAzioniGiornaliere(LocalDate data);
    
    /**
     * Elimina un obiettivo.
     * 
     * @param ID Identificativo dell'obiettivo.
     */
    public void eliminaObiettivo(String idObiettivo);
    
}
