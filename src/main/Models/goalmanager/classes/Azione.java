package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import main.Giorno;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;

public abstract class Azione implements IAzione {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * Nome dell'azione
     */
    private String nome;
    
    /**
     * Se l'azione è completata o meno
     */
    private boolean completata = false;
    
    /**
     * Il valore di cui viene incrementato l'obiettivo a cui è collegata l'azione
     */
    private int incremento;
    
    /**
     * I giorni di ripetizione dell'azione
     */
    private List<Giorno> giorni = new ArrayList<>();
    
    /**
     * La data di partenza dell'azione
     */
    private LocalDate dataInizio;
    
    /**
     * L'obiettivo a cui l'azione è collegata
     */
    private IObiettivoAzione obiettivo;
    
    /**
     * Identificativo dell'azione
     */
    private String id;

    //----------------------------- COSTRUTTORI --------------------------------
    /***
     * 
     * @param nome
     * @param incremento
     * @param dataInizio 
     */
    public Azione(String nome, int incremento, LocalDate dataInizio, List<Giorno> giorni) {
        this.nome = nome;
        this.incremento = incremento;
        this.dataInizio = dataInizio;
        this.giorni = giorni;
        this.id = UUID.randomUUID().toString();
    }
    
    public Azione(String nome, int incremento, LocalDate dataInizio, List<Giorno> giorni, String id) {
        this(nome, incremento, dataInizio, giorni);
        this.id = id;
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void completa() {
        if(!completata) {
        	completata = true;
        	obiettivo.setValoreAttuale(obiettivo.getValoreAttuale() + incremento);
        } else {
        	completata = false;
        	obiettivo.setValoreAttuale(obiettivo.getValoreAttuale() - incremento);
        }
        obiettivo.verificaCompletamento();
    }

    @Override
    public boolean getCompletata() {
        return completata;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getIncremento() {
        return incremento;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDate getDataInizio() {
        return dataInizio;
    }

    @Override
    public List<Giorno> getGiorniRipetizione() {
        return giorni;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setIncremento(int incremento) {
        this.incremento = incremento;
    }

    @Override
    public void setGiorniRipetizione(List<Giorno> giorni) {
        this.giorni = giorni;
    }

    @Override
    public void setDataInizio(LocalDate date) {
        this.dataInizio = dataInizio;
    }

    @Override
    public void setObiettivo(IObiettivoAzione obiettivo) {
        this.obiettivo = obiettivo;
    }

    @Override
    public IObiettivoAzione getObiettivo() {
        return obiettivo;
    }
    
}
