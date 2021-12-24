/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

import java.util.Date;
import main.Models.timetracker.classes.Progetto;

public interface ITracker {
    
    /**
     * 
     * @param nome Nome dell'attività.
     * @param progetto Progetto di cui l'attività fa parte.
     * @param data Data in cui si è iniziato a monitorare l'attività.
     * @param durata Durata dell'attività.
     */
    public void aggiungiAttività(String nome, Progetto progetto, Date data, long durata);
    
    /**
     * 
     * @param nome Nome dell'attività.
     * @param progetto Progetto di cui l'attività fa parte.
     * @param data Data in cui si è iniziato a monitorare l'attività.
     * @param durata Durata dell'attività.
     * @param ID Identificativo dell'attività.
     */
    public void modificaAttività(String nome, Progetto progetto, Date data, long durata, int ID);
    
    /**
     * 
     * @param ID Identificativo dell'attività.
     */
    public void eliminaAttività(int ID);
    
    /**
     * 
     * @param nome Nome del progetto.
     * @param colore Colore del progetto.
     */
    public void aggiungiProgetto(String nome, String colore);
    
    /**
     * 
     * @param nome Nome del progetto da modificare.
     * @param colore Colore del progetto.
     */
    public void modificaProgetto(String nome, String progetto);
    
    /**
     * 
     * @param nome Nome del progetto da eliminare. 
     */
    public void eliminaProgetto(String nome);
    
}
