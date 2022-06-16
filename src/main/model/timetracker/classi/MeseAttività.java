package main.model.timetracker.classi;

import java.time.Month;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Contiene i giorni di monitoraggio delle attivit� in un mese.
 *
 */
public class MeseAttivit� {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Mese di monitoraggio 
	 */
	private Month mese;
	
	/*
	 * Chiave: giorno
	 * Valore: GiornoAttivit� 
	 */
	private Map<Integer, GiornoAttivit�> giorni = new TreeMap<>(Collections.reverseOrder());
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param mese
	 */
	public MeseAttivit�(Month mese) {
		this.mese = mese;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiGiorno(GiornoAttivit� g) {
		giorni.put(g.getGiorno(), g);
	}
	
	public void rimuoviGiorno(int giorno) {
		giorni.remove(giorno);
	}
	
	public Month getMese() {
		return mese;
	}
	
	public Map<Integer, GiornoAttivit�> getGiorni() {
		return giorni;
	}
}
