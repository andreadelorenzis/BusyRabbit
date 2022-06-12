/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.model.goalmanager.interfacce;

import java.time.LocalDate;
import java.util.List;

/**
 * Obiettivo a cui è possibile collegare azioni periodiche che, se completate, fanno incrementare il progresso 
 * dell'obiettivo. Le azioni si ripetono nei giorni della settimana scelti. Il totale delle azioni non per forza deve
 * portare al completamento dell'obiettivo.
 */
public interface IObiettivoAzione extends IObiettivo {
    
    /**
     * Collega un'azione all'obiettivo.
     * 
     * @param azione l'azione da collegare
     */
    public void collegaAzione(IAzione azione);
    
    /**
     * 
     * @return la lista di azioni collegate a questo obiettivo
     */
    public List<IAzione> getAzioni();
    
    /**
     * 
     * @return le azioni da completare alla data specificata
     */
    public List<IAzione> getAzioniGiornaliere(LocalDate data);
    
    /**
     * 
     * @param idAzione identificativo dell'azione da eliminare
     */
    public void eliminaAzione(String idAzione);
    
    /**
     * 
     * @return valore attuale
     */
    public int getValoreAttuale();
    
    /**
     * 
     * @return valore totale
     */
    public int getValoreTotale();
    
    /**
     * 
     * @return l'unità di misura dell'obiettivo
     */
    public String getUnit�();

    /**
     * 
     * @param unità l'unità di misura dell'obiettivo
     */
    public void setUnita(String unit�);
    
    /**
     * 
     * @param valore valore attuale
     */
    public void setValoreAttuale(int valore);
    
    /***
     * 
     * @param valore valore totale
     */
    public void setValoreTotale(int valore);
    
}
