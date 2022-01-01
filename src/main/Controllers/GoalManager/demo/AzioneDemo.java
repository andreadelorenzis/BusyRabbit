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
public class AzioneDemo {
    private String nome;
    private ArrayList<String> giorni;
    private int valore;
    private IObiettivo obiettivoPadre;

    public AzioneDemo(String nome, ArrayList<String> giorni, int valore, IObiettivo obiettivoPadre) {
        this.nome = nome;
        this.giorni = giorni;
        this.valore = valore;
        this.obiettivoPadre = obiettivoPadre;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<String> getGiorni() {
        return giorni;
    }

    public int getValore() {
        return valore;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGiorni(ArrayList<String> giorni) {
        this.giorni = giorni;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public IObiettivo getObiettivoPadre() {
        return obiettivoPadre;
    }

    public void setObiettivoPadre(IObiettivo obiettivoPadre) {
        this.obiettivoPadre = obiettivoPadre;
    }
    
}
