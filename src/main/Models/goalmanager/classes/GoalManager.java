/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.goalmanager.classes;

import java.util.Date;
import main.Models.goalmanager.interfaces.IGoalManager;

/**
 * Gestore degli obiettivi
 */
public class GoalManager implements IGoalManager {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private static GoalManager istanza = null;           // Istanza singletone classe GoalManager
    
    private ObiettivoSemplice[] listaObiettivi;   // Lista degli obiettiv aggiunti al goal manager.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    private GoalManager() {
    };
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
     /**
     * 
     * @return Istanza singleton della classe GoalManager
     */
    public static GoalManager getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new GoalManager();
        }
        return istanza;
    };

    @Override
    public void aggiungiObiettivoSemplice(String nome, String descrizione, Date dataRaggiungimento) {
    }

    @Override
    public void aggiungiObiettivoMisurabile(String nome, String descrizione, Date dataRaggiungimento, 
            int valore, String unità, Azione[] listaAzioni) {
    }

    @Override
    public void modificaObiettivoSemplice(String nome, String descrizione, Date dataRaggiungimento, int ID) {
    }

    @Override
    public void modificaObiettivoMisurabile(String nome, String descrizione, Date dataRaggiungimento, 
            int valore, String unità, Azione[] listaAzioni, int ID) {
    }

    @Override
    public void eliminaObiettivo(int ID) {
    }
    
}
