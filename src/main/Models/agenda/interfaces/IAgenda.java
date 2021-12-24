/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.agenda.interfaces;

import java.util.Date;
import main.Models.goalmanager.classes.ObiettivoMisurabile;

/**
 * Permette di annotare degli eventi futuri.
 */
public interface IAgenda {
    
    /**
     * 
     * @param nome Nome dell'evento.
     * @param descrizione Descrizione dell'evento.
     * @param data Data in cui avverrà l'evento.
     * @param obiettivo Obiettivo associato all'evento.
     * @param valore Valore dell'evento che incrementerò l'obiettivo associato.
     */
    public void aggiungiEvento(String nome, String descrizione, Date data,
            ObiettivoMisurabile obiettivo, int valore);
    
    /**
     * 
     * @param nome Nome dell'evento.
     * @param descrizione Descrizione dell'evento.
     * @param data Data in cui avverrà l'evento.
     * @param obiettivo Obiettivo associato all'evento.
     * @param valore Valore dell'evento che incrementerò l'obiettivo associato.
     * @param ID Identificativo dell'evento.
     */
    public void modificaEvento(String nome, String descrizione, Date data,
            ObiettivoMisurabile obiettivo, int valorem, int ID);
    
    /**
     * 
     * @param ID Identificativo dell'evento.
     */
    public void eliminaEvento(int ID);
    
}
