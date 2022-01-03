/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import main.Models.timetracker.interfaces.ITimeTracker;

// Permette di cronometrare un'attività.

public class TimeTracker extends Tracker implements ITimeTracker {
    
    //  CAMPI    
    private static TimeTracker instanzaTimeTracker = null;
    Attività corrente;
    private Cronometro cronometro;
    
    // COSTRUTTORI
    public static TimeTracker getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instanzaTimeTracker == null) {
            instanzaTimeTracker = new TimeTracker();
        }
        return instanzaTimeTracker;
    };
        
    private TimeTracker() {
    }
        
    //  METODI PUBBLICI

    @Override
    public void avviaTimeTracker(Attività attività) {
        this.corrente = attività;
        cronometro.avvia();        
    }

    @Override
    public void arrestaTimeTracker(Attività attività) {
        cronometro.arresta();
        this.corrente.incDurata(cronometro.getTempo());
        listaAttività.add(this.corrente);
    }   
}
