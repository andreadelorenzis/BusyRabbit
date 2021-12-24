/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.habittracker.classes;

import java.util.Date;
import main.Models.habittracker.interfaces.IAbitudinePeriodica;

/**
 * Abitudine da completare periodicamente.
 */
public class AbitudinePeriodica extends Abitudine implements IAbitudinePeriodica {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private Date dataInizio;            // data di partenza dell'abitudine
    
    private int[] giorniRipetizione;    // giorni della settimana in cui comparirà l'abitudine
    
    private Boolean completata;         // variabile booleana completamento abitudine
    
    private Item[] listaItem;           // contiene eventuali sotto-item da completare

    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    public AbitudinePeriodica(String nome, String descrizione) {
        super(nome, descrizione);
    }
    
    public AbitudinePeriodica(String nome) {
        super(nome);
    }

    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    @Override
    public void setDataInizio(Date dataInizio) {
    }

    @Override
    public void setGiorniRipetizione(int[] giorni) {
    }

    @Override
    public void setNome(String nome) {
    }

    @Override
    public void setDescrizione(String nome) {
    }

    @Override
    public void aggiungiTask(String nome) {
    }

    @Override
    public void eliminaTask(int ID) {
    }
    
}
