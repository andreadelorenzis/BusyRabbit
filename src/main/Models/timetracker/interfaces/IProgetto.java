/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

import main.Models.timetracker.classes.Attività;

public interface IProgetto {
        
    public void aggiungiAttività(Attività attività);
    
    public void setNome(String nome);
     
    public void setColore(String colore);
    
    public String getId();
    
    public long getDurata();
}
