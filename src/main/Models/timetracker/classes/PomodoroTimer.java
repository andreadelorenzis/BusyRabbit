/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import main.Models.timetracker.interfaces.IPomodoroTimer;

 // Permette di monitorare un'attività con la tecnica di studio del pomodoro.

public class PomodoroTimer extends Tracker implements IPomodoroTimer {
    //  CAMPI
    private static PomodoroTimer instance = null;
    
    private int durataSessione;     // Durata della sessione di lavoro  
    private int durataPausaBreve;   // Durata della pausa breve
    private int durataPausaLunga;   // Durata della pausa lunga
    private int nCicli;             // Periodo dopo il quale comincia una pausa lunga
    private boolean reset;          // Variabile di lavoro per fermare il timer
    
    private Timer sessione     = new Timer(durataSessione);
    private Timer pausaBreve   = new Timer(durataPausaBreve);
    private Timer pausaLunga   = new Timer(durataPausaLunga);
  
    // COSTRUTTORE PRIVATO.
    private PomodoroTimer() {
    };

    //  METODI PUBBLICI
    public static PomodoroTimer getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new PomodoroTimer();
        }
        return instance;
    };
    
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
  
    public void avviaPomodoroTimer(Attività a){
        this.reset = false;
        for(int i = 0; i < nCicli && this.reset == false; i++){
            sessione.run();
            System.out.println("Fine esecuzione del thread sessione!");
            if(i < nCicli - 1){
                pausaBreve.run();
                System.out.println("Fine esecuzione del thread pausa breve!");
            }        
            else{
                pausaLunga.run();
                System.out.println("Fine esecuzione del thread pausa lunga!");
            }
            this.reset = sessione.getReset();
        }
        if(this.reset == false)
            a.incDurata(nCicli * durataSessione * 1000);        
    }
    
    @Override
    public void resettaPomodoroTimer(){
        this.sessione.setReset(true);
        this.pausaBreve.setReset(true);
        this.pausaLunga.setReset(true);
    }   
}
