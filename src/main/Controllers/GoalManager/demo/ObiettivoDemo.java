/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.GoalManager.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author andre
 */
public class ObiettivoDemo implements IObiettivo {
    private String nome;
    private String descrizione;
    private Date data;
    private ArrayList<IObiettivo> sottoObiettivi;
    private String id;

    public ObiettivoDemo(String nome, String descrizione, Date data, ArrayList<IObiettivo> sottoObiettivi) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.sottoObiettivi = sottoObiettivi;
        this.id = UUID.randomUUID().toString();
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

    public ArrayList<IObiettivo> getSottoObiettivi() {
        return sottoObiettivi;
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

    public void setData(Date data) {
        this.data = data;
    }

    public void setSottoObiettivi(ArrayList<IObiettivo> sottoObiettivi) {
        this.sottoObiettivi = sottoObiettivi;
    }
    
}
