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
     * La durata della sessione
     */
    private int durata;

    /**
     * Il timer per misurare il tempo
     */
    private TimerSemplice timer;
    
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
        timer = new TimerSemplice(durata, this);
    }
    
    public AzioneSessione(String nome, int incremento, LocalDate dataInizio, List<DayOfWeek> giorni, int durata, String id) {
        super(nome, incremento, dataInizio, giorni, id);
        this.durata = durata;
        timer = new TimerSemplice(durata, this);
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void avviaSessione() {
    	timer.setDurata(durata);
    	timer.avvia();
    }
    
    @Override
    public void terminaSessione() {
    	timer.termina();
    }
    
	@Override
	public void timerTerminato(long tempo) {
    	completa();
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
		// TODO Auto-generated method stub
		
	}
    
}
