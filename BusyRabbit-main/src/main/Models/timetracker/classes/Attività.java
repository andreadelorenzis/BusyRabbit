/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Date;

/**
 * Rappresenta un'attività che è stata monitorata dall'utente.
 */
public class Attività {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private String nome;        // Nome dell'attività.
    
    private Date data;          // Data in cui è stata svolta l'attività.
    
    private int Durata;         // Durata dell'attività.
    
    private Progetto progetto;  // Progetto a cui l'attività è associato.
    
    private int ID;             // Identificativo dell'attività.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    public Attività(Date data, int durata, String nome, Progetto progetto) {};    
    
    public Attività(Date data, int durata, String nome) {};
    
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
    public void setNome(String nome) {};
    
    /**
     * 
     * @param durata Durata dell'attività.
     */
    public void setDurata(int durata) {};
    
    /**
     * 
     * @param progetto Progetto a cui l'attività è associato.
     */
    public void setProgettoPadre(Progetto progetto) {};
    
    public String getNome() {
        return "";
    };
    
}
