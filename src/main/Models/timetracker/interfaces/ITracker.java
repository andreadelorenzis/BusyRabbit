/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

import java.time.LocalDate;
import main.Models.timetracker.classes.Progetto;

public interface ITracker {
    
    public void aggiungiAttività(String nome, LocalDate data, long durata, String progetto);
    
    public void modificaAttività(String nome, String progetto, String id);
    
    public void eliminaAttività(String id);
  
    public void aggiungiProgetto(String nome, String colore);  
    
    
}
