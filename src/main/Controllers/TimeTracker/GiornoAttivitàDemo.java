/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.TimeTracker;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author andre
 */
public class GiornoAttivitàDemo {
    private Date giorno;
    private ArrayList<AttivitàDemo> listaAttività;
    private int tempoTotale;

    public Date getGiorno() {
        return giorno;
    }

    public ArrayList<AttivitàDemo> getListaAttività() {
        return listaAttività;
    }

    public int getTempoTotale() {
        return tempoTotale;
    }

    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }

    public void setListaAttività(ArrayList<AttivitàDemo> listaAttività) {
        this.listaAttività = listaAttività;
    }

    public void setTempoTotale(int tempoTotale) {
        this.tempoTotale = tempoTotale;
    }
    
    
}
