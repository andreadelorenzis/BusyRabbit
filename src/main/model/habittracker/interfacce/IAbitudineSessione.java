package main.model.habittracker.interfacce;

import main.model.timetracker.classi.TimerSemplice;
import main.model.timetracker.interfacce.ITrackable;

/**
 * Habit that has a duration. To complete the habit, a timer with the given duration must pass.
 */
public interface IAbitudineSessione extends IAbitudine {
	
	/**
	 * Start the habit session
	 */
    public void startSession();
    
    /**
     * Reset the session
     */
    public void stopSession();
    
    /**
     * 
     * @param duration of the habit
     */
    public void setDuration(int duration);
    
    /**
     * 
     * @return duration of the habit
     */
    public int getDuration();
    
    /**
     * 
     * @return se la sessione è iniziata
     */
    public boolean isStarted();
    
    /**
     * Create a new instance of TimerSemplice
     */
    public TimerSemplice newTimer(int duration);
}
