package main.model.habittracker.interfacce;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import main.model.habittracker.classi.HabitTracker;


/**
 * 
 * Habit that repeat in time.
 *
 */
public interface IAbitudine {
	
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
	 * @param date of start of the habit
	 */
	public void setStartDate(LocalDate date);
	
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
	
	/**
	 * Ottiene le informazioni di completamente delle abitudini in un dato anno
	 * 
	 * @param year
	 */
	public List<Integer> getYearRecords(int year);
	
	/**
	 * Ottiene le informazioni di completamente delle abitudini in nell'ultima settimana
	 */
	public List<Integer> getWeekRecords();
	
	/**
	 * Aggiunge completamento abitudine allo storico
	 * 
	 * @param date
	 */
	public void addHabitRecord(LocalDate date);
	
	/**
	 * Rimuove completamento abitudine allo storico
	 * 
	 * @param date
	 */
	public void removeHabitRecord(LocalDate date);
	
	public Map<Integer, List<Integer>> getYearData();
}
