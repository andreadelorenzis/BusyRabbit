package main.Models.habittracker.interfaces;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Represents a day of monitoring habits. It contains the date, the total habits for that day and the habits 
 * completed on that date.
 *
 */
public interface IHabitDay {

	/**
	 * 
	 * @param habits the list of total habits that were to complete.
	 */
	public void addTotalHabits(List<IHabit> habits);
		
	/**
	 * 
	 * @param a completed habit for in this date.
	 */
	public void addCompletedHabit(IHabit h);
	
	/**
	 * 
	 * @param ha completed habit for in this date.
	 */
	public void removeCompletedHabit(IHabit h);
	
	/**
	 * 
	 * @return the date 
	 */
    public LocalDate getDay();
    
    /**
     * 
     * @return the list of total habits that were to complete.
     */
    public List<IHabit> getNumTotalHabits();

    /**
     * 
     * @return the list of the habits completed in that date.
     */
    public List<IHabit> getCompletedHabits();
}
