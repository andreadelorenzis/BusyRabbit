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

    //  CAMPI

    private static Impostazioni istanza = null;
    
    // Account dell'utente, passato alle impostazioni per permettere modifica dei dati 
    // ed eliminazione dell'account.
    private Account account;   

    //  COSTRUTTORI
    private Impostazioni() {
    };

    //  METODI PRIVATI

    //  METODI PUBBLICI

    public static Impostazioni getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new Impostazioni();
        }
        return istanza;
    };
    
    public void impostaSfondo(Boolean chiaro) {
    };

    public void cambiaNome(String nome) {
    };
    
    public void cambiaEmail(String email, String password) {
    };
    

    public void cambiaPassword(String vecchiaPass, String nuovaPass) {
    };

    public void eliminaAccount(String password) {
    };

    public void importaFileCSV(File file) {
    };
   
}
