package main.controller.habittracker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import main.model.habittracker.classi.AbitudineTracker;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineTracker;
import main.views.HabitTracker.interfaces.HabitTrackerView;

public class HabitTrackerControllerImpl implements HabitTrackerController {
    
	private HabitTrackerView view;
	
	private IAbitudineTracker ht;
	
	public HabitTrackerControllerImpl(HabitTrackerView view) {
		this.view = view;
		ht = AbitudineTracker.getInstance();
	}
	
	public void aggiungiAbitudine(IAbitudine h) {
		ht.addHabit(h);
	}
	
	public void modificaAbitudine(IAbitudine h1, IAbitudine h2) {
		String nome = h2.getName();
		String descrizione = h2.getDescription();
		LocalDate data = h2.getStartDate();
		List<DayOfWeek> giorni = h2.getDays();
		String id = h2.getId();
		
		h1.setName(nome);
		h1.setDescription(descrizione);
		h1.setStartDate(data);
		h1.setDays(giorni);
	}
	
	public void eliminaAbitudine(IAbitudine h) {
		ht.removeHabit(h.getId());
	}
	
	public void completaAbitudine(IAbitudine h) {
		h.complete();
	}
	
}
