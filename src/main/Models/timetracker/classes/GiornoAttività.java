/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;



import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import main.Models.timetracker.interfaces.IGiornoAttività;

/**
 *
 * @author andre
 */
public class GiornoAttività implements IGiornoAttività{
    private LocalDate data = LocalDate.now();
    private int tempoTotale;
    private LinkedList<Attività> listaAttivitàDelGiorno;
    private Tracker t;
    
    public GiornoAttività(){
        for(Iterator<Attività> iter = t.getListaAttività().iterator(); (iter.hasNext());){
            Attività a = iter.next();
            if(a.getData().equals(this.data))  
                this.listaAttivitàDelGiorno.add(a);
        }
    }

    public LocalDate getData() {
        return data;
    }

    public int getTempoTotale() {
        return tempoTotale;
    }

    public LinkedList<Attività> getListaAttività() {
        return this.listaAttivitàDelGiorno;
    }

    public void setData(LocalDate data) {
        this.data = data;
        setListaAttivitàDelGiorno();
    }

    public void setTempoTotale(int tempoTotale) {
        this.tempoTotale = tempoTotale;
    }
    
    public void aggiungiAttività(Attività attività) {
        this.listaAttivitàDelGiorno.add(attività);
    }
    
    private void setListaAttivitàDelGiorno(){
        this.listaAttivitàDelGiorno = null;
        for(Iterator<Attività> iter = t.getListaAttività().iterator(); (iter.hasNext());){
            Attività a = iter.next();
            if(a.getData().equals(this.data))  
                this.listaAttivitàDelGiorno.add(a);
        }
    }
    
}
