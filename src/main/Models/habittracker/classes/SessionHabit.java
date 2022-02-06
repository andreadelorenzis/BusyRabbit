package main.Models.habittracker.classes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import main.Models.habittracker.interfaces.ISessionHabit;
import main.Models.timetracker.classes.TimerSemplice;
import main.Models.timetracker.interfaces.ITrackable;

public class SessionHabit extends Habit implements ISessionHabit, ITrackable {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Duration of the session
	 */
	private int duration;
	
	/*
	 * Timer of the session
	 */
	private TimerSemplice timer;

    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param name
	 * @param startDate
	 * @param days
	 * @param duration
	 */
	public SessionHabit(String name, LocalDate startDate, List<DayOfWeek> days, int duration) {
		super(name, "", startDate, days);
		this.duration = duration;
		this.timer = new TimerSemplice(duration, this);
	}
	
	/**
	 * 
	 * @param name
	 * @param startDate
	 * @param days
	 * @param duration
	 * @param id
	 */
	public SessionHabit(String name, LocalDate startDate, List<DayOfWeek> days, int duration, String id) {
		super(name, "", startDate, days, id);
		this.duration = duration;
		this.timer = new TimerSemplice(duration, this);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 * @param duration
	 */
	public SessionHabit(String name, String description, LocalDate startDate, List<DayOfWeek> days, int duration) {
		super(name, description, startDate, days);
		this.duration = duration;
		this.timer = new TimerSemplice(duration, this);
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
	public SessionHabit(String name, String description, LocalDate startDate, List<DayOfWeek> days, int duration, String id) {
		super(name, description, startDate, days, id);
		this.duration = duration;
		this.timer = new TimerSemplice(duration, this);
	}

	//---------------------------- METODI PUBBLICI -----------------------------
	@Override
	public void timerTerminato(long tempo) {
		complete();
	}

	@Override
	public void startSession() {
		timer.setDurata(duration);
		timer.avvia();
	}

	@Override
	public void stopSession() {
		timer.termina();
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
		// TODO Auto-generated method stub
	}
	
}
