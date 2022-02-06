package main.Models.habittracker.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;

public class HabitTracker implements IHabitTracker {

    //------------------------------- CAMPI ----------------------------------
	/*
	 * List of all habits
	 */
	private List<IHabit> habits = new ArrayList<>();
	
    //---------------------------- METODI PUBBLICI -----------------------------
	@Override
	public void addHabit(IHabit habit) {
		habits.add(habit);
	}

	@Override
	public List<IHabit> calculateTodayHabits(LocalDate date) {
		List<IHabit> habitsToday = new ArrayList<>();
		for(IHabit h : habits) {
			if(h.getDays().contains(date.getDayOfWeek())) {
				habitsToday.add(h);
			  }
		}
		return habitsToday;
	}

	@Override
	public void resetHabits(LocalDate date1, LocalDate date2) {
		for(int i = 0; i < habits.size(); i++) {
			IHabit h = habits.get(i);
			// if the habit was never completed, the count is already set to 0
			if(h.getDateOfLastCompletion() != null) {
				// get the dates between the date of last completion of the habit and today
				Stream<LocalDate> datesBetween = h.getDateOfLastCompletion().datesUntil(date2);
				
				// for every day, if the habit showed up (not today o date of last completion), reset it
				datesBetween.forEach(date -> {
					if(!date.isEqual(h.getDateOfLastCompletion()) && !date.isEqual(date2)) {
						if(h.getDays().contains(date.getDayOfWeek())) {
							h.setCount(0);
						}
					}
				});
			}
		}
	}

	@Override
	public List<IHabit> getHabits() {
		return habits;
	}

	@Override
	public void removeHabit(String idHabit) {
		habits = habits.stream()
					   .filter(h -> !(h.getId().equals(idHabit)))
					   .collect(Collectors.toList());
	}

	@Override
	public Map<Integer, List<IHabit>> getYearRecords(int year) {
		Map<Integer, List<IHabit>> yearMap = new TreeMap<>();
		for(int i = 1; i <= 365; i++) {
			List<IHabit> habitsCompleted = new ArrayList<>();	
			for(IHabit h : this.habits) {
				List<Integer> habitRecords = h.getYearRecords(year);
				if(habitRecords != null) {
					if(habitRecords.contains(i)) {
						habitsCompleted.add(h);
					}
				}
			}
			yearMap.put(i, habitsCompleted);
		}
		return yearMap;
	}

	@Override
	public Map<Integer, List<IHabit>> getWeekRecords() {
		Map<Integer, List<IHabit>> weekMap = new TreeMap<>();
		for(LocalDate date : getLastWeek()) {
			List<IHabit> habitsCompleted = new ArrayList<>();
			for(IHabit h : this.habits) {
				List<Integer> habitRecords = h.getWeekRecords();
				if(habitRecords.contains(date.getDayOfYear())) {
					habitsCompleted.add(h);
				}
			}
			weekMap.put(date.getDayOfYear(), habitsCompleted);
		}
		return weekMap;
	}
	
	public static List<LocalDate> getLastWeek() {
		LocalDate weekBeforeToday = LocalDate.now().minusDays(7);
		return weekBeforeToday.datesUntil(LocalDate.now().plusDays(1))
			   .collect(Collectors.toList());
	}
	
	public IHabit getHabit(String id) {
		IHabit habit = null;
		for(IHabit h : habits) {
			if(h.getId().equals(id)) {
				habit = h;
			}
		}
		return habit;
	}
    
}
