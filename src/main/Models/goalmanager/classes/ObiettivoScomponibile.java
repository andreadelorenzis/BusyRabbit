package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;

public class ObiettivoScomponibile extends Obiettivo implements IObiettivoScomponibile {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * La lista di sotto-obiettivi 
     */
    private List<IObiettivo> sottoObiettivi = new ArrayList<>();
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param data 
     */
    public ObiettivoScomponibile(String nome, LocalDate data) {
        super(nome, data);
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data 
     */
    public ObiettivoScomponibile(String nome, String descrizione, LocalDate data) {
        super(nome, descrizione, data);
    }
    
    /**
     * 
     * @param nome
     * @param descrizione
     * @param data
     * @param id
     */
    public ObiettivoScomponibile(String nome, String descrizione, LocalDate data, String id) {
        super(nome, descrizione, data, id);
    }


    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void completa() {
        super.completa();
        boolean sottoObCompletato;
        if(this.getCompletato()) {
        	sottoObCompletato = true;
        } else {
        	sottoObCompletato = false;
        }
        for(IObiettivo o : sottoObiettivi) {
        	o.setCompletato(sottoObCompletato);
        }
    }
    
    @Override
    public void verificaCompletamento() {
    	boolean completato = true;
    	for(IObiettivo o : sottoObiettivi) {
    		if(!o.getCompletato()) {
    			completato = false;
    		}
    	}
    	if(completato) {
    		completa();
    	} else {
    		setCompletato(false);
    	}
    }
    
    @Override
    public double calcolaProgresso() {
        int sottoObiettiviCompletati = 0;
        for(IObiettivo ob : sottoObiettivi) {
            if(ob.getCompletato()) {
                sottoObiettiviCompletati++;
            }
        }
        
        if(this.getCompletato()) {
            return 1.0;
        }
        
        return (double)sottoObiettiviCompletati / sottoObiettivi.size();
    }

    @Override
    public void aggiungiSottoObiettivo(IObiettivo obiettivo) {
        obiettivo.setObiettivoPadre(this);
        sottoObiettivi.add(obiettivo);
    }

    @Override
    public List<IObiettivo> getSottoObiettivi() {
        return sottoObiettivi;
    }

    @Override
    public void eliminaSottoObiettivo(String idObiettivo) {
        sottoObiettivi = sottoObiettivi.stream()    
                                       .filter(ob -> !(ob.getId().equals(idObiettivo)))
                                       .collect(Collectors.toList());
    }
    
}
