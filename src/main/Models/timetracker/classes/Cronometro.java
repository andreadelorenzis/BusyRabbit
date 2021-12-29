/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Utilizzato da TimeTracker per cronometrare il tempo.
 */
public class Cronometro {
    
    //  CAMPI
        private long inizio;
        private long fine;
        private StringProperty secondi, minuti, ore = new SimpleStringProperty();
    

        
    //  METODI PRIVATI


    //  METODI PUBBLICI
    public void avvia() {
        inizio = System.currentTimeMillis();
    }    
     
    public void arresta() {
        fine = System.currentTimeMillis();
    }
 
    public long getTempo() {
        return fine - inizio;
    } 
    
    public void convertiTempo(long durata) {
        int secondi = (int)((((durata) % 3600000.0) % 60000.0)/ 1000.0);
        int minuti = (int)(((durata) % 3600000.0) / 60000.0);
        int ore = (int)((durata) / 3600000.0);
        
        this.secondi.setValue(""+secondi);
        this.minuti.setValue(""+minuti);
        this.ore.setValue(""+ore);      
    }
  
}

