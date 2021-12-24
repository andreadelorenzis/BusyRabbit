/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Date;
import main.Models.timetracker.interfaces.ITimeTracker;

/**
 * Permette di cronometrare un'attività.
 */
public class TimeTracker implements ITimeTracker {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private static TimeTracker instance = null;
    
    private Cronometro cronometro;
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    // Costruttore invisibile.
    private TimeTracker() {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * 
     * @return Istanza singleton della classe TimeTracker
     */
    public static TimeTracker getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new TimeTracker();
        }
        return instance;
    };
    
    @Override
    public void avviaTimeTracker() {
    }

    @Override
    public void arrestaTimeTracker() {
    }

    @Override
    public void aggiungiAttività(String nome, Progetto progetto, Date data, long durata) {
    }

    @Override
    public void modificaAttività(String nome, Progetto progetto, Date data, long durata, int ID) {
    }

    @Override
    public void eliminaAttività(int ID) {
    }

    @Override
    public void aggiungiProgetto(String nome, String colore) {
    }

    @Override
    public void modificaProgetto(String nome, String progetto) {
    }

    @Override
    public void eliminaProgetto(String nome) {
    }
    
}
