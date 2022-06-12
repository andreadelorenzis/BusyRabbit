package main.controller.habittracker;

import main.model.habittracker.interfacce.IAbitudine;

public interface HabitTrackerController {
	public void aggiungiAbitudine(IAbitudine h);
	
	public void modificaAbitudine(IAbitudine h1, IAbitudine h2);
	
	public void eliminaAbitudine(IAbitudine h);
	
	public void completaAbitudine(IAbitudine h);
}
