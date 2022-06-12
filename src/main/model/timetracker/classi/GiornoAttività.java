package main.model.timetracker.classi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.timetracker.interfacce.IAttivit�;

/**
 * 
 * Contiene la lista di attivit� in un dato giorno.
 *
 */
public class GiornoAttivit� {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Giorno di monitoraggio
	 */
	private int giorno;
	
	/*
	 * Lista di attivit� svolte in questo giorno
	 */
	private List<IAttivit�> attivit� = new ArrayList<>();
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param giorno
	 */
	public GiornoAttivit�(int giorno) {
		this.giorno = giorno;
	}
	
    //---------------------------- METODI PUBBLICI------------------------------
	public void aggiungiAttivit�(IAttivit� a) {
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
	
	public List<IAttivit�> getAttivit�() {
		return this.attivit�;
	}
}
