/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.habittracker.classes;

import main.Models.habittracker.interfaces.IAbitudineContatore;

/**
 * Abitudine svolgibile quante volte si vuole che non si ripete periodicamente.
 */
public class AbitudineContatore extends Abitudine implements IAbitudineContatore {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private String tipo;    // permette di dire se l'abitudine è positiva (+), negativa (-) o entrambe (+ e -).

    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     * @param descrizione Descrizione dell'abitudine.
     */
    public AbitudineContatore(String nome, String descrizione) {
        super(nome, descrizione);
    }
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     */
    public AbitudineContatore(String nome) {
        super(nome);
    }
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************

    @Override
    public void setTipo(String tipo) {
    }
        
}
