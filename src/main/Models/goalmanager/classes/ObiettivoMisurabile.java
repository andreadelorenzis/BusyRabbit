/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.goalmanager.classes;

import java.util.Date;

/**
 * Obiettivo quantitativo a cui sono associabili delle azioni periodiche che, se 
 * completate, ne garantiscono l'incremento. Estensione di ObiettivoSemplice.
 */
public class ObiettivoMisurabile extends ObiettivoSemplice {
    
    // *********************************
    //  CAMPI
    // *********************************
    
    private int valore;             // Valore totale dell'obiettivo da raggiungere
    
    private String unità;           // Unità Unità di misura dell'obiettivo
    
    private Azione[] listaAzioni;   //Lista di azioni collegate all'obiettivo.
    
    // *********************************
    //  COSTRUTTORI
    // *********************************

    /**
     * 
     * @param nome Nome dell'obiettivo.
     * @param descrizione Descrizione dell'obiettivo.
     * @param dataRaggiungimento  Data entro cui raggiungere l'obiettivo.
     */
    public ObiettivoMisurabile(String nome, String descrizione, Date dataRaggiungimento) {
        super(nome, descrizione, dataRaggiungimento);
    }
    
    /**
     * @param nome Nome dell'obiettivo.
     * @param dataRaggiungimento  Data entro cui raggiungere l'obiettivo.
     */
    public ObiettivoMisurabile(String nome, Date dataRaggiungimento) {
        super(nome, dataRaggiungimento);
    }
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************

    /**
     * 
     * @param valore Valore totale dell'obiettivo da raggiungere.
     */
    public void setValore(int valore) {
    }

    /**
     * 
     * @param unità Unità di misura dell'obiettivo.
     */
    public void setUnità(String unità) {
    }
    
    /**
     * 
     * @param nome Nome dell'azione.
     * @param giorni Giorni in cui l'azione si ripeterà.
     * @param valore Valore di incremento dell'obiettivo.
     */
    public void aggiungiAzione(String nome, int[] giorni, int valore) {};
    
    /**
     * 
     * @param nome Nome dell'azione.
     * @param giorni Giorni in cui l'azione si ripeterà.
     * @param valore Valore di incremento dell'obiettivo.
     * @param ID Identificativo dell'azione.
     */
    public void modificaAzione(String nome, int[] giorni, int valore, int ID) {};
    
    /**
     * 
     * @param ID Identificativo dell'azione.
     */
    public void eliminaAzione(int ID) {};
    
}
