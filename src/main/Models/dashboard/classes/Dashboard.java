/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.dashboard.classes;

import java.util.Map;
import javafx.util.Pair;
import main.Models.dashboard.interfaces.IDashboard;
import main.Models.goalmanager.classes.ObiettivoSemplice;
import main.Models.habittracker.classes.Abitudine;
import main.Models.timetracker.classes.Progetto;

/**
 * Permette di visualizzare i dati raccolti nell'account dell'utente.
 */
public class Dashboard implements IDashboard {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    // Itanza singletone classe Dashboard
    private static Dashboard istanza = null;
    
    // Lista di abitudini esistenti.
    private Abitudine[] listaAbitudini;                         
    
    // Lista di progetti esistenti.
    private Progetto[] listaProgetti;                           
    
    // Lista di obiettivi esistenti.
    private ObiettivoSemplice[] listaObiettivi;           
    
    // *********************************
    //  COSTRUTTORE
    // *********************************
    
    /**
     * 
     * @param listaAbitudini Lista di abitudini esistenti.
     * @param listaProgetti Lista di progetti esistenti.
     * @param listaObiettivi Lista di obiettivi esistenti.
     */
    private void Dashboard(Abitudine[] listaAbitudini, Progetto[] listaProgetti, ObiettivoSemplice[] listaObiettivi) {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************
 
     /**
     * 
     * @return Istanza singleton della classe Dashboard
     */
    public static Dashboard getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new Dashboard();
        }
        return istanza;
    };

    @Override
    public void visualizzaReportTimeTracking() {
    }

    @Override
    public void visualizzaReportObiettivi() {
    }

    @Override
    public void visualizzaReportAbitudini() {
    }
    
}
