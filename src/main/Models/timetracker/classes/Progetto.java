package main.Models.timetracker.classes;

import java.time.Month;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import main.Colori;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IProgetto;

public class Progetto implements IProgetto {
	
    //------------------------------- CAMPI ----------------------------------
	/*
	 * Nome del progetto
	 */
	private String nome;
	
	/*
	 * Colore del progetto
	 */
	private Colori colore;
	
	/*
	 * Gli anni in cui il progetto è stato monitorato
	 */
	private Map<Integer, AnnoProgetto> anniProgetto = new TreeMap<>();
	
	/*
	 * Identificativo del progetto
	 */
	private String id;
	
    //----------------------------- COSTRUTTORI --------------------------------
	public Progetto(String nome, Colori colore) {
		this.nome = nome;
		this.colore = colore;
		this.id = UUID.randomUUID().toString();
	}
	
	public Progetto(String nome, Colori colore, String id) {
		this(nome, colore);
		this.id = id;
	}
	
    //---------------------------- METODI PRIVATI ------------------------------
//	private MeseProgetto creaMese(IAttività a){
//		MeseProgetto m = new MeseProgetto(a.getData().getMonth());
//		m.aggiungiDurata(a);
//		return m;
//	}

    //---------------------------- METODI PUBBLICI -----------------------------
	@Override
	public void aggiungiDurata(IAttività a) {
		
		// aggiungo la durata al giusto AnnoProgetto
		int anno = a.getData().getYear();
		AnnoProgetto aProgetto;
		if(anniProgetto.containsKey(anno)) {
			aProgetto = anniProgetto.get(anno);
		} else {
			aProgetto = new AnnoProgetto(anno);
			anniProgetto.put(anno, aProgetto);
		}
		aProgetto.aggiungiDurata(a);
		
		
		// aggiungo la durata al giusto MeseProgetto
		int mese = a.getData().getMonthValue();
		MeseProgetto mProgetto;
		if(aProgetto.getMesiProgetto().containsKey(mese)) {
			mProgetto = aProgetto.getMesiProgetto().get(mese);
		} else {
			mProgetto = new MeseProgetto(a.getData().getMonth());
			aProgetto.getMesiProgetto().put(mese, mProgetto);
		}
		mProgetto.aggiungiDurata(a);
	}

	@Override
	public void eliminaDurata(IAttività a) {
		int anno = a.getData().getYear();
	
		AnnoProgetto aProgetto = anniProgetto.get(anno);
		if(aProgetto != null) {
			aProgetto.eliminaDurata(a);
		}
		
	}

	@Override
	public Map<Integer, Long> getAnnoProgetto(int anno) {
		if(anniProgetto.containsKey(anno)) {
			return anniProgetto.get(anno).getTempiAnno();
		}
		return new TreeMap<>();
	}

	@Override
	public Map<Integer, Long> getMeseProgetto(int anno, Month mese) {
		if(anniProgetto.containsKey(anno)) {
			AnnoProgetto aProgetto = anniProgetto.get(anno);
			if(aProgetto.getMesiProgetto().containsKey(mese.getValue())) {
				return aProgetto.getMesiProgetto().get(mese.getValue()).getTempiMese();
			}
		}
		return new TreeMap<>();
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public void setColore(Colori colore) {
		this.colore = colore;
	}

	@Override
	public Colori getColore() {
		return colore;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Progetto)) {
			return false;
		}
		Progetto other = (Progetto) o;
		if(this.getId().equals(other.getId())) {
			return true;
		}
		
		return false;
	}

}
