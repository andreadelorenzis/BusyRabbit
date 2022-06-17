package main.model.goalmanager.classi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import main.model.goalmanager.interfacce.IAzioneSessione;
import main.model.timetracker.classi.TimerSemplice;
import main.model.timetracker.interfacce.ITrackable;

public class AzioneSessione extends Azione implements IAzioneSessione, ITrackable {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * La durata della sessione in minuti
     */
    private int durata;

    /**
     * Il timer per misurare il tempo
     */
    private TimerSemplice timer;
    
    /**
     * Se il timer è avviato o no
     */
    private boolean avviato = false;
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param incremento
     * @param dataInizio
     * @param giorni
     * @param durata 
     */
    public AzioneSessione(String nome, int incremento, LocalDate dataInizio, List<DayOfWeek> giorni, int durata) {
        super(nome, incremento, dataInizio, giorni);
        this.durata = durata;
        this.timer = new TimerSemplice(durata * 60);
		this.timer.registraAscoltatore(this);
    }
    
    /**
     * 
     * @param nome
     * @param incremento
     * @param dataInizio
     * @param giorni
     * @param durata
     * @param id
     */
    public AzioneSessione(String nome, int incremento, LocalDate dataInizio, List<DayOfWeek> giorni, int durata, String id) {
        super(nome, incremento, dataInizio, giorni, id);
        this.durata = durata;
        this.timer = new TimerSemplice(durata * 60);
		this.timer.registraAscoltatore(this);
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void avviaSessione() {
    	timer.avvia();
    	this.avviato = true;
    }
    
    @Override
    public void terminaSessione() {
    	timer.termina();
    	this.avviato = false;
    }

    @Override
    public int getDurata() {
        return durata;
    }

    @Override
    public TimerSemplice getTimer() {
        return timer;
    }

    @Override
    public void setDurata(int durata) {
        this.durata = durata;
    }

	@Override
	public void secondoPassato(int o, int m, int s) {
		
	}
	
	@Override
	public void timerTerminato(long tempo) {
    	completa();
    	this.avviato = false;
	}
	
	@Override
	public TimerSemplice nuovoTimer(int durata) {
		this.durata = durata;
		this.timer = new TimerSemplice(durata * 60);
		timer.registraAscoltatore(this);
		return timer;
	}
	
	@Override
	public boolean getAvviato() {
		return this.avviato;
	}
	
	/**
	 * Metodo per usato per il testing di AzioneSessione
	 */
	public void impostaTimerInSecondi(int secondi) {
		this.timer = new TimerSemplice(secondi);
		timer.registraAscoltatore(this);
	}
    
}
