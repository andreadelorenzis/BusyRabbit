/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.habittracker.classes;

import main.Models.habittracker.interfaces.IAbitudineSessione;
import main.Models.timetracker.classes.Timer;

/**
 * Rappresenta un abitudine da completare periodicamente nel tempo, che si 
 * differenzia per il fatto di doverla svolgere per un periodo prolungato di tempo 
 * per poterla completare con successo.
 */
public class AbitudineSessione extends Abitudine implements IAbitudineSessione {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private int durata;     // Durata dell'azione prima del completamento.
    
    private Timer timer;    // Timer da usare per tenere il tempo.

    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     * @param descrizione Descrizione dell'abitudine.
     */
    public AbitudineSessione(String nome, String descrizione) {
        super(nome, descrizione);
    }
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     */
    public AbitudineSessione(String nome) {
        super(nome);
    }
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************

    @Override
    public void setDurata(int durata) {
    }

    @Override
    public void avviaSessione() {
    }
    
}
