/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Date;
import main.Models.timetracker.interfaces.ITracker;

/**
 * Classe padre di TimeTracker e PomodoroTimer, contiene elementi comuni ad entrambi.
 */
public class Tracker implements ITracker {

    // *********************************
    //  CAMPI
    // *********************************
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    public Tracker() {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    public void aggiungiAttività(String nome, Progetto progetto, Date data, long durata) {};
    
    public void modificaAttività(String nome, Progetto progetto, Date data, long durata, int ID) {};
    
    @Override
    public void eliminaAttività(int ID) {};
    
    @Override
    public void aggiungiProgetto(String nome, String colore) {};
    
    @Override
    public void modificaProgetto(String nome, String progetto) {};

    @Override
    public void eliminaProgetto(String nome) {
    }
    
}
