/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.dashboard.interfaces;

import main.Models.goalmanager.classes.ObiettivoSemplice;
import main.Models.habittracker.classes.Abitudine;
import main.Models.timetracker.classes.Progetto;

/**
 * Permette di visualizzare i dati raccolti nell'account dell'utente.
 */
public interface IDashboard {
    
    /**
     * Visualizza le informazioni relative al tempo monitorato suddiviso per progetti e periodi di tempo
     */
    public void visualizzaReportTimeTracking();
    
    /**
     * Visualizza un elenco di tutti gli obiettivi e sotto-obiettivi, mostrando il relativo progresso e i premi.
     */
    public void visualizzaReportObiettivi();
    
    /**
     * Visualizza le informazioni relative al numero di giorni in cui le abitudini sono state completate con successo.
     */
    public void visualizzaReportAbitudini();
    
}
