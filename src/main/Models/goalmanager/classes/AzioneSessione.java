/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.goalmanager.classes;


/**
 * Azione associata ad un obiettivo che dura nel tempo e viene completata quando
 * passa un periodo prefissato di tempo dalla partenza del timer
 */
public class AzioneSessione extends Azione {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private int durata;     // Durata dell'azione prima del completamento.
    
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    public AzioneSessione(String nome, int[] giorni, int valore, int durata) {
        super(nome, giorni, valore);
    }
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
}
