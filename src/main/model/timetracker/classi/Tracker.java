package main.model.timetracker.classi;

import javax.swing.Timer;

import main.model.timetracker.interfacce.ITrackable;
import main.model.timetracker.interfacce.ITracker;

public abstract class Tracker implements ITracker {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Tempo passato in millisecondi
	 */
	int tempoPassato = 0;
	
	/*
	 * Secondi 
	 */
	int secondi = 0;
	
	/*
	 * Minuti
	 */
	int minuti = 0;
	
	/*
	 * Ore
	 */
	int ore = 0;

	/*
	 * Se il tracker è avviato o no
	 */
	public boolean avviato = false;
	
	/*
	 * Se il tracker è in pausa o no
	 */
	public boolean sospeso = false;
	
	/*
	 * Timer semplice
	 */
	Timer timer;
	
	/*
	 * L'oggetto che è in ascolto di questo tracker
	 */
	protected ITrackable ascoltatore;

    //-------------------------- METODI PUBBLICI ----------------------------
	@Override
	public void avvia() {
		if(!avviato) {
			avviato = true;
			timer.start();
		}
	}

	@Override
	public long termina() {
		long durata = tempoPassato / 1000;
		if(avviato) {
			avviato = false;
			sospeso = false;
			timer.stop();
			tempoPassato = 0;
			secondi = 0;
			minuti = 0;
			ore = 0;
		}
		return durata;
	}

	@Override
	public boolean getAvviato() {
		return avviato;
	}
	
	@Override
	public void setAscoltatore(ITrackable ascoltatore) {
		this.ascoltatore = ascoltatore;
	}

}
