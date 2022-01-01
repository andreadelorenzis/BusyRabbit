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
public class ObiettivoDemo implements IObiettivo {
    private String nome;
    private String descrizione;
    private Date data;
    private ArrayList<ObiettivoDemo> sottoObiettivi;

    public ObiettivoDemo(String nome, String descrizione, Date data, ArrayList<ObiettivoDemo> sottoObiettivi) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.sottoObiettivi = sottoObiettivi;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Date getData() {
        return data;
    }

    public ArrayList<ObiettivoDemo> getSottoObiettivi() {
        return sottoObiettivi;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setSottoObiettivi(ArrayList<ObiettivoDemo> sottoObiettivi) {
        this.sottoObiettivi = sottoObiettivi;
    }
    
}
