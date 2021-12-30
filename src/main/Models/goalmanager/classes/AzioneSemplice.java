/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.goalmanager.classes;

import main.Models.habittracker.classes.Item;

/**
 * Azione da completare associata ad un obiettivo suddivisibile in sotto-azioni
 */
public class AzioneSemplice extends Azione {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private Item[] listaItem;   // Lista di sotto-task da completare
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'azione
     * @param giorni Giorni di ripetizione dell'azione
     * @param valore Valore di cui incrementare l'obiettivo a cui l'azione è associato
     */
    public AzioneSemplice(String nome, int[] giorni, int valore) {
        super(nome, giorni, valore);
    }
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // ********************************* 
    
    /**
     * 
     * @param nome Nome del sotto-item
     */
    public void aggiungiItem(String nome) {
    };
   
}
