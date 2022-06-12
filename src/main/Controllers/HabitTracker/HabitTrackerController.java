package main.Controllers.HabitTracker;

import main.Models.habittracker.interfaces.IAbitudine;

public interface HabitTrackerController {
	public void aggiungiAbitudine(IAbitudine h);
	
	public void modificaAbitudine(IAbitudine h1, IAbitudine h2);
	
	public void eliminaAbitudine(IAbitudine h);
	
	public void completaAbitudine(IAbitudine h);
}
