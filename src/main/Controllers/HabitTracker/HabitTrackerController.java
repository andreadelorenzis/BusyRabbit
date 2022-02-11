package main.Controllers.HabitTracker;

import main.Models.habittracker.interfaces.IHabit;

public interface HabitTrackerController {
	public void aggiungiAbitudine(IHabit h);
	
	public void modificaAbitudine(IHabit h1, IHabit h2);
	
	public void eliminaAbitudine(IHabit h);
	
	public void completaAbitudine(IHabit h);
}
