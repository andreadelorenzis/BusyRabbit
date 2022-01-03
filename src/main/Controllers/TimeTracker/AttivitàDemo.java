/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.TimeTracker;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author andre
 */
public class AttivitàDemo {
    private String nome;
    private Date giorno;
    private int durata;
    private ProgettoDemo progetto;
    private String id;

    public AttivitàDemo(Date giorno, int durata, String nome, ProgettoDemo progetto) {
        this.nome = nome;
        this.giorno = giorno;
        this.durata = durata;
        this.progetto = progetto;
        this.id = UUID.randomUUID().toString();
    }
    
    public AttivitàDemo(Date giorno, int durata, String nome) {
        this.nome = nome;
        this.giorno = giorno;
        this.durata = durata;
        this.id = UUID.randomUUID().toString();
    }

    public String getNome() {
        return nome;
    }

    public Date getGiorno() {
        return giorno;
    }

    public int getDurata() {
        return durata;
    }

    public ProgettoDemo getProgetto() {
        return progetto;
    }

    public String getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setProgetto(ProgettoDemo progetto) {
        this.progetto = progetto;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
}
