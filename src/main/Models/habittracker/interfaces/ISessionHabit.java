package main.Models.habittracker.interfaces;

import main.Models.timetracker.interfaces.ITrackable;

/**
 * Habit that has a duration. To complete the habit, a timer with the given duration must pass.
 */
public interface ISessionHabit extends IHabit {
	
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
}
