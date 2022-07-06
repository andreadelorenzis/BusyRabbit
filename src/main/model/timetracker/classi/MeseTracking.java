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
public class MeseTracking {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Mese di monitoraggio 
	 */
	private Month mese;
	
	/*
	 * Chiave: giorno
	 * Valore: GiornoAttivit� 
	 */
	private Map<Integer, GiornoTracking> giorni = new TreeMap<>(Collections.reverseOrder());
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param mese
	 */
	public MeseTracking(Month mese) {
		this.mese = mese;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiGiorno(GiornoTracking g) {
		giorni.put(g.getGiorno(), g);
	}
	
	public void rimuoviGiorno(int giorno) {
		giorni.remove(giorno);
	}
	
	public Month getMese() {
		return mese;
	}
	
	public Map<Integer, GiornoTracking> getGiorni() {
		return giorni;
	}
}
