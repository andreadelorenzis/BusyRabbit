package main.model.timetracker.classi;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Anno di monitoraggio attivitą
 *
 */
public class AnnoAttivitą {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Anno di monitoraggio
	 */
	private int anno;
	
	/*
	 * Chiave: mese (1-12)
	 * Valore: MeseAttivitą
	 */
	private Map<Integer, MeseAttivitą> mesi = new TreeMap<>(Collections.reverseOrder());
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param anno
	 */
	public AnnoAttivitą(int anno) {
		this.anno = anno;
	}
	
    //-------------------------- METODI PUBBLICI ----------------------------
	public void aggiungiMese(MeseAttivitą m) {
		mesi.put(m.getMese().getValue(), m);
	}
	
	public void rimuoviMese(int m) {
		mesi.remove(m);
	}
	
	public int getAnno() {
		return anno;
	}
	
	public Map<Integer, MeseAttivitą> getMesi() {
		return mesi;
	}
}
