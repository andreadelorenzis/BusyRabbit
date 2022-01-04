/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.timetracker.classes;

import java.time.LocalDate;
import java.util.UUID;
import main.Models.timetracker.interfaces.IAttività;

 // Rappresenta un'attività che è stata monitorata dall'utente.

public class Attività implements IAttività{
    //  CAMPI
    private String nome;        // Nome dell'attività.    
    private LocalDate data;     // Data in cui è stata svolta l'attività.    
    private long durata;        // Durata dell'attività.    
    private String progetto;    // Progetto a cui l'attività è associato.    
    private String id;          // Identificativo dell'attività, generato casualmente.
    
    //  COSTRUTTORI    
    public Attività(String nome, LocalDate data, long durata, String progetto) {
        this.nome = nome;
        this.data = data;
        this.durata = durata;
        this.progetto = progetto;
        this.id = UUID.randomUUID().toString();
    }    
    
    public Attività(LocalDate data, long durata, String nome) {
        this.data = data;
        this.durata = durata;
        this.nome = nome;
        this.progetto = null;
        this.id = UUID.randomUUID().toString();
    }
    //  METODI PUBBLICI
     public void setNome(String nome) {
        this.nome = nome;
    }    
     
    public String getNome() {
        return this.nome;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public void incDurata(long durata){
        this.durata += durata;
    }

    public long getDurata() {
        return durata;
    }
    
    public void setDurata(int durata) {
        this.durata = durata;
    }
    
    public void setProgettoPadre(String progetto) {
        this.progetto = progetto;
    }
        
    public String getId() {
        return this.id;
    }
    
    public String getProgetto() {
        return progetto;
    }    
}