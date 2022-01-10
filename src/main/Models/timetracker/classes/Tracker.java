/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import main.Models.timetracker.interfaces.ITracker;

// Classe estesa da PomodoroTimer e TimeTracker

public class Tracker implements ITracker {
    
    //  CAMPI
    private int lineNumber = 0;
    private String nextValue  = "";
    protected LinkedList listaAttività = new LinkedList<Attività>();
    protected LinkedList listaProgetti = new LinkedList<Progetto>();
    
    public Tracker(){
        inizializzaAttivitàDaFile();
        inizializzaProgettiDaFile();
    }

    //METODI PUBBLICI 
    
    @Override
    public void aggiungiAttività(String nome, LocalDate data, long durata, String progetto) {
        listaAttività.add(new Attività(nome, data, durata, progetto));
    }
  
    @Override
    public void modificaAttività(String nome, String progetto, String id) {
        /*int verifica = 0;
        for(Iterator<Attività> iter = listaAttività.iterator(); ((iter.hasNext() && verifica == 0));){
            Attività a = iter.next();
            if(a.getId() == id)  {
                verifica = 1;
                //c.setParametri(nome, progetto);                
            }             
        }*/
    }
    
    @Override
    public void eliminaAttività(String id) {
        int verifica = 0;
        for(Iterator<Attività> iter = listaAttività.iterator(); ((iter.hasNext() && verifica == 0));){
            Attività a = iter.next();
            if(a.getId().equals(id))  {
                verifica = 1;
                iter.remove();
            }             
        }
    }
    
    @Override
    public void aggiungiProgetto(String nome, String colore) {
        listaProgetti.add(new Progetto(nome, colore));
    }
    
    public void eliminaProgetto(String id){
        int verifica = 0;
        for(Iterator<Progetto> iter = listaProgetti.iterator(); ((iter.hasNext() && verifica == 0));){
            Progetto p = iter.next();
            if(p.getId() == id)  {
                verifica = 1;
                iter.remove();
            }             
        }
    }
    
    public LinkedList<Attività> getListaAttività(){
        return listaAttività;
    }  
    
    //METODI PRIVATI
    private void inizializzaAttivitàDaFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("FileAttività.txt").getFile());
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
                LocalDate data = LocalDate.parse(nextValue);

                long Value = input.nextLong();
                long durata = Value;

                nextValue = input.next().replace("\"", "");
                String progetto = nextValue;

                nextValue = input.next().replace("\"", "");
                int id = Integer.valueOf(nextValue);

                listaAttività.add(new Attività(nome, data, durata, progetto));
            }
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Linea numero '%s, prossimo valore: '%s''", lineNumber, nextValue), ex);
        }
    }
    
    private void inizializzaProgettiDaFile(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("FileProgetti.txt").getFile());
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
                String colore = nextValue;
                
                listaProgetti.add(new Progetto(nome, colore));
            }
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Line number '%s, nextValue '%s''", lineNumber, nextValue), ex);
        }
    }
}