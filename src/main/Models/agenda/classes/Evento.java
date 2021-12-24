/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.agenda.classes;

import java.util.Date;
import main.Models.goalmanager.classes.ObiettivoMisurabile;

/**
 * Entità che rappresenta l'evento futuro.
 */
public class Evento {
        
    // *********************************
    //  CAMPI
    // *********************************
    
    private String nome;                    // Nome dell'evento.
    
    private String descrizione;             // Descrizione dell'evento.
    
    private Date data;                      // Data in cui avverrà l'evento.
    
    private ObiettivoMisurabile obiettivo;  // Obiettivo associato all'evento.
    
    private int valore;                     // Valore dell'evento che incrementerò l'obiettivo associato.
    
    private Boolean completato;             // Variabile booleana che rappresenta il completamento.
   
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'evento.
     * @param descrizione Date dell'evento.
     * @param data Data in cui avverrà l'evento.
     * @param Obiettivo Obiettivo associato all'evento.
     * @param valore Valore dell'evento che incrementerò l'obiettivo associato.
     */
    public Evento(String nome, String descrizione, Date data, ObiettivoMisurabile Obiettivo, int valore) {};
    
    /**
     * 
     * @param nome Nome dell'evento.
     * @param data Data in cui avverrà l'evento.
     * @param Obiettivo Obiettivo associato all'evento.
     * @param valore Valore dell'evento che incrementerò l'obiettivo associato. 
     */
    public Evento(String nome, Date data, ObiettivoMisurabile Obiettivo, int valore) {};
    
    /**
     * 
     * @param nome Nome dell'evento.
     * @param data Data in cui avverrà l'evento.
     */
    public Evento(String nome, Date data) {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************

    /**
     * 
     * @param nome Nome dell'evento.
     */
    public void setNome(String nome) {
    };
    
    /**
     * 
     * @param descrizione Descrizione dell'evento.
     */
    public void setDescrizione(String descrizione) {
    };

    /**
     * 
     * @param data Data in cui avverrà l'evento.
     */
    public void setData(Date data) {
    }

    /**
     * 
     * @param obiettivo Obiettivo associato all'evento.
     */
    public void setObiettivo(ObiettivoMisurabile obiettivo) {
    }

    /**
     * 
     * @param valore Valore di incremento dell'obiettivo una volta che l'evento sarà completato.
     */
    public void setValore(int valore) {
    }

    /**
     * 
     * @param completato Variabile booleana di completamento.
     */
    public void setCompletato(Boolean completato) {
    }
    
}
