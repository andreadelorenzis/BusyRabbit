/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Date;
import java.util.UUID;

/**
 * Rappresenta un'attività che è stata monitorata dall'utente.
 */
public class Attività {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private String nome;        // Nome dell'attività.
    
    private Date data;          // Data in cui è stata svolta l'attività.
    
    private int durata;         // Durata dell'attività.
    
    private Progetto progetto;  // Progetto a cui l'attività è associato.
    
    private String ID;             // Identificativo dell'attività.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    public Attività(Date data, int durata, String nome, Progetto progetto) {
        this.data = data;
        this.nome = nome;
        this.progetto = progetto;
        this.ID = UUID.randomUUID().toString();
    };    
    
    public Attività(Date data, int durata, String nome) {
        this.data = data;
        this.durata = durata;
        this.nome = nome;
        this.progetto = null;
        this.ID = UUID.randomUUID().toString();
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'attività.
     */
    public void setNome(String nome) {
        this.nome = nome;
    };
    
    /**
     * 
     * @param durata Durata dell'attività.
     */
    public void setDurata(int durata) {
        this.durata = durata;
    };
    
    /**
     * 
     * @param progetto Progetto a cui l'attività è associato.
     */
    public void setProgettoPadre(Progetto progetto) {
        this.progetto = progetto;
    };
    
    public String getNome() {
        return this.nome;
    };
    
    public String getID() {
        return this.ID;
    }

    public Date getData() {
        return data;
    }

    public int getDurata() {
        return durata;
    }

    public Progetto getProgetto() {
        return progetto;
    }
    
    
    
}
