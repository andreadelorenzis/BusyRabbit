package main.model.goalmanager.classi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IObiettivoAzione;

public class ObiettivoAzione extends Obiettivo implements IObiettivoAzione {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * La lista di azioni collegate a questo obiettivo
     */
    private List<IAzione> azioni = new ArrayList<>();
    
    /**
     * Il valore totale da raggiungere 
     */
    private int valoreTotale;
    
    /**
     * Il valore attuale raggiunto completando le azioni
     */
    private int valoreAttuale = 0;
    
    /**
     * L'unità  di misura del valore di questo obiettivo
     */
    private String unità;

    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param data
     * @param valore
     * @param unita
     */
    public ObiettivoAzione(String nome, LocalDate data, int valore, String unità) {
    	super(nome, data);
    	this.valoreTotale = valore;
    	this.unità = unità;
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data
     * @param valoreTotale
     * @param unita
     */
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unità) {
        super(nome, descrizione, data);
        this.valoreTotale = valoreTotale;
    	this.unità = unità;
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data
     * @param valoreTotale
     * @param unita
     * @param id
     */
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unità, String id) {
        super(nome, descrizione, data, id);
        this.valoreTotale = valoreTotale;
    	this.unità = unità;
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public double calcolaProgresso() {
    	if(getCompletato()) {
    		return 1.0;
    	}
        return (double) valoreAttuale / valoreTotale;
    }
    
    @Override
    public void verificaCompletamento() {
    	if(valoreAttuale / valoreTotale == 1) {
    		this.completa();
    	} else {
    		this.setCompletato(false);
    	}
    }

    @Override
    public void collegaAzione(IAzione azione) {
        azione.setObiettivo(this);
        azioni.add(azione);
    }

    @Override
    public List<IAzione> getAzioni() {
        return azioni;
    }
    
    @Override 
    public List<IAzione> getAzioniGiornaliere(LocalDate data) {
        List<IAzione> azioni = new ArrayList<>();
        this.azioni.stream()
        	  .forEach(az -> {
        		  if((az.getDataInizio().isBefore(data) || az.getDataInizio().isEqual(data)) 
        			 && GoalManager.giornoPresente(data, az.getGiorniRipetizione()) 
        			 && !super.getCompletato()) {
        			  azioni.add(az);
        		  }
        	  });
        return azioni;    
    }

    @Override
    public void eliminaAzione(String idAzione) {
        azioni = azioni.stream()
                       .filter(az -> !(az.getId().equals(idAzione)))
                       .collect(Collectors.toList());
    }

    @Override
    public String getUnità() {
        return unità;
    }

    @Override
    public void setUnita(String unità) {
        this.unità = unità;
    }

    @Override
    public int getValoreAttuale() {
        return valoreAttuale;
    }

    @Override
    public int getValoreTotale() {
        return valoreTotale;
    }

    @Override
    public void setValoreAttuale(int valore) {
        valoreAttuale = valore;
    }

    @Override
    public void setValoreTotale(int valore) {
        valoreTotale = valore;
    }
    
}
