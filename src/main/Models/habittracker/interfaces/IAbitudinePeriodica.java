/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.habittracker.interfaces;

import java.util.Date;

/**
 * Abitudine da completare periodicamente.
 *
 * @author andre
 */
public interface IAbitudinePeriodica extends IAbitudine {
    
    /**
     * 
     * @param dataInizio giorno di partenza dell'abitudine
     */
    public void setDataInizio(Date dataInizio);
    
    /**
     * 
     * @param giorni giorni della settimana in cui comparirà l'abitudine
     */
    public void setGiorniRipetizione(int giorni[]);
    
    /**
     * Aggiunge una item alla lista (eventualmente vuota) di sotto-item da completare
     * 
     * @param nome nome del sotto-task
     */
    public void aggiungiTask(String nome);
    
    /**
     * Elimina uno specifico task.
     * 
     * @param ID ID del task da eliminare
     */    
    public void eliminaTask(int ID);
    
}
