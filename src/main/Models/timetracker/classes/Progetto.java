/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Map;
import java.util.UUID;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Rappresenta l'entità a cui devono essere associate tutte le attività. Tali entità sono
 * quelle effettivamente visualizzate nella Dashboard.
 */
public class Progetto {
    
    // *********************************
    //  CAMPI
    Collection<Attività> listaAttivitàProgetto = new LinkedList<>();
    Collection<Attività> listaAttività = new LinkedList<>();
    Tracker tracker = new Tracker();
    
    // Nome del progetto.
    private String nome;            
    
    // Colore del progetto.
    private String colore;          
    
    private String ID;
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome   // Nome del progetto.
     * @param colore // Colore del progetto.
     */
    public Progetto (String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
        this.ID = UUID.randomUUID().toString();
        
        for(Iterator<Attività> iter = tracker.getListaAttività().iterator(); (iter.hasNext());){
            Attività c = iter.next();
            if(c.getProgetto().equals(nome))  
                listaAttivitàProgetto.add(c);
        }
    };
    
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

    public String getID() {
        return this.ID;
    }
    
}
