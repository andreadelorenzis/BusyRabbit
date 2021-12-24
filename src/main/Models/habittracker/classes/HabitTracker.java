/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.habittracker.classes;

import java.util.Date;
import main.Models.habittracker.interfaces.IHabitTracker;

public class HabitTracker implements IHabitTracker {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private static HabitTracker istanza = null;        // Istanza singleton della classe HabitTracker.
    
    private Abitudine[] listaAbitudini;         // Lista di tutte le abitudini create.
    
    private Abitudine[] listaAbitudiniOdierne;  // Lista delle abitudini da completare oggi.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    private HabitTracker() {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
     /**
     * 
     * @return Istanza singleton della classe HabitTracker
     */
    public static HabitTracker getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new HabitTracker();
        }
        return istanza;
    };

    @Override
    public void aggiungiAbitudinePeriodica(String nome, Date dataInizio, int[] giorni) {
    }

    @Override
    public void aggiungiAbitudineContatore(String nome, Boolean tipo) {
    }

    @Override
    public void modificaAbitudinePeriodica(String nome, Date data, int[] giorni, int ID) {
    }

    @Override
    public void modificaAbitudineContatore(String nome, Boolean tipo, int ID) {
    }

    @Override
    public void eliminaAbitudine(int ID) {
    }
    
}
