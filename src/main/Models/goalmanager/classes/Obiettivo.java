package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.UUID;
import main.Models.goalmanager.interfaces.IObiettivo;

public abstract class Obiettivo implements IObiettivo {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * Il nome dell'obiettivo
     */
    private String nome;
    
    /**
     * La descrizione
     */
    private String descrizione;
    
    /**
     * La data di scadenza dell'obiettivo
     */
    private LocalDate data;
    
    /**
     * Se l'obiettivo è completato o no
     */
    private boolean completato = false;
    
    /**
     * Se l'obiettivo è arrivato alla data di scadenza o no
     */
    private boolean fallito = false;
    
    /**
     * L'eventuale obiettivo padre di questo obiettivo, null altrimenti
     */
    private IObiettivo obiettivoPadre = null;
    
    /**
     * Identificativo del obiettivo
     */
    private final String id;
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome 
     * @param descrizione
     * @param data 
     */
    public Obiettivo(String nome, String descrizione, LocalDate data) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * 
     * @param nome
     * @param data 
     */
    public Obiettivo(String nome, LocalDate data) {
        this(nome, "", data);
    }
    
    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public abstract double calcolaProgresso();

    @Override
    public void completa() {
        if(!fallito) {
            completato = !completato;
        }
    }

    @Override
    public void faiFallire() {
        if(!completato) {
            if(!fallito) {
                fallito = true;
            }
        }
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public LocalDate getData() {
        return data;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean getCompletato() {
        return completato;
    }

    @Override
    public boolean getFallimento() {
        return fallito;
    }
    
    @Override
    public IObiettivo getObiettivoPadre() {
        return obiettivoPadre;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public void setData(LocalDate data) {
        this.data = data;
    }
    
    @Override
    public void setObiettivoPadre(IObiettivo obiettivo) {
        obiettivoPadre = obiettivo;
    }
    
}
