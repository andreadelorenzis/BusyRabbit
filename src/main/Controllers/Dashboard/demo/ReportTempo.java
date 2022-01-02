/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.Dashboard.demo;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class ReportTempo {
    private int tempoMassimoMesi;
    private ArrayList<ArrayList<Integer>> monitoraggioMesi;
    
    public ReportTempo() {
        ArrayList<Integer> tempoProgetti = new ArrayList<Integer>();
        tempoProgetti.add(10);  // progetto 1
        tempoProgetti.add(20);  // progetto 2
        tempoProgetti.add(30);  // progetto 3
        
        for(int i = 0; i < 12; i++) {
            this.monitoraggioMesi.add(tempoProgetti);
        }
        
        this.tempoMassimoMesi = 60;
    }
}
