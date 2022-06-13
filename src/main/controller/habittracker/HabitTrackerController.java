package main.controller.habittracker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import main.model.habittracker.classi.HabitTracker;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineTracker;
import main.views.IView;
import main.views.habittracker.interfacce.IHabitTrackerView;

public class HabitTrackerController implements IHabitTrackerController {
    
	private IHabitTrackerView view;
	
	private IAbitudineTracker ht = HabitTracker.getInstance();;
	
	@Override
	public void setView(IView v) {
		this.view = (IHabitTrackerView) v;
	}

	@Override
	public IView getView() {
		return (IView) this.view;
	}
	
	@Override
	public void aggiungiAbitudine(IAbitudine h) {
		ht.addHabit(h);
	}
	
	@Override
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
	
	@Override
	public void eliminaAbitudine(IAbitudine h) {
		ht.removeHabit(h.getId());
	}
	
	@Override
	public void completaAbitudine(IAbitudine h) {
		h.complete();
	}
	
}
