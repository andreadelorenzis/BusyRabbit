/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.goalmanager.classes;

import java.util.Date;

/**
 * Obiettivo qualitativo da completare a cui sono assegnabili dei sotto-obiettivi.
 *
 * @author andre
 */
public class ObiettivoSemplice {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private String nome;                                // Nome dell'obiettivo.
    
    private String descrizione;                         // Descrizione dell'obiettivo.
    
    private Date dataRaggiungimento;                    // Data entro cui raggiungere l'obiettivo.
    
    private Boolean completato;                         // Variabile booleana completamento obiettivo.
    
    private ObiettivoSemplice[] listaSottoObiettivi;    // Lista degli obiettivi figli di questo obiettivo.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param descrizione Descrizione dell'obiettivo.
     * @param dataRaggiungimento  Data entro cui raggiungere l'obiettivo.
     */
    public ObiettivoSemplice(String nome, String descrizione, Date dataRaggiungimento) {
    }
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param dataRaggiungimento  Data entro cui raggiungere l'obiettivo.
     */
    public ObiettivoSemplice(String nome, Date dataRaggiungimento) {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'obiettivo.
     */
    public void setNome(String nome) {};
    
    /**
     * 
     * @param descrizione Descrizione dell'obiettivo.
     */
    public void setDescrizione(String descrizione) {};
    
    /**
     * 
     * @param dataRaggiungimento Data entro cui raggiungere l'obiettivo.
     */
    public void setDataRaggiungimento(Date dataRaggiungimento) {};
    
    /**
     * 
     * @param completato  Variabile booleana completamento obiettivo.
     */
    public void setCompletato(Boolean completato) {};
    
    /**
     * 
     * @param nome Nome del sotto-obiettivo.
     * @param descrizione Descrizione del sotto-obiettivo.
     * @param dataRaggiungimento Data di raggiungimento del sotto-obiettivo.
     */
    public void aggiungiSottoObiettivo(String nome, String descrizione, Date dataRaggiungimento) {};
    
}
