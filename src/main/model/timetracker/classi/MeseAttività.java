package main.model.timetracker.classi;

import java.time.Month;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Contiene i giorni di monitoraggio delle attivitą in un mese.
 *
 */
public class MeseAttivitą {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Mese di monitoraggio 
	 */
	private Month mese;
	
	/*
	 * Chiave: giorno
	 * Valore: GiornoAttivitą 
	 */
	private Map<Integer, GiornoAttivitą> giorni = new TreeMap<>(Collections.reverseOrder());
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param mese
	 */
	public MeseAttivitą(Month mese) {
		this.mese = mese;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiGiorno(GiornoAttivitą g) {
		giorni.put(g.getGiorno(), g);
	}
	
	public void rimuoviGiorno(int giorno) {
		giorni.remove(giorno);
	}
	
	public Month getMese() {
		return mese;
	}
	
	public Map<Integer, GiornoAttivitą> getGiorni() {
		return giorni;
	}
}
