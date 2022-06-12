package main.model.timetracker.classi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.timetracker.interfacce.IAttività;

/**
 * 
 * Contiene la lista di attività in un dato giorno.
 *
 */
public class GiornoAttività {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Giorno di monitoraggio
	 */
	private int giorno;
	
	/*
	 * Lista di attività svolte in questo giorno
	 */
	private List<IAttività> attività = new ArrayList<>();
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param giorno
	 */
	public GiornoAttività(int giorno) {
		this.giorno = giorno;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiAttività(IAttività a) {
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
	
	public List<IAttività> getAttività() {
		return this.attività;
	}
}
