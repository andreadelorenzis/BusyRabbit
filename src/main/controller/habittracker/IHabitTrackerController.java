package main.controller.habittracker;

import main.controller.IController;
import main.model.habittracker.interfacce.IAbitudine;

public interface IHabitTrackerController extends IController {
	public void aggiungiAbitudine(IAbitudine h);
	
	public void modificaAbitudine(IAbitudine h1, IAbitudine h2);
	
	public void eliminaAbitudine(IAbitudine h);
	
	public void completaAbitudine(IAbitudine h);
}
