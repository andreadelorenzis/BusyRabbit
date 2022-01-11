/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.goalmanager.interfaces;

import java.util.List;

/**
 * Obiettivo a cui è possibile assegnare dei sotto-obiettivi. Questi sotto-obiettivi possono essere sia altri 
 * obiettivi "scomponibili" e sia obiettivi "azione".
 */
public interface IObiettivoScomponibile extends IObiettivo {
    
    /**
     * Aggiunge un sotto-obiettivo al seguente obiettivo scomponibile. 
     * 
     * @param obiettivo 
     */
    public void aggiungiSottoObiettivo(IObiettivo obiettivo);
    
    /**
     * @return i sotto-obiettivi.
     */
    public List<IObiettivo> getSottoObiettivi();
    
    /**
     * 
     * @return l'obiettivo padre se presente, null altrimenti
     */
    public IObiettivo getObiettivoPadre();
    
    /**
     * 
     * @param idObiettivo identificativo del sotto-obiettivo da eliminare
     */
    public void eliminaSottoObiettivo(String idObiettivo);
    
}
