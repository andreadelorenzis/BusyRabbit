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
    private LinkedList listaAttivitàProgetto = new LinkedList<Attività>();
    private Tracker t = new Tracker();
    
    private String nome;
    private String colore;          
    private String id;
    private long durata;

    //  COSTRUTTORI
    public Progetto (String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
        this.id = UUID.randomUUID().toString();
        
        for(Iterator<Attività> iter = t.getListaAttività().iterator(); (iter.hasNext());){
            Attività a = iter.next();
            if(a.getProgetto().equals(nome))  
                this.listaAttivitàProgetto.add(a);
        }
    };
    //  METODI PUBBLICI

    public void aggiungiAttività(Attività attività) {
        this.listaAttivitàProgetto.add(attività);
    }
    
    public void rimuoviAttività(Attività attività){
        for(Iterator<Attività> iter = listaAttivitàProgetto.iterator(); (iter.hasNext());){
            Attività a = iter.next();
            if(a.getId().equals(attività.getId()))  
                this.listaAttivitàProgetto.remove(a);
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getId() {
        return this.id;
    }
    
    public long getDurata(){
        this.durata = 0;
        calcolaDurata();
        return this.durata;
    }
    
    //  METODI PRIVATI
    
    private void calcolaDurata(){
        for(Iterator<Attività> iter = this.listaAttivitàProgetto.iterator(); (iter.hasNext());){
            Attività a = iter.next();
            this.durata += a.getDurata();
        }
    }
}