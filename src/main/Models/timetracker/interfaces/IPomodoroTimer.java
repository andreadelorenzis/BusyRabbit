/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

public interface IPomodoroTimer extends ITracker {
    
    public void setDurataSessione(int durata);

    public void setDurataPausaBreve(int durata);
  
    public void setDurataPausaLunga(int durata);
  
    public void setCicli(int nCicli);
  
    public void avviaPomodoroTimer();
  
    public void sospendiPomodoroTimer();
  
    public void resettaPomodoroTimer();
    
}
