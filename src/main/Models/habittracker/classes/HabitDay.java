package main.Models.habittracker.classes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import main.Models.habittracker.interfaces.IHabit;

public class HabitDay {

	/*
	 * Date of completion.
	 */
    private LocalDate date;
    
    /*
     * List of all habits to complete in this date. 
     */
    private List<IHabit> allHabits;
    
    /*
     * List of all habits completed in this date.
     */
    private List<IHabit> completedHabits;
    
	public LocalDate getDay() {
		return date;
	}

	public List<IHabit> getNumTotalHabits() {
		return allHabits;
	}

	public List<IHabit> getCompletedHabits() {
		return completedHabits;
	}

	public void addTotalHabits(List<IHabit> habits) {
		this.allHabits = habits;
	}

	public void addCompletedHabit(IHabit h) {
		this.completedHabits.add(h);
	}

	public void removeCompletedHabit(IHabit habitToRemove) {
		this.completedHabits = this.completedHabits.stream()
												   .filter(h -> !(h.getId().equals(habitToRemove.getId())))
												   .collect(Collectors.toList());
	}

}
