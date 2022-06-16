package main.views.habittracker.interfacce;

import main.model.habittracker.interfacce.IAbitudine;
import main.views.IView;

/**
 * View a cui è collegato l'HabitTrackerView
 */
public interface IHabitTrackerView extends IView {
	
	public void visualizzaAbitudiniGiornaliere();
	
	public void visualizzaTotaleAbitudini();
	
	public void aggiornaAbitudini();
	
	public void visualizzaInfoAbitudine(IAbitudine abitudine);
	
	public void successo(String messaggio);
}
