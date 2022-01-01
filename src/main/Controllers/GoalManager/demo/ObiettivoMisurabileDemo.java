/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.GoalManager.demo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author andre
 */
public class ObiettivoMisurabileDemo extends ObiettivoDemo {
    
    private String unità;
    
    private int valore;
    
    private ArrayList<AzioneDemo> azioni;
    
    public ObiettivoMisurabileDemo(String nome, String descrizione, Date data,
            ArrayList<ObiettivoDemo> sottoObiettivi, String unità, int valore) {
        super(nome, descrizione, data, sottoObiettivi);
        this.unità = unità;
        this.valore = valore;
    }

    public String getUnità() {
        return unità;
    }

    public int getValore() {
        return valore;
    }

    public void setUnità(String unità) {
        this.unità = unità;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }
    
    public void setAzioni(ArrayList<AzioneDemo> azioni) {
        this.azioni = azioni;
    }
    
    public ArrayList<AzioneDemo> getAzioni() {
        return this.azioni;
    }
    
}
