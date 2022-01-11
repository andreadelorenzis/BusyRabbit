/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.goalmanager.interfaces;

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
     * @param idAzione identificativo dell'azione da eliminare
     */
    public void eliminaAzione(String idAzione);
}
