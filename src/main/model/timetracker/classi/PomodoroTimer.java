package main.model.timetracker.classi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.model.timetracker.interfacce.IPomodoroTimer;
import main.model.timetracker.interfacce.ITrackable;

public class PomodoroTimer extends Tracker implements IPomodoroTimer {
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Durata sessione in secondi (default: 30M)
	 */
	private int durataSessione = 1800;
	
	/*
	 * Durata pausa breve in secondi (default: 5M)
	 */
	private int durataPausaBreve = 300;
	
	/*
	 * Durata pausa lunga in secondi (default: 10M)
	 */
	private int durataPausaLunga = 600;
	
	/*
	 * Numero di cicli prima della pausa lunga
	 */
	private int nCicli = 3;
	
	/*
	 * Durata attuale del timer in millisecondi
	 */
	private int durataTimer = durataSessione * 1000;
	
	/*
	 * Se attualmente vi è una sessione
	 */
	private boolean sessione = true;
	
	/*
	 * Se attualmente ci deve essere una pausa breve
	 */
	private boolean pausaBreve = false;
	
	/*
	 * Se attualmente ci deve essere una pausa lunga 
	 */
	private boolean pausaLunga = false;
	
	/*
	 * Numero di cicli sessione-pausaBreve svolti
	 */
	private int cicloCorrente = 0;
	
    //----------------------------- COSTRUTTORI --------------------------------
	public PomodoroTimer() {
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(durataTimer > 0) {
					tempoPassato += 1000;
					durataTimer -= 1000;
					ore = (durataTimer / 3600000);
					minuti = (durataTimer / 60000) % 60;
					secondi = (durataTimer / 1000) % 60;
					notificaAscoltatoriSecondoPassato();
				} else {
					if(sessione) {
						sessioneTerminata();
						pausaBreve = true;
						sessione = false;
					} else if(pausaBreve) {
						pausaBreve = false;
						if(cicloCorrente < nCicli)
							sessione = true;
						else 	
							pausaLunga = true;
					} else {
						pausaLunga = false;
						pausaBreve = false;
						sessione = true;
					}
					
					faseSuccessiva(); 
				}
			};
		});
	}
	
	//---------------------------- METODI PRIVATI ------------------------------
	/**
	 * Permette di andare alla fase successiva programmata per il pomodoro timer
	 */
	private void faseSuccessiva() {
		tempoPassato = 0;
		
		// se ci sono ancora cicli prima della pausa lunga
		if(cicloCorrente < nCicli) {
			if(sessione) {
				durataTimer = durataSessione * 1000;
				timer.start();
			} else {
				durataTimer = durataPausaBreve * 1000;
				timer.start();
				cicloCorrente++;
			}
		} else {
			durataTimer = durataPausaLunga * 1000;
			timer.start();
			cicloCorrente = 0;
		}
	}
	
	//---------------------------- METODI PUBBLICI -----------------------------
	@Override 
	public void avvia() {
		sessione = true;
		pausaBreve = false;
		pausaLunga = false;
		if(!avviato) {
			avviato = true;
			faseSuccessiva();
		}
	}
	
	@Override
	public void sessioneTerminata() {
		this.notificaAscoltatoriTrackerTerminato();
	}
	
	@Override
	public void setSessione(int durata) {
		this.durataSessione = durata;
	}

	@Override
	public void setPausaBreve(int durata) {
		this.durataPausaBreve = durata;
	}

	@Override
	public void setPausaLunga(int durata) {
		this.durataPausaLunga = durata;
	}

	@Override
	public void setCicli(int nCicli) {
		this.nCicli = nCicli;
	}

	@Override
	public int getSessione() {
		return durataSessione;
	}

	@Override
	public int getPausaBreve() {
		return durataPausaBreve;
	}

	@Override
	public int getPausaLunga() {
		return durataPausaLunga;
	}

	@Override
	public int getCicli() {
		return nCicli;
	}
	
	@Override
	public void notificaAscoltatoriTrackerTerminato() {
		for(ITrackable ascoltatore : ascoltatori) {
			ascoltatore.timerTerminato(durataSessione);
		}
	}

	@Override
	public boolean getInSessione() {
		return this.sessione;
	}
}
