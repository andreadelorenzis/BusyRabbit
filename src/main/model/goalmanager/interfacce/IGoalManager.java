package main.model.goalmanager.interfacce;

import java.time.LocalDate;
import java.util.List;

/**
 * Gestore degli obiettivi.
 */
public interface IGoalManager { 
    
    /**
     * Aggiunge un nuovo obiettivo
     * 
     * @param obiettivo
     */
    public void aggiungiObiettivo(IObiettivo obiettivo);
    
    /**
     * Aggiunge un nuovo sotto-obiettivo
     * 
     * @param obiettivo
     */
    public void aggiungiSottoObiettivo(IObiettivoScomponibile padre, IObiettivo figlio);
    
    /**
     * Verifica alla data passata se qualche obiettivo è scaduto e, in caso affermativo, fa fallire tali obiettivi
     * 
     * @param data la data di oggi
     * @return la lista degli obiettivi da completare oggi
     */
    public void verificaScadenzeObiettivi(LocalDate data);

    /**
     * Calcola l'elenco delle azioni collegate da svolgere nella data specificata.
     * 
     * @param data la data del giorno
     * @return la lista degli obiettivi da completare oggi
     */
    public List<IAzione> calcolaAzioniGiornaliere(LocalDate data);
    
    
    /**
     * Elimina un obiettivo.
     * 
     * @param ID Identificativo dell'obiettivo.
     */
    public void eliminaObiettivo(String idObiettivo);
    
    /**
     * 
     * @return la lista di tutti gli obiettivi 
     */
    public List<IObiettivo> getObiettivi();
    
}
