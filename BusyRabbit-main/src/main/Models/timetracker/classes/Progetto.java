/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Map;

/**
 * Rappresenta l'entità a cui devono essere associate tutte le attività. Tali entità sono
 * quelle effettivamente visualizzate nella Dashboard.
 */
public class Progetto {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    // Nome del progetto.
    private String nome;            
    
    // Colore del progetto.
    private String colore;          
    
    // Struttura che contiene tutte le attività assegnate al progetto, divise per
    // anni, mesi e giorni. (Utile per dashboard)
    private Map strutturaAttività;
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome   // Nome del progetto.
     * @param colore // Colore del progetto.
     */
    public Progetto (String nome, String colore) {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * 
     * @param attività Attività da associare al progetto.
     */
    public void aggiungiAttività(Attività attività) {};
    
    /**
     * 
     * @param nome Nome del progetto.
     */
    public void setNome(String nome) {};
    
    /**
     * 
     * @param colore Colore del progetto
     */
    public void setColore(String colore) {};
    
}
