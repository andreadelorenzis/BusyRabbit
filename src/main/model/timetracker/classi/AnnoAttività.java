package main.model.timetracker.classi;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Anno di monitoraggio attività
 *
 */
public class AnnoAttività {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Anno di monitoraggio
	 */
	private int anno;
	
	/*
	 * Chiave: mese (1-12)
	 * Valore: MeseAttività
	 */
	private Map<Integer, MeseAttività> mesi = new TreeMap<>(Collections.reverseOrder());
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param anno
	 */
	public AnnoAttività(int anno) {
		this.anno = anno;
	}
	
    //-------------------------- METODI PUBBLICI ----------------------------
	public void aggiungiMese(MeseAttività m) {
		mesi.put(m.getMese().getValue(), m);
	}
	
	public void rimuoviMese(int m) {
		mesi.remove(m);
	}
	
	public int getAnno() {
		return anno;
	}
	
	public Map<Integer, MeseAttività> getMesi() {
		return mesi;
	}
}
