package main.model.habittracker.classi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IHabitTracker;

public class HabitTracker implements IHabitTracker {

	/*
	 * Elenco di tutte le abitudini
	 */
	private List<IAbitudine> habits = new ArrayList<>();
	
	private static HabitTracker habitTracker = null;
	
	private HabitTracker() {
		
	}
	
	public static HabitTracker getInstance() {
		if(habitTracker == null) {
			habitTracker = new HabitTracker();
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
			if(h.getDays().contains(date.getDayOfWeek()) &&
		      (h.getStartDate().isBefore(date) || h.getStartDate().isEqual(date))) {
				habitsToday.add(h);
			  }
		}
		return habitsToday;
	}

	@Override
	public void resetHabits(LocalDate date1, LocalDate date2) {
		for(int i = 0; i < habits.size(); i++) {
			IAbitudine h = habits.get(i);
			//se l'abitudine non è mai stata completata, il conteggio è già impostato a 0
			if(h.getDateOfLastCompletion() != null) {
				// ottenere le date comprese tra la data dell'ultimo completamento dell'abito e oggi
				Stream<LocalDate> datesBetween = h.getDateOfLastCompletion().datesUntil(date2);
				
				//per ogni giorno, se l'abitudine si è presentata (non oggi o data dell'ultimo completamento), ripristinarla
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
	
	@Override
	public IAbitudine getHabit(String id) {
		IAbitudine habit = null;
		for(IAbitudine h : habits) {
			if(h.getId().equals(id)) {
				habit = h;
			}
		}
		return habit;
	}
	
	public static List<LocalDate> getLastWeek() {
		LocalDate weekBeforeToday = LocalDate.now().minusDays(7);
		return weekBeforeToday.datesUntil(LocalDate.now().plusDays(1))
				.collect(Collectors.toList());
	}
    
}
