/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

public interface IPomodoroTimer extends ITracker {
    
    /**
     * 
     * @param durata Durata del periodo di lavoro.
     */
    public void setDurataSessione(int durata);
    
    /**
     * 
     * @param durata Durata della pausa breve.
     */
    public void setDurataPausaBreve(int durata);
    
    /**
     * 
     * @param durata Durata della pausa lunga.
     */
    public void setDurataPausaLunga(int durata);
    
    /**
     * 
     * @param periodo Periodo dopo il quale avviene una pausa lunga.
     */
    public void setPeriodo(int periodo);
    
    /**
     * Avvia il ciclo del Pomodoro Timer
     */
    public void avviaPomodoroTimer();
    
    /**
     * Metti in pausa il ciclo del Pomodoro Timer.
     */
    public void sospendiPomodoroTimer();
    
    /**
     * Termina il ciclo del Pomodoro Timer.
     */
    public void resettaPomodoroTimer();
    
}
