package main.Models.habittracker.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 
 * Tool to track habits of the user.
 *
 */
public interface IAbitudineTracker {
    
    /**
     * 
     * @param habit
     */
    public void addHabit(IAbitudine habit);
    
    /**
     * Calculate habits to do in the specified date
     * 
     * @param date of today
     * @return list of today's habits
     */
    public List<IAbitudine> calculateTodayHabits(LocalDate date);
    
    /**
     * Reset the count of all the habits which were not completed by the user in the days between the two dates.
     * 
     * @param date1 most recent access
     * @param date2 access of today
     */
    public void resetHabits(LocalDate date1, LocalDate date2); 
    
    /**
     * 
     * @return list of all habits
     */
    public List<IAbitudine> getHabits();
    
    /**
     * 
     * @param idHabit id of the habit
     */
    public void removeHabit(String idHabit);
    
    /**
     * Get the recording of habits in a year
     * chiave: days
     * valore: list of habits completed
     * 
     * @return days of monitoring
     */
    public Map<Integer, List<IAbitudine>> getYearRecords(int year);
    
    /**
     * Get the recording of habits in the last week
     * chiave: days
     * valore: list of habits completed
     * 
     * @return days of monitoring
     */
    public Map<Integer, List<IAbitudine>> getWeekRecords();
    
	public IAbitudine getHabit(String id);
    
}
