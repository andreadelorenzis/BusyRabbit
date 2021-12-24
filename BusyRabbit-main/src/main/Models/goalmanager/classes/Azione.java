/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.goalmanager.classes;

/**
 * Azione periodica da associare ad un obiettivo misurabile, che ne determina l'incremento.
 */
public class Azione {

    // *********************************
    //  CAMPI
    // *********************************
    
    private String nome;    // Nome dell'azione
    
    private int[] giorni;   // Giorni di ripetizione dell'azione
    
    private int valore;     // Valore di cui incrementare l'obiettivo a cui l'azione è associato
    
    private Boolean completata;
    
    private int ID;         // Identificativo dell'azione
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'azione
     * @param giorni Giorni di ripetizione dell'azione
     * @param valore Valore di cui incrementare l'obiettivo a cui l'azione è associato
     */
    public Azione(String nome, int[] giorni, int valore) {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'azione
     */
    public void setNome(String nome) {};
    
    /**
     * 
     * @param giorni Giorni di ripetizione dell'azione
     */
    public void setGiorniRipetizione(int[] giorni) {};
    
    /**
     * 
     * @param valore Valore di cui incrementare l'obiettivo a cui l'azione è associato
     */
    public void setValore(int valore) {};
    
    /**
     * 
     * @param completata 
     */
    public void setCompletata(Boolean completata) {};
    
}
