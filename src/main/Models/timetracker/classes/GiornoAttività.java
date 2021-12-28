/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author andre
 */
public class GiornoAttività {
    private Date data;
    private int tempoTotale;
    private ArrayList<Attività> listaAttività;

    public void setListaAttività(ArrayList<Attività> listaAttività) {
        this.listaAttività = listaAttività;
    }

    public Date getData() {
        return data;
    }

    public int getTempoTotale() {
        return tempoTotale;
    }

    public ArrayList<Attività> getListaAttività() {
        return listaAttività;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTempoTotale(int tempoTotale) {
        this.tempoTotale = tempoTotale;
    }
    
}
