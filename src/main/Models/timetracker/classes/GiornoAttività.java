/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;


import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author andre
 */
public class GiornoAttività {
    private Date data;
    private int tempoTotale;
    private LinkedList<Attività> listaAttività;

    public void setListaAttività(LinkedList<Attività> listaAttività) {
        this.listaAttività = listaAttività;
    }

    public Date getData() {
        return data;
    }

    public int getTempoTotale() {
        return tempoTotale;
    }

    public LinkedList<Attività> getListaAttività() {
        return listaAttività;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTempoTotale(int tempoTotale) {
        this.tempoTotale = tempoTotale;
    }
    
    public void aggiungiAttività(Attività attività) {
        this.listaAttività.add(attività);
    }
    
}
