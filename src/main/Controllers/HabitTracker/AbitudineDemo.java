/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.HabitTracker;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class AbitudineDemo {
    private String nome;
    private String descrizione;
    private int serieAttuale;
    private int serieRecord;
    private Boolean completata;
    private ArrayList<Integer> giorniRipetizione;
    private ArrayList<ItemDemo> items;

    public AbitudineDemo(String nome, String descrizione, ArrayList<Integer> giorniRipetizione, ArrayList<ItemDemo> items) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.giorniRipetizione = giorniRipetizione;
        this.items = items;
        this.serieAttuale = 0;
        this.serieRecord = 0;
    }
     
    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getSerieAttuale() {
        return serieAttuale;
    }

    public int getSerieRecord() {
        return serieRecord;
    }

    public Boolean getCompletata() {
        return completata;
    }

    public ArrayList<Integer> getGiorniRipetizione() {
        return giorniRipetizione;
    }

    public ArrayList<ItemDemo> getItems() {
        return items;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setSerieAttuale(int serieAttuale) {
        this.serieAttuale = serieAttuale;
    }

    public void setSerieRecord(int serieRecord) {
        this.serieRecord = serieRecord;
    }

    public void setCompletata(Boolean completata) {
        this.completata = completata;
    }

    public void setGiorniRipetizione(ArrayList<Integer> giorniRipetizione) {
        this.giorniRipetizione = giorniRipetizione;
    }

    public void setItems(ArrayList<ItemDemo> items) {
        this.items = items;
    }
    
    
}
