package main.model.habittracker.classi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.goalmanager.interfacce.IItem;
import main.model.habittracker.interfacce.IAbitudineScomponibile;

public class AbitudineScomponibile extends Abitudine implements IAbitudineScomponibile {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Oggetti che compongono questa abitudine
	 */
	private List<IItem> items = new ArrayList<>();

    //----------------------------- COSTRUTTORI --------------------------------
	public AbitudineScomponibile(String name, LocalDate startDate, List<DayOfWeek> days) {
		super(name, "", startDate, days);
	}

	public AbitudineScomponibile(String name, LocalDate startDate, List<DayOfWeek> days, String id) {
		super(name, "", startDate, days, id);
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 */
	public AbitudineScomponibile(String name, String description, LocalDate startDate, List<DayOfWeek> days) {
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
	public AbitudineScomponibile(String name, String description, LocalDate startDate, List<DayOfWeek> days, String id) {
		super(name, description, startDate, days, id);
	}

    //---------------------------- METODI PUBBLICI -----------------------------
	@Override
	public void addItem(IItem item) {
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
	public List<IItem> getItems() {
		return items;
	}
	
}
