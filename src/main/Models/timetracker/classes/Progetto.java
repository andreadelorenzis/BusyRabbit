/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.UUID;
import java.util.Iterator;
import java.util.LinkedList;


/* Rappresenta l'entità a cui devono essere associate tutte le attività. Tali entità sono
   quelle effettivamente visualizzate nella Dashboard.*/

public class Progetto {
    //  CAMPI
    LinkedList listaAttivitàProgetto = new LinkedList<Attività>();
    LinkedList listaAttività = new LinkedList<Attività>();
    Tracker tracker = new Tracker();
    
    private String nome;
    private String colore;          
    private String id;
    private long durata;

    //  COSTRUTTORI
    public Progetto (String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
        this.id = UUID.randomUUID().toString();
        
        for(Iterator<Attività> iter = tracker.getListaAttività().iterator(); (iter.hasNext());){
            Attività c = iter.next();
            if(c.getProgetto().equals(nome))  
                listaAttivitàProgetto.add(c);
        }
    };
    //  METODI PUBBLICI

    public void aggiungiAttività(Attività attività) {
        listaAttivitàProgetto.add(attività);
    };

    public void setNome(String nome) {
        this.nome = nome;
    };

    public void setColore(String colore) {
        this.colore = colore;
    };

    public String getId() {
        return this.id;
    }
    
    public long getDurata(){
        calcolaDurata();
        return this.durata;
    }
    
    //  METODI PRIVATI
    
    private void calcolaDurata(){
        for(Iterator<Attività> iter = this.listaAttivitàProgetto.iterator(); (iter.hasNext());){
            Attività c = iter.next();
            this.durata += c.getDurata();
        }
    }
}