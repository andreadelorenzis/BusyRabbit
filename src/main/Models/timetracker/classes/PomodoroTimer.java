/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import main.Models.timetracker.interfaces.IPomodoroTimer;

 //Permette di monitorare un'attività con delle sessioni, intervallate da periodi di pausa.

public class PomodoroTimer extends Tracker implements IPomodoroTimer {
    //  CAMPI

    private int durataSessione;     // Durata della sessione di lavoro.
    
    private int durataPausaBreve;   // Durata della pausa breve.
    
    private int durataPausaLunga;   // Durata della pausa lunga.
    
    private int nCicli;            // Periodo dopo il quale comincia una pausa lunga.
    
   // private Timer timer;            // Timer semplice usato dal pomodoro timer
   
    //  METODI PUBBLICI
    @Override
    public void setDurataSessione(int durata){
        this.durataSessione = durata;
    }
    @Override
    public void setDurataPausaBreve(int durata){
        this.durataPausaBreve = durata;
    }
  
    public void setDurataPausaLunga(int durata){
        this.durataPausaLunga = durata;
    }
  
    public void setCicli(int nCicli){
        this.nCicli = nCicli;
    }
  
    public void avviaPomodoroTimer(){
        
    }
  
    public void sospendiPomodoroTimer(){
        
    }
  
    public void resettaPomodoroTimer(){
        
    }
   
}
