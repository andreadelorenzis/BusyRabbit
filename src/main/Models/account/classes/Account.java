/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.account.classes;

import java.util.Date;
import main.Models.agenda.classes.Evento;
import main.Models.goalmanager.classes.ObiettivoSemplice;
import main.Models.habittracker.classes.Abitudine;
import main.Models.timetracker.classes.Progetto;

/**
 * Entità che rappresenta l'account dell'utente e contiene tutti i dati.
 */
public class Account {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private String nome;                          // Nome o username utente.
        
    private String email;                         // Email dell'utente.
    
    private String password;                      // Password associata all'account.
    
    private Date data;                            // Data di creazione dell'account.
    
    private Boolean sfondoChiaro;                 // Theme chiaro/scuro
    
    private Progetto[] listaProgetti;             // Lista dei progetti creati.
    
    private ObiettivoSemplice[] listaObiettivi;   // Lista obiettivi correnti.
    
    private Abitudine[] listaAbitudini;           // Lista abitudini attive.
    
    private Evento[] listaEventi;                 // Lista eventi programmati.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'utente.
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     */
    public Account(String nome, String email, String password) {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // ********************************* 

    /**
     * 
     * @param nome Nome o username utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * 
     * @param email Email dell'utente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @param password Password associata all'account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @param sfondoChiaro Theme chiaro/scuro
     */
    public void setSfondoChiaro(Boolean sfondoChiaro) {
        this.sfondoChiaro = sfondoChiaro;
    }

    /**
     * 
     * @return Lista dei progetti creati.
     */
    public Progetto[] getListaProgetti() {
        return listaProgetti;
    }

    /**
     * 
     * @return Lista obiettivi correnti.
     */
    public ObiettivoSemplice[] getListaObiettivi() {
        return listaObiettivi;
    }

    /**
     * 
     * @return Lista abitudini attive.
     */
    public Abitudine[] getListaAbitudini() {
        return listaAbitudini;
    }

    /**
     * 
     * @return Lista eventi programmati.
     */
    public Evento[] getListaEventi() {
        return listaEventi;
    }
    
}
