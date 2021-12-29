/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.time.LocalDate;
import java.util.Date;
import main.Models.timetracker.interfaces.ITimeTracker;


 //Permette di cronometrare un'attività.

public class TimeTracker extends Tracker implements ITimeTracker {

    //  CAMPI
    
    private static TimeTracker instance = null;
    
    private Cronometro cronometro;

    //  COSTRUTTORI
    
    // Costruttore invisibile.
    private TimeTracker() {};

    //  METODI PRIVATI

    //  METODI PUBBLICI
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
   
}
