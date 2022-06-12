package main.Models.habittracker.classes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import main.Models.habittracker.interfaces.IAbitudine;

public class GiornoAbitudine {

	/*
	 * Data di completamento.
	 */
    private LocalDate date;
    
    /*
     * Elenco di tutte le abitudini da completare in questa data.
     */
    private List<IAbitudine> allHabits;
    
    /*
     * Elenco di tutte le abitudini completate in questa data.
     */
    private List<IAbitudine> completedHabits;
    
	public LocalDate getDay() {
		return date;
	}

	public List<IAbitudine> getNumTotalHabits() {
		return allHabits;
	}

	public List<IAbitudine> getCompletedHabits() {
		return completedHabits;
	}

	public void addTotalHabits(List<IAbitudine> habits) {
		this.allHabits = habits;
	}

	public void addCompletedHabit(IAbitudine h) {
		this.completedHabits.add(h);
	}

	public void removeCompletedHabit(IAbitudine habitToRemove) {
		this.completedHabits = this.completedHabits.stream()
												   .filter(h -> !(h.getId().equals(habitToRemove.getId())))
												   .collect(Collectors.toList());
	}

}
