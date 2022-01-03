/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.agenda.classes;

import java.time.LocalDate;
import java.util.UUID;
import main.Models.goalmanager.classes.ObiettivoMisurabile;

//Entità che rappresenta l'evento futuro.

public class Evento {
   
    //CAMPI  
    private String nome;                    // Nome dell'evento.
    private String descrizione;             // Descrizione dell'evento.
    private LocalDate data;                 // Data in cui avverrà l'evento.
    private String obiettivo;               // Obiettivo associato all'evento. 
    private int valore;                     // Valore dell'evento che incrementerò l'obiettivo associato.
    private boolean completato;             // Variabile booleana che rappresenta il completamento.
    private String id;          // Identificativo dell'attività.
   
    //  COSTRUTTORI
    public Evento(String nome, String descrizione, LocalDate data, String Obiettivo, int valore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.obiettivo = obiettivo;
        this.valore = valore;
        this.completato = false;   
        this.id = UUID.randomUUID().toString();
    };
    
    public Evento(String nome, String descrizione, LocalDate data) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.completato = false;
        this.id = UUID.randomUUID().toString();
    };
    
    //costruttore per metodo inizializzaEventiDaFile
    public Evento(String nome, String descrizione, LocalDate data, String Obiettivo, int valore, int id) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.obiettivo = obiettivo;
        this.valore = valore;
        this.completato = false;   
        this.id = UUID.randomUUID().toString();
    };

    //  METODI PUBBLICI

    public void setNome(String nome) {
        this.nome = nome;
    };
    public String getNome(){
        return this.nome;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    };
    public String getDescrizione(){
        return this.descrizione;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }
    public LocalDate getData() {
        return this.data;
    }

    public void setObiettivo(String obiettivo) {
        this.obiettivo = obiettivo;
    }
    public String getObiettivo(){
        return this.obiettivo;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }
    public int getValore(){
        return this.valore;
    }

    public void setCompletato(boolean completato) {
        this.completato = completato;
    }
    public boolean getCompleato(){
        return this.completato;
    }
    
    public String getId(){
        return this.id;
    }
}
