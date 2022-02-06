package main.Models.habittracker.classes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitDay;

public class HabitDay implements IHabitDay {

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
    
	@Override
	public LocalDate getDay() {
		return date;
	}

	@Override
	public List<IHabit> getNumTotalHabits() {
		return allHabits;
	}

	@Override
	public List<IHabit> getCompletedHabits() {
		return completedHabits;
	}

	@Override
	public void addTotalHabits(List<IHabit> habits) {
		this.allHabits = habits;
	}

	@Override
	public void addCompletedHabit(IHabit h) {
		this.completedHabits.add(h);
	}

	@Override
	public void removeCompletedHabit(IHabit habitToRemove) {
		this.completedHabits = this.completedHabits.stream()
												   .filter(h -> !(h.getId().equals(habitToRemove.getId())))
												   .collect(Collectors.toList());
	}

}
