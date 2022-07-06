package main.model.timetracker.classi;

import java.time.Month;
import java.util.Map;
import java.util.TreeMap;

import main.model.timetracker.interfacce.IActivity;

/**
 * 
 * Modella un mese di monitoraggio del progetto.
 *
 */
public class MeseProgetto {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Chiavi: mese (1-12)
	 * Valori: tempo totale del progetto in quel mese
	 */
	private Map<Integer, Long> tempiMese = new TreeMap<>();
	
	/*
	 * Il mese di monitoraggio
	 */
	private final Month mese;
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param mese
	 */
	public MeseProgetto(final Month mese) {
		this.mese = mese;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiDurata(IActivity a) {
		int giorno = a.getData().getDayOfMonth();
		
		// se il giorno esiste
		if(tempiMese.containsKey(giorno)) {
			tempiMese.put(giorno, tempiMese.get(giorno) + a.getDurata());
		} else {
			tempiMese.put(giorno, a.getDurata());
		}
	}
	
	public void eliminaDurata(IActivity a) {
		int giorno = a.getData().getDayOfMonth();
		
		if(tempiMese.containsKey(giorno)) {
			tempiMese.put(giorno, tempiMese.get(giorno) - a.getDurata());
			
			// rimuove il giorno se la durata è uguale a zero
			if(tempiMese.get(giorno) == 0) {
				tempiMese.remove(giorno);
			}
		}
	}
	
	public Map<Integer, Long> getTempiMese() {
		return tempiMese;
	}
}
