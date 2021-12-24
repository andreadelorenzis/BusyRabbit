/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.account.classes;

import java.io.File;

/**
 * Componenente che contiene i metodi per modificare alcuni dati dell'account.
 */
public class Impostazioni {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    // Istanza singola classe Impostazioni
    private static Impostazioni istanza = null;
    
    // Account dell'utente, passato alle impostazioni per permettere modifica dei dati 
    // ed eliminazione dell'account.
    private Account account;   
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    private Impostazioni() {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // ********************************* 
    
     /**
     * 
     * @return Istanza singleton della classe Impostazioni
     */
    public static Impostazioni getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new Impostazioni();
        }
        return istanza;
    };
    
    /**
     * Cambia la palette di colori principali dell'app.
     * 
     * @param chiaro Parametro booelano che rappresenta lo sfondo chiario se true, scuro se false.
     */
    public void impostaSfondo(Boolean chiaro) {
    };
    
    /**
     * 
     * @param nome Nome o username dell'utente.
     */
    public void cambiaNome(String nome) {
    };
    
    /**
     * Cambiare l'email dell'utente. Serve la password perchè è una modifica importante (l'email è usata per accedere).
     * 
     * @param email Email associata all'account.
     * @param password Password dell'account.
     */
    public void cambiaEmail(String email, String password) {
    };
    
    /**
     * 
     * @param vecchiaPass La password attuale dell'utente.
     * @param nuovaPass La nuova password che si vuole inserire.
     */
    public void cambiaPassword(String vecchiaPass, String nuovaPass) {
    };
    
    /**
     * 
     * @param password Password associata all'account.
     */
    public void eliminaAccount(String password) {
    };
    
    /**
     * Permette di importare i dati da un file CSV nel corretto formato.
     * 
     * @param file File CSV da importare.
     */
    public void importaFileCSV(File file) {
    };
   
}
