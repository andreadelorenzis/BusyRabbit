package main.Models.habittracker.classes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import main.Models.habittracker.interfaces.IHabit;

public class Abitudine implements IHabit {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Nome Abitudine
	 */
	private String name;
	
	/*
	 * Descrizione Abitudine
	 */
	private String description;
	
	/*
	 * Punteggio Attuale
	 */
	private int count = 0;
	
	/*
	 * Record Corrente
	 */
	private int record = 0;
	
	/*
	 * Date Inizio Abitudine 
	 */
	private LocalDate startDate;
	
	/*
	 * Ultima data in cui l'abito è stato completato
	 */
	private LocalDate dateOfLastCompletion = null;
	
	/*
	 * Contiene un backup della data dell'ultimo completamento nel caso in cui il completamento dell'abitudine venga ripristinato 
	 */
	private LocalDate temp;
	
	/*
	 * Se l'abitudine è completata o meno
	 */
	private boolean isCompleted = false;
	
	/*
	 * I giorni della ripetizione
	 */
	private List<DayOfWeek> days = new ArrayList<>();
	
	/*
	 * Id dell'abitudine
	 */
	private String id;
	
	/*
	 * Contiene le informazioni sui giorni in cui l'abitudine è stata completata negli anni monitorati
     * chiave: anno
     * valore: giorni in cui l'abitudine è stata completata
	 */
	private Map<Integer, List<Integer>> yearRecords = new TreeMap<>();
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param name
	 * @param description
	 * @param startDate
	 * @param days
	 */
	public Abitudine(String name, String description, LocalDate startDate, List<DayOfWeek> days) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.days = days;
		this.id = UUID.randomUUID().toString();
	}
	
	public Abitudine(String name, String description, LocalDate startDate, List<DayOfWeek> days, String id) {
		this(name, description, startDate, days);
		this.id = id;
	}

    //---------------------------- METODI PUBBLICI -----------------------------
	
	public void addHabitRecord(LocalDate date) {
		int year = date.getYear();
		int day = date.getDayOfYear();
		
		// aggiungere record giorno per anno
		List<Integer> habitsInYear = new ArrayList<>();
		// se anno non esiste 
		if(yearRecords.containsKey(year)) {
			// otiene l'anno esistente 
			habitsInYear = yearRecords.get(year);
		}
		// se il giorno non esiste, aggiungere la data all'anno
		if(!habitsInYear.contains(day)) {
			habitsInYear.add(day);
		}
		
		// aggiungi o aggiorna l'anno nella mappa
		yearRecords.put(year, habitsInYear);
	}
	
	public void removeHabitRecord(LocalDate date) {
		int year = date.getYear();
		int day = date.getDayOfYear();
		
		// rimuovere dai record dell'anno
		List<Integer> habitsInYear = new ArrayList<>();
		if(yearRecords.containsKey(year)) {
			habitsInYear = yearRecords.get(year);
		}
		if(habitsInYear.contains(day)) {
			habitsInYear.remove((Object) day);
		}
		yearRecords.put(year, habitsInYear);
		
		// se nessuna abitudine è stata completata in quest'anno, rimuovi l'anno dalla mappa
		if(habitsInYear.size() == 0) {
			yearRecords.remove(year);
		}
	}

	@Override
	public void complete() {
		temp = dateOfLastCompletion;
		if(!isCompleted) {
			isCompleted = true;
			dateOfLastCompletion = LocalDate.now();
			count++;
			if(count > record) {
				record = count;
			}
			addHabitRecord(LocalDate.now());
		} else {
			isCompleted = false;
			dateOfLastCompletion = temp;
			if(record == count) {
				record--;
			}
			count--;
			removeHabitRecord(LocalDate.now());
		}
	}

	@Override
	public void setDays(List<DayOfWeek> days) {
		this.days = days;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void setRecord(int record) {
		this.record = record;
	}
	
	@Override
	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public int getRecord() {
		return record;
	}

	@Override
	public List<DayOfWeek> getDays() {
		return days;
	}

	@Override
	public boolean isCompleted() {
		return isCompleted;
	}

	@Override
	public LocalDate getDateOfLastCompletion() {
		return dateOfLastCompletion;
	}

	@Override
	public void setDateOfLastCompletion(LocalDate date) {
		this.dateOfLastCompletion = date;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public Map<Integer, List<Integer>> getYearsData() {
		return yearRecords;
	}

	@Override
	public List<Integer> getYearRecords(int year) {
		return yearRecords.get(year);
	}

	@Override
	public List<Integer> getWeekRecords() {
		List<Integer> weekRecords = new ArrayList<>();
		List<LocalDate> lastWeek = AbitudineTracker.getLastWeek();
		int year = lastWeek.get(lastWeek.size() - 1).getYear();
		for(LocalDate date : lastWeek) {
			int dayOfYear = date.getDayOfYear();
			List<Integer> yearRecords = getYearRecords(year);
			if(yearRecords != null) {
				if(yearRecords.contains(dayOfYear)) {
					weekRecords.add(dayOfYear);
				}
			}
		}
		return weekRecords;
	}

	@Override
	public Map<Integer, List<Integer>> getYearData() {
		return yearRecords;
	}
	
	@Override
    public boolean equals(Object obj) {
  	      if (obj == this) {
  	         return true;
  	      }
  	      if (!(obj instanceof Abitudine)) {
  	         return false;
  	      }
  	      IHabit o = (IHabit) obj;
  	      return this.id.equals(o.getId());
   }
}
