package main.model.habittracker.interfacce;

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
    
    public boolean isStarted();
}
