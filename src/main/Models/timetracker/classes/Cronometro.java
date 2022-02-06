package main.Models.timetracker.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import main.Models.timetracker.interfaces.ICronometro;

public class Cronometro extends Tracker implements ICronometro {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Secondi passati
	 */
	private int secondi = 0;
	
	/*
	 * Minuti passati 
	 */
	private int minuti = 0;
	
	/*
	 * Ore passate 
	 */
	private int ore = 0;
	
    //----------------------------- COSTRUTTORI --------------------------------
	public Cronometro() {
		timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tempoPassato += 1000;
					ore = (int)(tempoPassato / 3600000);
					minuti = (int)(tempoPassato / 60000) % 60;
					secondi = (int)(tempoPassato / 1000) % 60;
					ascoltatore.secondoPassato(ore, minuti, secondi);
				};
			});
	}

    //-------------------------- METODI PUBBLICI ----------------------------
	
	@Override
	public long getTempoAttuale() {
		return tempoPassato / 1000;
	}

}
