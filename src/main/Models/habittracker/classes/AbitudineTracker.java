package main.Models.habittracker.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import main.Models.habittracker.interfaces.IAbitudine;
import main.Models.habittracker.interfaces.IAbitudineTracker;

public class AbitudineTracker implements IAbitudineTracker {

	/*
	 * Elenco di tutte le abitudini
	 */
	private List<IAbitudine> habits = new ArrayList<>();
	
	private static AbitudineTracker habitTracker = null;
	
	private AbitudineTracker() {
		
	}
	
	public static AbitudineTracker getInstance() {
		if(habitTracker == null) {
			habitTracker = new AbitudineTracker();
		}
		return habitTracker;
	}
	
	@Override
	public void addHabit(IAbitudine habit) {
		habits.add(habit);
	}

	@Override
	public List<IAbitudine> calculateTodayHabits(LocalDate date) {
		List<IAbitudine> habitsToday = new ArrayList<>();
		for(IAbitudine h : habits) {
			if(h.getDays().contains(date.getDayOfWeek())) {
				habitsToday.add(h);
			  }
		}
		return habitsToday;
	}

	@Override
	public void resetHabits(LocalDate date1, LocalDate date2) {
		for(int i = 0; i < habits.size(); i++) {
			IAbitudine h = habits.get(i);
			//se l'abitudine non � mai stata completata, il conteggio � gi� impostato a 0
			if(h.getDateOfLastCompletion() != null) {
				// ottenere le date comprese tra la data dell'ultimo completamento dell'abito e oggi
				Stream<LocalDate> datesBetween = h.getDateOfLastCompletion().datesUntil(date2);
				
				//per ogni giorno, se l'abitudine si � presentata (non oggi o data dell'ultimo completamento), ripristinarla
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
	public List<IAbitudine> getHabits() {
		return habits;
	}

	@Override
	public void removeHabit(String idHabit) {
		habits = habits.stream()
					   .filter(h -> !(h.getId().equals(idHabit)))
					   .collect(Collectors.toList());
	}

	@Override
	public Map<Integer, List<IAbitudine>> getYearRecords(int year) {
		Map<Integer, List<IAbitudine>> yearMap = new TreeMap<>();
		for(int i = 1; i <= 365; i++) {
			List<IAbitudine> habitsCompleted = new ArrayList<>();	
			for(IAbitudine h : this.habits) {
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
	public Map<Integer, List<IAbitudine>> getWeekRecords() {
		Map<Integer, List<IAbitudine>> weekMap = new TreeMap<>();
		for(LocalDate date : getLastWeek()) {
			List<IAbitudine> habitsCompleted = new ArrayList<>();
			for(IAbitudine h : this.habits) {
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
	
	public IAbitudine getHabit(String id) {
		IAbitudine habit = null;
		for(IAbitudine h : habits) {
			if(h.getId().equals(id)) {
				habit = h;
			}
		}
		return habit;
	}
    
}
