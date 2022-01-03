/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.HabitTracker;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author andre
 */
public class GiornoCompletamento {
    private Date giorno;
    private double numTotaleAbitudini;
    private ArrayList<AbitudineDemo> abitudiniCompletate;

    public GiornoCompletamento(Date giorno, int numTotaleAbitudini, ArrayList<AbitudineDemo> abitudiniCompletate) {
        this.giorno = giorno;
        this.numTotaleAbitudini = numTotaleAbitudini;
        this.abitudiniCompletate = abitudiniCompletate;
    }

    public Date getGiorno() {
        return giorno;
    }

    public double getNumTotaleAbitudini() {
        return numTotaleAbitudini;
    }

    public ArrayList<AbitudineDemo> getAbitudiniCompletate() {
        return abitudiniCompletate;
    }
    
    
    
}
