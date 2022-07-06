package main.model.timetracker.classi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.timetracker.interfacce.IActivity;

/**
 * 
 * Contiene la lista di attivit� in un dato giorno.
 *
 */
public class GiornoTracking {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Giorno di monitoraggio
	 */
	private int giorno;
	
	/*
	 * Lista di attivit� svolte in questo giorno
	 */
	private List<IActivity> attivit� = new ArrayList<>();
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param giorno
	 */
	public GiornoTracking(int giorno) {
		this.giorno = giorno;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiAttivit�(IActivity a) {
		this.attivit�.add(a);
	}
	
	public void rimuoviAttivit�(String id) {
		attivit� = attivit�.stream()
						   .filter(a -> !(a.getId().equals(id)))
						   .collect(Collectors.toList());
	}
	
	public int getGiorno() {
		return this.giorno;
	}
	
	public List<IActivity> getAttivit�() {
		return this.attivit�;
	}
}
