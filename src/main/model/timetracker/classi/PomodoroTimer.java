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
	int durataSessione = 1800;
	
	/*
	 * Durata pausa breve in secondi (default: 5M)
	 */
	int durataPausaBreve = 300;
	
	/*
	 * Durata pausa lunga in secondi (default: 10M)
	 */
	int durataPausaLunga = 600;
	
	/*
	 * Numero di cicli prima della pausa lunga
	 */
	int nCicli = 3;
	
	/*
	 * Durata attuale del timer in millisecondi
	 */
	int durataTimer = durataSessione * 1000;
	
	/*
	 * Se attualmente vi è una sessione o una pausa breve
	 */
	private boolean sessione = false;
	
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
					ascoltatore.secondoPassato(ore, minuti, secondi);
				} else {
					if(sessione) {
						sessioneTerminata();
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
		if(sospeso) {
			sospeso = false;
		} else {
			sessione = !sessione;
		}
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
			sessione = false;
			durataTimer = durataPausaLunga * 1000;
			timer.start();
			cicloCorrente = 0;
		}
	}
	
	//---------------------------- METODI PUBBLICI -----------------------------
	@Override 
	public void avvia() {
		if(!avviato) {
			avviato = true;
			faseSuccessiva();
		}
	}
	
	@Override
	public void sessioneTerminata() {
		ascoltatore.timerTerminato(tempoPassato / 1000);
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

}
