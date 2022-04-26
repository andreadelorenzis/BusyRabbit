package main.Controllers.HabitTracker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import main.Views.HabitTracker.interfaces.HabitTrackerView;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;

public class HabitTrackerControllerImpl implements HabitTrackerController {
    
	private HabitTrackerView view;
	
	private IHabitTracker ht;
	
	public HabitTrackerControllerImpl(HabitTrackerView view) {
		this.view = view;
		ht = HabitTracker.getInstance();
	}
	
	public void aggiungiAbitudine(IHabit h) {
		ht.addHabit(h);
	}
	
	public void modificaAbitudine(IHabit h1, IHabit h2) {
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
	
	public void eliminaAbitudine(IHabit h) {
		ht.removeHabit(h.getId());
	}
	
	public void completaAbitudine(IHabit h) {
		h.complete();
	}
	
}
