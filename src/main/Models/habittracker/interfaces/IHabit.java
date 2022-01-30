package main.Models.habittracker.interfaces;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import main.Giorno;

/**
 * 
 * Habit that repeat in time.
 *
 */
public interface IHabit {
	
	/**
	 * Complete this habit
	 */
	public void complete();
	
	/**
	 * 
	 * @param days of repetition of the habit
	 */
	public void setDays(List<DayOfWeek> days);
	
	/**
	 * 
	 * @param name of the habit
	 */
	public void setName(String name);
	
	/**
	 * 
	 * @param description of the habit
	 */
	public void setDescription(String description);
	
	/**
	 * 
	 * @param count of the habit
	 */
	public void setCount(int count);
	
	/**
	 * 
	 * @param record count of the habit
	 */
	public void setRecord(int record);
	
	/**
	 * 
	 * @return name
	 */
	public String getName();
	
	/**
	 * 
	 * @return description
	 */
	public String getDescription();
	
	/**
	 * 
	 * @return count
	 */
	public int getCount();
	
	/**
	 * 
	 * @return record
	 */
	public int getRecord();
	
	/**
	 * 
	 * @return days of repetition
	 */
	public List<DayOfWeek> getDays();
	
	/**
	 * 
	 * @return if the habit is complete or not
	 */
	public boolean isCompleted();
	
	/**
	 * 
	 * @return the date of the last completion of this habit
	 */
	public LocalDate getDateOfLastCompletion();
	
	/**
	 * 
	 * @return the start date of this habit
	 */
	public LocalDate getStartDate();
	
	/**
	 * 
	 * @param date of the last completion of this habit
	 */
	public void setDateOfLastCompletion(LocalDate date);
	
	/**
	 * 
	 * @return id
	 */
	public String getId();
	
}
