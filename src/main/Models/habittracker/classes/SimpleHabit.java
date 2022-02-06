package main.Models.habittracker.classes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.Models.habittracker.interfaces.ISimpleHabit;
import main.Models.goalmanager.interfaces.Item;

public class SimpleHabit extends Habit implements ISimpleHabit {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Items that compose this habit
	 */
	private List<Item> items = new ArrayList<>();

    //----------------------------- COSTRUTTORI --------------------------------
	public SimpleHabit(String name, LocalDate startDate, List<DayOfWeek> days) {
		super(name, "", startDate, days);
	}

	public SimpleHabit(String name, LocalDate startDate, List<DayOfWeek> days, String id) {
		super(name, "", startDate, days, id);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 */
	public SimpleHabit(String name, String description, LocalDate startDate, List<DayOfWeek> days) {
		super(name, description, startDate, days);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 * @param id
	 */
	public SimpleHabit(String name, String description, LocalDate startDate, List<DayOfWeek> days, String id) {
		super(name, description, startDate, days, id);
	}

    //---------------------------- METODI PUBBLICI -----------------------------
	@Override
	public void addItem(Item item) {
		items.add(item);
		item.setPadre(this);
	}

	@Override
	public void removeItem(String idItem) {
		items = items.stream()
					 .filter(i -> !(i.getId().equals(idItem)))
					 .collect(Collectors.toList());
	}

	@Override
	public List<Item> getItems() {
		return items;
	}
	
}
