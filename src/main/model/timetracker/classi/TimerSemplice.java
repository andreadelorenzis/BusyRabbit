package main.model.timetracker.classi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.model.timetracker.interfacce.ITimerSemplice;
import main.model.timetracker.interfacce.ITrackable;

public class TimerSemplice extends Tracker implements ITimerSemplice {
	
	//-------------------------------- CAMPI -----------------------------------
	/*
	 * Durata del timer variabile
	 */
	private int tempoRimanente;
	
	private int durataTimer;
	
	//----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param durata
	 * @param ascoltatore
	 */
	public TimerSemplice(int durata) {
		this.durataTimer = durata;
		this.tempoRimanente = durata * 1000;
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tempoRimanente > 0) {
					tempoPassato += 1000;
					tempoRimanente -= 1000;
					ore = (tempoRimanente / 3600000);
					minuti = (tempoRimanente / 60000) % 60;
					secondi = (tempoRimanente / 1000) % 60;
					notificaAscoltatoriSecondoPassato();
				} else {
					notificaAscoltatoriTrackerTerminato();
					termina();
				}
			};
		});
	}
	
	//--------------------------- METODI PUBBLICI ------------------------------
	
	@Override
	public void setDurata(int durata) {
		this.tempoRimanente = durata;
	}
	
	@Override
	public void notificaAscoltatoriTrackerTerminato() {
		for(ITrackable ascoltatore : ascoltatori) {
			ascoltatore.timerTerminato(durataTimer);
		}
	}

}
