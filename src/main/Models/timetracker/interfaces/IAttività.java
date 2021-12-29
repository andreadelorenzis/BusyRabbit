/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

import java.time.LocalDate;


public interface IAttività {
    
    public void setNome(String nome);
    
    public String getNome();
    
    public void setDurata(int durata);
    
    public long getDurata();
    
    public void setProgettoPadre(String progetto);
        
    public String getId();
    
    public LocalDate getData();
    
    public String getProgetto();
}
