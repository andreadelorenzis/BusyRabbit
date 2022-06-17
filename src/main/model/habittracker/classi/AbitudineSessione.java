package main.model.habittracker.classi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import main.model.habittracker.interfacce.IAbitudineSessione;
import main.model.timetracker.classi.TimerSemplice;
import main.model.timetracker.interfacce.ITrackable;

public class AbitudineSessione extends Abitudine implements IAbitudineSessione, ITrackable {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Duration of timer
	 */
	private int duration;
	
	/*
	 * Timer of the session
	 */
	private TimerSemplice timer;
	
	private boolean isStarted = false;

    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param name
	 * @param startDate
	 * @param days
	 * @param duration
	 */
	public AbitudineSessione(String name, LocalDate startDate, List<DayOfWeek> days, int duration) {
		super(name, "", startDate, days);
		this.duration = duration;
		this.timer = new TimerSemplice(duration);
		this.timer.registraAscoltatore(this);
	}
	
	/**
	 * 
	 * @param name
	 * @param startDate
	 * @param days
	 * @param duration
	 * @param id
	 */
	public AbitudineSessione(String name, LocalDate startDate, List<DayOfWeek> days, int duration, String id) {
		super(name, "", startDate, days, id);
		this.duration = duration;
		this.timer = new TimerSemplice(duration);
		this.timer.registraAscoltatore(this);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 * @param duration
	 */
	public AbitudineSessione(String name, String description, LocalDate startDate, List<DayOfWeek> days, int duration) {
		super(name, description, startDate, days);
		this.duration = duration;
		this.timer = new TimerSemplice(duration);
		this.timer.registraAscoltatore(this);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 * @param duration
	 * @param id
	 */
	public AbitudineSessione(String name, String description, LocalDate startDate, List<DayOfWeek> days, int duration, String id) {
		super(name, description, startDate, days, id);
		this.duration = duration;
		this.timer = new TimerSemplice(duration);
		this.timer.registraAscoltatore(this);
	}

	//---------------------------- METODI PUBBLICI -----------------------------
	@Override
	public void timerTerminato(long tempo) {
		complete();
		isStarted = false;
	}

	@Override
	public void startSession() {
		timer.avvia();
		isStarted = true;
	}

	@Override
	public void stopSession() {
		timer.termina();
		isStarted = false;
	}

	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
	
	}
	
	@Override
	public boolean isStarted() {
		return this.isStarted;
	}
	
}
