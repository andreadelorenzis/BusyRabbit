/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import main.Models.timetracker.interfaces.ITimeTracker;

/**
 * Permette di cronometrare un'attività.
 */
public class TimeTracker extends Tracker implements ITimeTracker {
    
    //  CAMPI    
    Collection<Progetto> listaProgetti = new LinkedList<>();
    private static TimeTracker instance = null;
    Attività corrente;
    
    private Cronometro cronometro;
    
    // *********************************
    //  COSTRUTTORI
    // *********************************
    
    // Costruttore invisibile.
    private TimeTracker() {};
    
    // *********************************
    //  METODI PRIVATI
    // *********************************
    
    // *********************************
    //  METODI PUBBLICI
    // *********************************
    
    /**
     * 
     * @return Istanza singleton della classe TimeTracker
     */
    public static TimeTracker getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new TimeTracker();
        }
        return instance;
    };
    
    @Override
    public void avviaTimeTracker(Attività attività) {
        this.corrente = attività;
        cronometro.avvia();        
    }

    @Override
    public void arrestaTimeTracker(Attività attività) {
        cronometro.arresta();
        this.corrente.incDurata(cronometro.getTempo());
        listaAttività.add(this.corrente);
    }
    
    private void InizializzaDaFile(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("MyFile.txt").getFile());
            Scanner input = new Scanner(file)
                .useDelimiter(",|\\R")
                .useLocale(Locale.ITALIAN);

            // vai oltre la testa
            input.nextLine();

            while (input.hasNext()) {
                lineNumber++;
                nextValue = input.next().replace("\"", "");
                String nome =nextValue;

                nextValue = input.next().replace("\"", "");
                String colore = nextValue;
                
                listaProgetti.add(new Progetto(nome, colore));
            }
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Line number '%s, nextValue '%s''", lineNumber, nextValue), ex);
        }
    }
}
