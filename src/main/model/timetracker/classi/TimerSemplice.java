package main.model.timetracker.classi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.model.timetracker.interfacce.ITimerSemplice;
import main.model.timetracker.interfacce.ITrackable;

public class TimerSemplice extends Tracker implements ITimerSemplice {
	
	//-------------------------------- CAMPI -----------------------------------
	/*
	 * Durata del timer
	 */
	private int durataTimer;
	
	//----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param durata
	 * @param ascoltatore
	 */
	public TimerSemplice(int durata, ITrackable ascoltatore) {
		registraAscoltatore(ascoltatore);
		this.durataTimer = durata * 60 * 1000;
		this.ascoltatore = ascoltatore;
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(durataTimer > 0) {
					tempoPassato += 1000;
					durataTimer -= 1000;
					ore = (durataTimer / 3600000);
					minuti = (durataTimer / 60000) % 60;
					secondi = (durataTimer / 1000) % 60;
					ascoltatore.secondoPassato(ore, minuti, secondi);
				} else {
					long durata = tempoPassato / 1000;
					ascoltatore.timerTerminato(durata);
					termina();
				}
			};
		});
	}
	
	//--------------------------- METODI PUBBLICI ------------------------------
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
	public void setDurata(int durata) {
		this.durataTimer = durata * 60 * 1000;
	}

}
