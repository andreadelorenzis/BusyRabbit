package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.Controllers.GoalManager.GMHelper;
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
     * L'unità di misura del valore di questo obiettivo
     */
    private String unita;

    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param data
     * @param valore
     * @param unita
     */
    public ObiettivoAzione(String nome, LocalDate data, int valore, String unita) {
    	super(nome, data);
    	this.valoreTotale = valore;
    	this.unita = unita;
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data
     * @param valoreTotale
     * @param unita
     */
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unita) {
        super(nome, descrizione, data);
        this.valoreTotale = valoreTotale;
    	this.unita = unita;
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
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unita, String id) {
        super(nome, descrizione, data, id);
        this.valoreTotale = valoreTotale;
    	this.unita = unita;
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public double calcolaProgresso() {
        return (double) valoreAttuale / valoreTotale;
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
        			 && GMHelper.giornoPresente(data, az.getGiorniRipetizione()) 
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
    public String getUnita() {
        return unita;
    }

    @Override
    public void setUnita(String unita) {
        this.unita = unita;
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
