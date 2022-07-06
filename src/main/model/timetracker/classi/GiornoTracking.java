package main.model.timetracker.classi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.timetracker.interfacce.IActivity;

/**
 * 
 * Contiene la lista di attività in un dato giorno.
 *
 */
public class GiornoTracking {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Giorno di monitoraggio
	 */
	private int giorno;
	
	/*
	 * Lista di attività svolte in questo giorno
	 */
	private List<IActivity> attività = new ArrayList<>();
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param giorno
	 */
	public GiornoTracking(int giorno) {
		this.giorno = giorno;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiAttività(IActivity a) {
		this.attività.add(a);
	}
	
	public void rimuoviAttività(String id) {
		attività = attività.stream()
						   .filter(a -> !(a.getId().equals(id)))
						   .collect(Collectors.toList());
	}
	
	public int getGiorno() {
		return this.giorno;
	}
	
	public List<IActivity> getAttività() {
		return this.attività;
	}
}
