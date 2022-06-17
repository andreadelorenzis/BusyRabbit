package main.model.timetracker.classi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import main.model.timetracker.interfacce.ICronometro;
import main.model.timetracker.interfacce.ITrackable;

public class Cronometro extends Tracker implements ICronometro {
	
    //----------------------------- COSTRUTTORI --------------------------------
	public Cronometro() {
		timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tempoPassato += 1000;
					ore = (int)(tempoPassato / 3600000);
					minuti = (int)(tempoPassato / 60000) % 60;
					secondi = (int)(tempoPassato / 1000) % 60;
					notificaAscoltatoriSecondoPassato();
				};
			});
	}

    //-------------------------- METODI PUBBLICI ----------------------------
	
	@Override
	public long getTempoAttuale() {
		return tempoPassato / 1000;
	}
	
	@Override
	public void notificaAscoltatoriTrackerTerminato() {
		for(ITrackable ascoltatore : ascoltatori) {
			ascoltatore.timerTerminato(tempoPassato);
		}
	}

}
