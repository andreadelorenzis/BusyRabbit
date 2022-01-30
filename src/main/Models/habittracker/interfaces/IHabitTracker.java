package main.Models.habittracker.interfaces;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import main.Giorno;

/**
 * 
 * Tool to track habits of the user.
 *
 */
public interface IHabitTracker {
    
    /**
     * 
     * @param habit
     */
    public void addHabit(IHabit habit);
    
    /**
     * Calculate habits to do in the specified date
     * 
     * @param date of today
     * @return list of today's habits
     */
    public List<IHabit> calculateTodayHabits(LocalDate date);
    
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
    public List<IHabit> getHabits();
    
    /**
     * 
     * @param idHabit id of the habit
     */
    public void removeHabit(String idHabit);
    
}
