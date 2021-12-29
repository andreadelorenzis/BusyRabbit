/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.Date;
import main.Models.timetracker.interfaces.IPomodoroTimer;

/**
 * Permette di monitorare un'attività con delle sessioni, intervallate da periodi di pausa.
 */
public class PomodoroTimer extends Tracker implements IPomodoroTimer {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private int durataSessione;     // Durata della sessione di lavoro.
    
    private int durataPausaBreve;   // Durata della pausa breve.
    
    private int durataPausaLunga;   // Durata della pausa lunga.
    
    private int periodo;            // Periodo dopo il quale comincia una pausa lunga.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************

    @Override
    public void setDurataSessione(int durata) {
    }

    @Override
    public void setDurataPausaBreve(int durata) {
    }

    @Override
    public void setDurataPausaLunga(int durata) {
    }

    @Override
    public void avviaPomodoroTimer() {
    }

    @Override
    public void sospendiPomodoroTimer() {
    }

    @Override
    public void resettaPomodoroTimer() {
    }

    @Override
    public void aggiungiAttività(String nome, Progetto progetto, Date data, long durata) {
    }

    @Override
    public void modificaAttività(String nome, Progetto progetto, Date data, long durata, int ID) {
    }

    @Override
    public void eliminaAttività(int ID) {
    }

    @Override
    public void aggiungiProgetto(String nome, String colore) {
    }

    @Override
    public void modificaProgetto(String nome, String progetto) {
    }

    @Override
    public void eliminaProgetto(String nome) {
    }

    @Override
    public void setPeriodo(int periodo) {
    }
}
