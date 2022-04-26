package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IObiettivoAzione;

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
     * L'unit√† di misura del valore di questo obiettivo
     */
    private String unit‡;

    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param data
     * @param valore
     * @param unita
     */
    public ObiettivoAzione(String nome, LocalDate data, int valore, String unit‡) {
    	super(nome, data);
    	this.valoreTotale = valore;
    	this.unit‡ = unit‡;
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data
     * @param valoreTotale
     * @param unita
     */
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unit‡) {
        super(nome, descrizione, data);
        this.valoreTotale = valoreTotale;
    	this.unit‡ = unit‡;
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
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unit‡, String id) {
        super(nome, descrizione, data, id);
        this.valoreTotale = valoreTotale;
    	this.unit‡ = unit‡;
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
    public String getUnit‡() {
        return unit‡;
    }

    @Override
    public void setUnita(String unit‡) {
        this.unit‡ = unit‡;
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
