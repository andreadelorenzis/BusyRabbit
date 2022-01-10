/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

// Classe che estende thread per la creazione di un timer

import main.Models.timetracker.interfaces.ITimer;

// utilizzato dal PomodoroTimer

public class Timer extends Thread implements ITimer {
    private int     secondi;
    private int     minuti;
    private boolean reset;

    
    public Timer(int secondi){
        this.secondi = secondi;
    }
    
    @Override
    public void run(){
        int pSecondi = this.secondi;
        int pMinuti;
        for(int i = 0; i < secondi && this.reset == false; i++){
            try {
                Timer.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            pSecondi--;
            pMinuti = pSecondi/60;
            System.out.println("Mancano ancora: "+ pMinuti +" minuti e "+ pSecondi +" secondi");
        }       
    } 
    
    public void setReset(boolean reset){
        this.reset = reset;
    }
    
    public boolean getReset(){
        return this.reset;
    }
}
