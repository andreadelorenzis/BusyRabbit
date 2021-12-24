/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.account.classes;

import main.Models.agenda.classes.Agenda;
import main.Models.dashboard.classes.Dashboard;
import main.Models.goalmanager.classes.GoalManager;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.timetracker.classes.TimeTracker;

/**
 * Punto di partenza dell'applicazione. 
 */
public class App {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    // Account utente ottenuto dal database in fase di Login/Registrazione.
    Account account;             
    
    // Componente che misura il tempo.
    TimeTracker timeTracker;     
    
    // Componente che monitora le abitudini.
    HabitTracker habitTracker;   
    
    // Componente che gestisce gli obiettivi.
    GoalManager goalManager;     
    
    // Componente che annota gli eventi futuri.
    Agenda agenda;               
    
    // Componente che visualizza i dati salvati in questo account.
    Dashboard dashboard;         
    
    // Componente per modificare delle impostazioni di questo account.
    Impostazioni impostazioni;   
        
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * Aggiunge un nuovo account nella cartella Database (se esistente, altrimenti la crea).
     * 
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @param confermaPass Conferma della password.
     * @return 
     */
    public Account registrati(String email, String password, String confermaPass) {
        return new Account("", email, password);
    };
    
    /**
     * Legge un account esistente dalla cartella Database usando email e password.
     * 
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Account che contiene la struttura dei dati dell'utente.
     */
    public Account accedi(String email, String password) {
        return new Account("", email, password);
    };
    
    /**
     * Esce dal Account attuale e torna all'inserimento delle credenziali.
     */
    public void faiLogout() {
    };
    
    /**
     * Mostra le abitudini/azioni/eventi del giorno prima non completati, chiedendo
     * all'utente quali ha effettivamente svolto.
     */
    public void visualizzaRecap() {
    };
    
}
