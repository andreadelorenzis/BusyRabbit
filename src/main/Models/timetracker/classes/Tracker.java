/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import main.Models.timetracker.interfaces.ITracker;

/**
 * Classe astratta estesa da PomodoroTimer e TimeTracker
 */
public class Tracker implements ITracker {
    //  CAMPI

    int lineNumber = 0;
    String nextValue  = "";
    LinkedList listaAttività = new LinkedList<>();
    
    public Tracker(){
        InizializzaDaFile();        
    }

    //  METODI PUBBLICI 
    @Override
    public void aggiungiAttività(String nome, LocalDate data, long durata, String progetto) {
        listaAttività.add(new Attività(nome, data, durata, progetto));
    }
  
    @Override
    public void modificaAttività(String nome, String progetto, String id) {
        int verifica = 0;
        for(Iterator<Attività> iter = listaAttività.iterator(); ((iter.hasNext() && verifica == 0));){
            Attività c = iter.next();
            if(c.getId() == id)  {
                verifica = 1;
                //c.setParametri(nome, progetto);                
            }             
        }
    }
    
    public LinkedList<Attività> getListaAttività(){
        return listaAttività;
    }
    
    @Override
    public void eliminaAttività(String id) {
        int verifica = 0;
        for(Iterator<Attività> iter = listaAttività.iterator(); ((iter.hasNext() && verifica == 0));){
            Attività c = iter.next();
            if(c.getId() == id)  {
                verifica = 1;
                iter.remove();
            }             
        }
    }
    
    @Override
    public void aggiungiProgetto(String nome, String colore) {
        
    }
 
    @Override
    public void modificaProgetto(String nome, String progetto){
        
    }
    
    private void InizializzaDaFile() {
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
            throw new RuntimeException(String.format("Line number '%s, nextValue '%s''", lineNumber, nextValue), ex);
        }
    }
}
