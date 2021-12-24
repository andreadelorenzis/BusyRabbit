/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.goalmanager.interfaces;

import java.util.Date;
import main.Models.goalmanager.classes.Azione;

/**
 * Gestore degli obiettivi.
 */
public interface IGoalManager {
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param descrizione Descrizione dell'obiettivo.
     * @param dataRaggiungimento Data entro cui raggiungere l'obiettivo.
     */
    public void aggiungiObiettivoSemplice(String nome, String descrizione, Date dataRaggiungimento);
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param descrizione Descrizione dell'obiettivo.
     * @param dataRaggiungimento Data entro cui raggiungere l'obiettivo.
     * @param valore Valore totale dell'obiettivo da raggiungere.
     * @param unità Unità di misura dell'obiettivo
     * @param listaAzioni Lista di azioni collegate all'obiettivo.
     */
    public void aggiungiObiettivoMisurabile(String nome, String descrizione, Date dataRaggiungimento, 
            int valore, String unità, Azione[] listaAzioni);
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param descrizione Descrizione dell'obiettivo.
     * @param dataRaggiungimento Data entro cui raggiungere l'obiettivo.
     * @param ID Identificativo dell'obiettivo.
     */
    public void modificaObiettivoSemplice(String nome, String descrizione, Date dataRaggiungimento, int ID);
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param descrizione Descrizione dell'obiettivo.
     * @param dataRaggiungimento Data entro cui raggiungere l'obiettivo.
     * @param valore Valore totale dell'obiettivo da raggiungere
     * @param unità Unità di misura dell'obiettivo
     * @param listaAzioni Lista di azioni collegate all'obiettivo.
     * @param ID Identificativo dell'obiettivo.
     */
    public void modificaObiettivoMisurabile(String nome, String descrizione, Date dataRaggiungimento, 
            int valore, String unità, Azione[] listaAzioni, int ID);
    
    /**
     * 
     * @param ID Identificativo dell'obiettivo.
     */
    public void eliminaObiettivo(int ID);
}
