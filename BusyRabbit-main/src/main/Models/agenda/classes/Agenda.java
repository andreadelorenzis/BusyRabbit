/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.agenda.classes;

import java.util.Date;
import main.Models.agenda.interfaces.IAgenda;
import main.Models.goalmanager.classes.ObiettivoMisurabile;

/**
 * Permette di annotare degli eventi futuri.
 */
public class Agenda implements IAgenda {

    // *********************************
    //  CAMPI
    // *********************************
    
    private static Agenda istanza = null;      // Istanza singletone della classe Agenda.
    
    private Evento[] listaEventi;       // Lista degli eventi futuri programmati.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    private Agenda() {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
 
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
     /**
     * 
     * @return Istanza singleton della classe Agenda
     */
    public static Agenda getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new Agenda();
        }
        return istanza;
    };

    @Override
    public void aggiungiEvento(String nome, String descrizione, Date data, ObiettivoMisurabile obiettivo, int valore) {
    }

    @Override
    public void modificaEvento(String nome, String descrizione, Date data, ObiettivoMisurabile obiettivo, int valorem, int ID) {
    }

    @Override
    public void eliminaEvento(int ID) {
    }
    
}
