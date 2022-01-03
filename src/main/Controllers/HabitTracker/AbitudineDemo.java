/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.HabitTracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
    private ArrayList<String> giorniRipetizione;
    private ArrayList<ItemDemo> items;
    private ArrayList<Date> giorniCompletamento;
    private String id;

    public AbitudineDemo(String nome, String descrizione, ArrayList<String> giorniRipetizione, ArrayList<ItemDemo> items) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.giorniRipetizione = giorniRipetizione;
        this.items = items;
        this.serieAttuale = 0;
        this.serieRecord = 0;
        this.id = UUID.randomUUID().toString();
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

    public ArrayList<String> getGiorniRipetizione() {
        return giorniRipetizione;
    }

    public ArrayList<ItemDemo> getItems() {
        return items;
    }
    
    public String getId() {
        return this.id;
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

    public void setGiorniRipetizione(ArrayList<String> giorniRipetizione) {
        this.giorniRipetizione = giorniRipetizione;
    }

    public void setItems(ArrayList<ItemDemo> items) {
        this.items = items;
    }
    
}
