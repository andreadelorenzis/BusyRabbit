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

    //  CAMPI
    
    private String nome;                          // Nome o username utente.        
    private String email;                         // Email dell'utente.    
    private String password;                      // Password associata all'account.    
    private Date data;                            // Data di creazione dell'account.    
    private Boolean sfondoChiaro;                 // Theme chiaro/scuro    

    //  COSTRUTTORI

    public Account(String nome, String email, String password) {};

    //  METODI PRIVATI

    //  METODI PUBBLICI

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSfondoChiaro(Boolean sfondoChiaro) {
        this.sfondoChiaro = sfondoChiaro;
    }
    
}
