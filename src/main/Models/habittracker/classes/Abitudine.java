/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.habittracker.classes;

import java.util.Date;

/**
 * Classe padre di tutte le abitudini
 * 
 * @author andre
 */
public class Abitudine {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    // Nome dell'abitudine.
    private String nome;                       
    
    // Descrizione dell'abitudine.
    private String descrizione;               
    
    // Valore che rappresenta il numero di volte in cui l'abitudine è stata completata consecutivamente.
    private int countAttuale;     
    
    // Conteggio di completamento consecutivo che si vuole raggiungere.
    private int countObiettivo;                 
    
    // Conteggio di completamento consecutivo record.
    private int countRecord;                   
                  
    // lista dei giorni in cui l'abitudine è stata completata con successo (necessario per dashboard)
    private Date[] listaGiorniCompletamento;
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     * @param descrizione Descrizione dell'abitudine.
     */
    public Abitudine(String nome, String descrizione) {};
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     */
    public Abitudine(String nome) {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * Modifica il nome dell'abitudine
     * 
     * @param nome nome dell'abitudine
     */
    public void setNome(String nome) {
    }
    
    /**
     * Modifica la descrizione dell'abitudine
     * 
     * @param descrizione descrizione dell'abitudine
     */
    public void setDescrizione(String descrizione) {
    }
    
    /**
     * Modifica il conteggio abitudine corrente
     * 
     * @param count conteggio attuale
     */
    public void setCountAttuale(int count) {
    }
    
    /**
     * Modifica il conteggio abitudine obiettivo
     * 
     * @param count conteggio obiettivo da raggiungere
     */
    public void setCountObiettivo(int count) {
    }
    
    /**
     * Modifica il conteggio abitudine record
     * 
     * @param count conteggio massimo raggiunto per l'abitudine
     */
    public void setCountRecord(int count) {
    }
    
    /**
     * Aggiunge un giorno alla cronologia dei giorni di completamento dell'attività
     * 
     * @param data data del giorno di completamento
     */
    public void aggiungiGiornoCompletamento(Date data) {
    }
    
}
