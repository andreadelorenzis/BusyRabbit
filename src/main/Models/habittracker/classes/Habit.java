package main.Models.habittracker.classes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.UUID;

import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitDay;
import main.Models.habittracker.interfaces.IHabitTracker;

public class Habit implements IHabit {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Name of the habit
	 */
	private String name;
	
	/*
	 * Description of the habit
	 */
	private String description;
	
	/*
	 * Current count
	 */
	private int count = 0;
	
	/*
	 * Current record
	 */
	private int record = 0;
	
	/*
	 * When the habit will start to be active
	 */
	private LocalDate startDate;
	
	/*
	 * Last date in which the habit was completed
	 */
	private LocalDate dateOfLastCompletion = null;
	
	/*
	 * Contains a backup of dateOfLastCompletion in case the completion of the habit is reverted 
	 */
	private LocalDate temp;
	
	/*
	 * If the habit is completed or not
	 */
	private boolean isCompleted = false;
	
	/*
	 * The days of repetition
	 */
	private List<DayOfWeek> days = new ArrayList<>();
	
	/*
	 * Id of the habit
	 */
	private String id;
	
	/*
	 * Contains the info about the days when the habit has been completed in the monitored years
	 * key: year
	 * value: days where the habit was completed
	 */
	private Map<Integer, List<Integer>> yearRecords = new TreeMap<>();
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 */
	public Habit(String name, String description, LocalDate startDate, List<DayOfWeek> days) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.days = days;
		this.id = UUID.randomUUID().toString();
	}
	
	public Habit(String name, String description, LocalDate startDate, List<DayOfWeek> days, String id) {
		this(name, description, startDate, days);
		this.id = id;
	}

    //---------------------------- METODI PUBBLICI -----------------------------
	
	public void addHabitRecord(LocalDate date) {
		int year = date.getYear();
		int day = date.getDayOfYear();
		
		// add day to year records
		List<Integer> habitsInYear = new ArrayList<>();
		// if year does exist
		if(yearRecords.containsKey(year)) {
			// get existing year
			habitsInYear = yearRecords.get(year);
		}
		// if day doesn't exist, add the date to the year
		if(!habitsInYear.contains(day)) {
			habitsInYear.add(day);
		}
		
		// add or update the year in the map
		yearRecords.put(year, habitsInYear);
	}
	
	public void removeHabitRecord(LocalDate date) {
		int year = date.getYear();
		int day = date.getDayOfYear();
		
		// remove from year records
		List<Integer> habitsInYear = new ArrayList<>();
		if(yearRecords.containsKey(year)) {
			habitsInYear = yearRecords.get(year);
		}
		if(habitsInYear.contains(day)) {
			habitsInYear.remove((Object) day);
		}
		yearRecords.put(year, habitsInYear);
		
		// if no habits were completed in this year, remove the year from the map
		if(habitsInYear.size() == 0) {
			yearRecords.remove(year);
		}
	}

	@Override
	public void complete() {
		temp = dateOfLastCompletion;
		if(!isCompleted) {
			isCompleted = true;
			dateOfLastCompletion = LocalDate.now();
			count++;
			if(count > record) {
				record = count;
			}
			addHabitRecord(LocalDate.now());
		} else {
			isCompleted = false;
			dateOfLastCompletion = temp;
			if(record == count) {
				record--;
			}
			count--;
			removeHabitRecord(LocalDate.now());
		}
	}

	@Override
	public void setDays(List<DayOfWeek> days) {
		this.days = days;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void setRecord(int record) {
		this.record = record;
	}
	
	@Override
	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public int getRecord() {
		return record;
	}

	@Override
	public List<DayOfWeek> getDays() {
		return days;
	}

	@Override
	public boolean isCompleted() {
		return isCompleted;
	}

	@Override
	public LocalDate getDateOfLastCompletion() {
		return dateOfLastCompletion;
	}

	@Override
	public void setDateOfLastCompletion(LocalDate date) {
		this.dateOfLastCompletion = date;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public Map<Integer, List<Integer>> getYearsData() {
		return yearRecords;
	}

	@Override
	public List<Integer> getYearRecords(int year) {
		return yearRecords.get(year);
	}

	@Override
	public List<Integer> getWeekRecords() {
		List<Integer> weekRecords = new ArrayList<>();
		List<LocalDate> lastWeek = HabitTracker.getLastWeek();
		int year = lastWeek.get(lastWeek.size() - 1).getYear();
		for(LocalDate date : lastWeek) {
			int dayOfYear = date.getDayOfYear();
			List<Integer> yearRecords = getYearRecords(year);
			if(yearRecords != null) {
				if(yearRecords.contains(dayOfYear)) {
					weekRecords.add(dayOfYear);
				}
			}
		}
		return weekRecords;
	}

	@Override
	public Map<Integer, List<Integer>> getYearData() {
		return yearRecords;
	}
	
	@Override
    public boolean equals(Object obj) {
  	      if (obj == this) {
  	         return true;
  	      }
  	      if (!(obj instanceof Habit)) {
  	         return false;
  	      }
  	      IHabit o = (IHabit) obj;
  	      return this.id.equals(o.getId());
   }
}
