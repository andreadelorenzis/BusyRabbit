package main.Models.timetracker.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.ITrackable;

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
	 * Se attualmente vi � una sessione o una pausa breve
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
						terminaSessione();
					}
					faseSuccessiva(); 
				}
			};
		});
	}
	
    //---------------------------- METODI PUBBLICI -----------------------------
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
	
	@Override 
	public void avvia() {
		if(!avviato) {
			avviato = true;
			faseSuccessiva();
		}
	}
	
	@Override
	public void terminaSessione() {
		ascoltatore.timerTerminato(tempoPassato / 1000);
	}
	
	@Override
	public void setDurataSessione(int durata) {
		this.durataSessione = durata;
	}

	@Override
	public void setDurataPausaBreve(int durata) {
		this.durataPausaBreve = durata;
	}

	@Override
	public void setDurataPausaLunga(int durata) {
		this.durataPausaLunga = durata;
	}

	@Override
	public void setNCicli(int nCicli) {
		this.nCicli = nCicli;
	}

	@Override
	public int getDurataSessione() {
		return durataSessione;
	}

	@Override
	public int getDurataPausaBreve() {
		return durataPausaBreve;
	}

	@Override
	public int getDurataPausaLunga() {
		return durataPausaLunga;
	}

	@Override
	public int getNCicli() {
		return nCicli;
	}

}
