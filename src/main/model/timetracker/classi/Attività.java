package main.model.timetracker.classi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IProgetto;

public class Attività implements IAttività {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Nome dell'attività
	 */
	private String nome;
	
	/*
	 * Data in cui l'attività è stata svolta
	 */
	private LocalDate data;
	
	/*
	 * Ora di inizio dell'attività
	 */
	private LocalTime ora;
	
	/*
	 * Durata dell'attività
	 */
	private Long durata;
	
	/*
	 * Progetto a cui appartiene l'attività
	 */
	private IProgetto progetto;
	
	/*
	 * Identificativo dell'attività
	 */
	private String id;
	
    //----------------------------- COSTRUTTORI --------------------------------
	/**
	 * 
	 * @param nome
	 * @param data
	 * @param durata
	 */
	public Attività(String nome, LocalDate data, LocalTime ora, Long durata) {
		this.nome = nome;
		this.data = data;
		this.ora = ora;
		this.durata = durata;
		this.id = UUID.randomUUID().toString();
		this.progetto = TimeTracker.progettoDefault;
	}
	
	/**
	 * 
	 * @param nome
	 * @param data
	 * @param ora
	 * @param durata
	 * @param id
	 */
	public Attività(String nome, LocalDate data, LocalTime ora, Long durata, String id) {
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
	public Attività(String nome, LocalDate data, LocalTime ora, Long durata, IProgetto progetto) {
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
	public Attività(String nome, LocalDate data, LocalTime ora, Long durata, IProgetto progetto, String id) {
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
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	@Override 
	public void setOra(LocalTime ora) {
		this.ora = ora;
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
  	      if (!(obj instanceof Attività)) {
  	         return false;
  	      }
  	      IAttività o = (IAttività) obj;
  	      return this.id.equals(o.getId());
   }

}
