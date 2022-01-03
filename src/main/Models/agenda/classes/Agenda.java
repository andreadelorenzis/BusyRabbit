/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.agenda.classes;

import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import main.Models.agenda.interfaces.IAgenda;

/**
 * Permette di annotare degli eventi futuri.
 */
public class Agenda implements IAgenda {

    //  CAMPI
    private static Agenda istanza = null;   // Istanza singletone della classe Agenda.
    private LinkedList listaEventi;         // Linked List di eventi
    
    int lineNumber = 0;                     // variabili per inizializzaEventiDaFile
    String nextValue  = "";
    
    //  COSTRUTTORI
    public static Agenda getInstance() {
    // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new Agenda();
        }
        return istanza;
    };
    private Agenda() {
        this.listaEventi = new LinkedList<Evento>();
        inizializzaEventiDaFile();
    };

   
    //  METODI PUBBLICI
    public void aggiungiEvento(String nome, String descrizione, LocalDate data, String obiettivo, int valore) {
        listaEventi.add(new Evento(nome, descrizione, data, obiettivo, valore));
    }

    public void modificaEvento(String nome, String descrizione, LocalDate data, String Obiettivo, int valore) {
        //ANDREA BELLO CI VUOI L'ITERATORE O NO?
    }

    public void eliminaEvento(String id) {
        int verifica = 0;
        for(Iterator<Evento> iter = listaEventi.iterator(); ((iter.hasNext() && verifica == 0));){
            Evento e = iter.next();
            if(e.getId().equals(id)) {
                verifica = 1;
                iter.remove();
            }             
        }
    }
    
     //  METODI PRIVATI
    private void inizializzaEventiDaFile() {
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
                String nome = nextValue;
                
                nextValue = input.next().replace("\"", "");
                String descrizione = nextValue;
                
                nextValue = input.next().replace("\"", "");
                LocalDate data = LocalDate.parse(nextValue);

                nextValue = input.next().replace("\"", "");
                String obiettivo = nextValue;

                nextValue = input.next().replace("\"", "");
                int valore = Integer.valueOf(nextValue);
                
                nextValue = input.next().replace("\"", "");
                int id = Integer.valueOf(nextValue);

                listaEventi.add(new Evento(nome, descrizione, data, obiettivo, valore, id));
            }
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Linea numero '%s, prossimo valore: '%s''", lineNumber, nextValue), ex);
        }
    }
}
