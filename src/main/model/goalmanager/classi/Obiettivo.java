package main.model.goalmanager.classi;

import java.time.LocalDate;
import java.util.UUID;
import main.model.goalmanager.interfacce.IObiettivo;
import main.model.goalmanager.interfacce.IObiettivoScomponibile;

public abstract class Obiettivo implements IObiettivo {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * Il nome dell'obiettivo
     */
    private String nome;
    
    /**
     * La descrizione
     */
    private String descrizione = "";
    
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
    private IObiettivoScomponibile obiettivoPadre = null;
    
    /**
     * Identificativo del obiettivo
     */
    private String id;
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param data 
     */
    public Obiettivo(String nome, LocalDate data) {
        this.nome = nome;
        this.data = data;
        this.id = UUID.randomUUID().toString();
    }
    
    /**
     * 
     * @param nome 
     * @param descrizione
     * @param data 
     */
    public Obiettivo(String nome, String descrizione, LocalDate data) {
        this(nome, data);
        this.descrizione = descrizione;
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data
     * @param id
     */
    public Obiettivo(String nome, String descrizione, LocalDate data, String id) {
        this(nome, data);
        this.descrizione = descrizione;
        this.id = id;
    }
    
    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public abstract double calcolaProgresso();
    
    public abstract void verificaCompletamento();

    @Override
    public void completa() {
        if(!fallito) {
            completato = !completato;
        }
        if(obiettivoPadre != null) {
        	obiettivoPadre.verificaCompletamento();
        }
        if(this instanceof ObiettivoScomponibile) {
        	((ObiettivoScomponibile) this).completaSottoObiettivi();
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
    public IObiettivoScomponibile getObiettivoPadre() {
        return obiettivoPadre;
    }
    
    @Override
    public void setCompletato(boolean completato) {
    	this.completato = completato;
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
    public void setObiettivoPadre(IObiettivoScomponibile obiettivo) {
        obiettivoPadre = obiettivo;
    }
    
	@Override
    public boolean equals(Object obj) {
  	      if (obj == this) {
  	         return true;
  	      }
  	      if (!(obj instanceof Obiettivo)) {
  	         return false;
  	      }
  	      IObiettivo o = (IObiettivo) obj;
  	      return this.id.equals(o.getId());
   }
    
}
