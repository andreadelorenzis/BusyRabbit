/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

import java.time.LocalDate;
import java.util.LinkedList;
import main.Models.timetracker.classes.Attività;

/**
 *
 * @author Mars_DB
 */
public interface IGiornoAttività {
    
    public LocalDate getData();    

    public int getTempoTotale();

    public LinkedList<Attività> getListaAttività();

    public void setData(LocalDate data);

    public void setTempoTotale(int tempoTotale);
    
    public void aggiungiAttività(Attività attività);
}
