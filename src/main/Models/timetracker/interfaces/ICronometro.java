/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;


public interface ICronometro {
    
    public void avvia();
     
    public void arresta();
 
    public long getTempo();
    
    public void convertiTempo(long durata);    
}
