package main.model.timetracker.classi;

import java.util.Map;
import java.util.TreeMap;

import main.model.timetracker.interfacce.IActivity;

/**
 * 
 * Modella un anno di monitoraggio del progetto.
 *
 */
public class AnnoProgetto {
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Map le cui chiavi sono i mesi dell'anno e i valori sono il tempo totale del
	 * progetto nei vari mesi
	 */
	private Map<Integer, Long> tempiAnno = new TreeMap<>();
	
	/*
	 * I mesi in cui il progetto è stato monitorato in quest'anno
	 */
	private Map<Integer, MeseProgetto> mesiProgetto = new TreeMap<>(); 
	
	/*
	 * L'anno
	 */
	private int anno;
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param anno
	 */
	public AnnoProgetto(int anno) {
		this.anno = anno;
	}
	
    //-------------------------- METODI PUBBLICI ----------------------------
	public void aggiungiDurata(IActivity a) {
		int mese = a.getData().getMonth().getValue();
		// se il mese esiste
		if(tempiAnno.containsKey(mese)) {
			tempiAnno.put(mese, tempiAnno.get(mese) + a.getDurata());
		} else {
			tempiAnno.put(mese, a.getDurata());
		}
	}
	
	public void eliminaDurata(IActivity a) {
		int mese = a.getData().getMonth().getValue();
		if(tempiAnno.containsKey(mese)) {
			tempiAnno.put(mese, tempiAnno.get(mese) - a.getDurata());
			
			// rimuove il mese se la durata è uguale a zero
			if(tempiAnno.get(mese) == 0) {
				tempiAnno.remove(mese);
			}
		}
		
		// rimuove la durata dal MeseProgetto
		if(mesiProgetto.containsKey(mese)) {
			mesiProgetto.get(mese).eliminaDurata(a);
		}
		
	}
	
	public Map<Integer, MeseProgetto> getMesiProgetto() {
		return mesiProgetto;
	}
	
	public Map<Integer, Long> getTempiAnno() {
		return tempiAnno;
	}
}

