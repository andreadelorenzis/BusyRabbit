package main.views.habittracker.interfacce;

import main.model.habittracker.interfacce.IAbitudine;
import main.views.IView;

public interface IHabitTrackerView extends IView {
	
	public void visualizzaAbitudiniGiornaliere();
	
	public void visualizzaTotaleAbitudini();
	
	public void aggiornaAbitudini();
	
	public void visualizzaInfoAbitudine(IAbitudine abitudine);
	
}
