package main.Models.timetracker.classes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

import main.Models.goalmanager.classes.Obiettivo;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.timetracker.interfaces.IAttivit�;
import main.Models.timetracker.interfaces.IProgetto;
import main.Views.Colore;

public class Attivit� implements IAttivit� {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Nome dell'attivit�
	 */
	private String nome;
	
	/*
	 * Data in cui l'attivit� � stata svolta
	 */
	private LocalDate data;
	
	/*
	 * Ora di inizio dell'attivit�
	 */
	private LocalTime ora;
	
	/*
	 * Durata dell'attivit�
	 */
	private Long durata;
	
	/*
	 * Progetto a cui appartiene l'attivit�
	 */
	private IProgetto progetto;
	
	/*
	 * Identificativo dell'attivit�
	 */
	private String id;
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param nome
	 * @param data
	 * @param durata
	 */
	public Attivit�(String nome, LocalDate data, LocalTime ora, Long durata) {
		this.nome = nome;
		this.data = data;
		this.ora = ora;
		this.durata = durata;
		this.id = UUID.randomUUID().toString();
		this.progetto = TimeTracker.progettoDefault;
	}
	
	public Attivit�(String nome, LocalDate data, LocalTime ora, Long durata, String id) {
		this(nome, data, ora, durata);
		this.id = id;
	}
	
	/**
	 * 
	 * @param nome
	 * @param data
	 * @param durata
	 * @param progetto
	 */
	public Attivit�(String nome, LocalDate data, LocalTime ora, Long durata, IProgetto progetto) {
		this(nome, data, ora, durata);
		this.progetto = progetto;
	}
	
	/**
	 * 
	 * @param nome
	 * @param data
	 * @param durata
	 * @param progetto
	 * @param id
	 */
	public Attivit�(String nome, LocalDate data, LocalTime ora, Long durata, IProgetto progetto, String id) {
		this(nome, data, ora, durata, progetto);
		this.id = id;
	}

    //-------------------------- METODI PUBBLICI ----------------------------
	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public LocalDate getData() {
		return data;
	}
	
	@Override 
	public LocalTime getOraInizio() {
		return ora;
	}
	
	@Override
	public LocalTime getOraFine() {
		return ora.plus(Duration.ofSeconds(durata));
	}

	@Override
	public long getDurata() {
		return durata;
	}

	@Override
	public void setDurata(long durata) {
		this.durata = durata;
	}

	@Override
	public void setProgettoPadre(IProgetto progetto) {
		this.progetto = progetto;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public IProgetto getProgetto() {
		return progetto;
	}
	
	@Override
    public boolean equals(Object obj) {
  	      if (obj == this) {
  	         return true;
  	      }
  	      if (!(obj instanceof Attivit�)) {
  	         return false;
  	      }
  	      IAttivit� o = (IAttivit�) obj;
  	      return this.id.equals(o.getId());
   }

}
