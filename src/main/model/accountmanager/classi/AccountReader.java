package main.model.accountmanager.classi;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import main.model.goalmanager.classi.AzioneScomponibile;
import main.model.goalmanager.classi.AzioneSessione;
import main.model.goalmanager.classi.Item;
import main.model.goalmanager.classi.ObiettivoAzione;
import main.model.goalmanager.classi.ObiettivoScomponibile;
import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IAzioneSessione;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.goalmanager.interfacce.IObiettivo;
import main.model.goalmanager.interfacce.IObiettivoAzione;
import main.model.goalmanager.interfacce.IObiettivoScomponibile;
import main.model.goalmanager.interfacce.IItem;
import main.model.habittracker.classi.AbitudineScomponibile;
import main.model.habittracker.classi.AbitudineSessione;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineScomponibile;
import main.model.habittracker.interfacce.IAbitudineSessione;
import main.model.habittracker.interfacce.IHabitTracker;
import main.model.timetracker.classi.Attività;
import main.model.timetracker.classi.Progetto;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITimeTracker;
import main.views.Colore;

/**
 * Classe per leggere i dati dal database
 */
public class AccountReader {
	//--------------------------- CAMPI ------------------------------
	
	/*
	 * Lista per contenere temporaneamente gli obiettivi letti
	 */
	private List<IObiettivo> obiettivi = new ArrayList<>();
	
	/*
	 * Lista per contenere temporaneamente le azioni lette
	 */
	private List<IAzione> azioni = new ArrayList<>();
    
	//---------------------------- METODI PRIVATI ------------------------------
	/**
	 * Ottiene il progetto specificato da quelli presenti in TimeTracker
	 */
	private IProgetto trovaProgetto(String idProgetto, ITimeTracker tt) {
		int i = 0;
		for(IProgetto p : tt.getProgetti()) {
			if(p.getId().equals(idProgetto)) {
				return tt.getProgetti().get(i);
			}
			i++;
		}
		return null;
	}
	
	//---------------------------- METODI PRIVATI ------------------------------
	/**
	 * Legge un ObiettivoScomponibile
	 */
	private void leggiObiettivoScomponibile(String[] params, IGoalManager gm) {
		boolean figlio = Boolean.parseBoolean(params[1]);
		String idPadre;
		String nome;
		String descrizione;
		int giorno;
		int mese;
		int anno;
		IObiettivoScomponibile nuovo;
		String id;
		if(figlio) {
			idPadre = params[2];
			nome = params[3];
			descrizione = params[4];
			giorno = Integer.parseInt(params[5]);
			mese = Integer.parseInt(params[6]);
			anno = Integer.parseInt(params[7]);
			id = params[8];
			// creo il sotto-obiettivo scomponibile e lo aggiungo alla lista
			nuovo = new ObiettivoScomponibile(nome, descrizione, LocalDate.of(anno, mese, giorno), id);
			obiettivi.add(nuovo);
			// lo aggiungo anche al obiettivo padre
			int i = 0;
			for(IObiettivo o : obiettivi) {
				if(o.getId().equals(idPadre)) {
					IObiettivoScomponibile os = (IObiettivoScomponibile) obiettivi.get(i);
					gm.aggiungiSottoObiettivo(os, nuovo);
				}
				i++;
			}
		} else {
			nome = params[2];
			descrizione = params[3];
			giorno = Integer.parseInt(params[4]);
			mese = Integer.parseInt(params[5]);
			anno = Integer.parseInt(params[6]);
			id = params[7];
			// creo l'obiettivo scomponibile e lo aggiungo alla lista
			nuovo = new ObiettivoScomponibile(nome, descrizione, LocalDate.of(anno, mese, giorno), id);
			obiettivi.add(nuovo);
			gm.aggiungiObiettivo(nuovo);
		}
	}
	
	/**
	 * Legge un ObiettivoAzione
	 */
	private void leggiObiettivoAzione(String[] params, IGoalManager gm) {
		boolean figlio = Boolean.parseBoolean(params[1]);
		String idPadre;
		String nome;
		String descrizione;
		int giorno;
		int mese;
		int anno;
		IObiettivoAzione nuovo;
		String id;
		if(figlio) {
			idPadre = params[2];
			nome = params[3];
			descrizione = params[4];
			giorno = Integer.parseInt(params[5]);
			mese = Integer.parseInt(params[6]);
			anno = Integer.parseInt(params[7]);
			int valore = Integer.parseInt(params[8]);
			String unità = params[9];
			id = params[10];
			// creo il sotto-obiettivo azione e lo aggiungo alla lista
			nuovo = new ObiettivoAzione(nome, descrizione, LocalDate.of(anno, mese, giorno), valore, unità, id);
			obiettivi.add(nuovo);
			// lo aggiungo anche al obiettivo padre
			int i = 0;
			for(IObiettivo o : obiettivi) {
				if(o.getId().equals(idPadre)) {
					IObiettivoScomponibile os = (IObiettivoScomponibile) obiettivi.get(i);
					gm.aggiungiSottoObiettivo(os, nuovo);
				}
				i++;
			}
		} else {
			nome = params[2];
			descrizione = params[3];
			giorno = Integer.parseInt(params[4]);
			mese = Integer.parseInt(params[5]);
			anno = Integer.parseInt(params[6]);
			int valore = Integer.parseInt(params[7]);
			String unità = params[8];
			id = params[9];
			// creo l'obiettivo scomponibile e lo aggiungo alla lista
			nuovo = new ObiettivoAzione(nome, descrizione, LocalDate.of(anno, mese, giorno), valore, unità, id);
			obiettivi.add(nuovo);
			gm.aggiungiObiettivo(nuovo);
		}
	}
	
	/**
	 * Legge un AzioneScomponibile
	 */
	private void leggiAzioneScomponibile(String[] params) {
		String idPadre = params[1];
		String nome = params[2];
		int incremento = Integer.parseInt(params[3]);
		int giorno = Integer.parseInt(params[4]);
		int mese = Integer.parseInt(params[5]);
		int anno = Integer.parseInt(params[6]);
		List<DayOfWeek> giorni = new ArrayList<>();
		if(!params[7].isBlank()) {
			String[] stringheGiorni = params[7].split("-");
			for(int i = 0; i < stringheGiorni.length; i++) {
				giorni.add(DayOfWeek.valueOf(stringheGiorni[i]));
			}
		}
		String id = params[8];
		IAzioneScomponibile nuova = new AzioneScomponibile(nome, incremento, LocalDate.of(anno, mese, giorno), giorni, id);
		azioni.add(nuova);
		// cerco l'obiettivo padre di questa azione
		int i = 0;
		for(IObiettivo o : obiettivi) {
			if(o.getId().equals(idPadre)) {
				IObiettivoAzione oa = (IObiettivoAzione) obiettivi.get(i);
				oa.collegaAzione(nuova);
			}
			i++;
		}
	}
	
	/**
	 * Legge un AzioneSessione
	 */
	private void leggiAzioneSessione(String[] params) {
		String idPadre = params[1];
		String nome = params[2];
		int incremento = Integer.parseInt(params[3]);
		int giorno = Integer.parseInt(params[4]);
		int mese = Integer.parseInt(params[5]);
		int anno = Integer.parseInt(params[6]);
		List<DayOfWeek> giorni = new ArrayList<>();
		String[] stringheGiorni = params[7].split("-");
		for(int i = 0; i < stringheGiorni.length; i++) {
			giorni.add(DayOfWeek.valueOf(stringheGiorni[i]));
		}
		int durata = Integer.parseInt(params[8]);
		String id = params[9];
		IAzioneSessione nuova = new AzioneSessione(nome, incremento, LocalDate.of(anno, mese, giorno), giorni, durata, id);
		// cerco l'obiettivo padre di questa azione
		int i = 0;
		for(IObiettivo o : obiettivi) {
			if(o.getId().equals(idPadre)) {
				IObiettivoAzione oa = (IObiettivoAzione) obiettivi.get(i);
				oa.collegaAzione(nuova);
			}
			i++;
		}
	}
	
	/**
	 * Legge un Item per AzioneScomponibile
	 */
	private void leggiItemAzione(String[] params) {
		String idPadre = params[1];
		String nome = params[2];
		String id = params[3];
		IItem nuovo = new Item(nome, id);
		// cerco l'azione scomponibile a cui appartiene questo item
		int i = 0;
		for(IAzione a : azioni) {
			if(a.getId().equals(idPadre)) {
				IAzioneScomponibile as = (IAzioneScomponibile) azioni.get(i);
				as.aggiungiItem(nuovo);
			}
			i++;
		}
	}
	
	/**
	 * Legge un Abitudine
	 */
	private void leggiAbitudine(String[] params, IHabitTracker ht) {
		String tipo = params[0];
		String nome = params[1];
		String descrizione = params[2];
		int giorno = Integer.parseInt(params[3]);
		int mese = Integer.parseInt(params[4]);
		int anno = Integer.parseInt(params[5]);
		List<DayOfWeek> giorni = new ArrayList<>();
		String[] stringheGiorni = params[6].split("-");
		for(int i = 0; i < stringheGiorni.length; i++) {
			giorni.add(DayOfWeek.valueOf(stringheGiorni[i]));
		}
		if(tipo.equals("abitudine-semplice")) {
			String id = params[7];
			IAbitudineScomponibile h = new AbitudineScomponibile(nome, descrizione, LocalDate.of(anno, mese, giorno), giorni, id);
			ht.addHabit(h);
		} else if(tipo.equals("abitudine-sessione")) {
			int durata = Integer.parseInt(params[7]);
			String id = params[8];
			IAbitudineSessione h = new AbitudineSessione(nome, descrizione, LocalDate.of(anno, mese, giorno), giorni, durata, id);
			ht.addHabit(h);
		}
	}
	
	/**
	 * Legge un Item per AbitudineScomponibile
	 */
	private void leggiItemAbitudine(String[] params, String nome, IHabitTracker ht) {
		String idPadre = params[1];
		nome = params[2];
		String id = params[3];
		IItem item = new Item(nome, id);
		int i = 0;
		for(IAbitudine h : ht.getHabits()) {
			if(h.getId().equals(idPadre)) {
				IAbitudineScomponibile sh = (IAbitudineScomponibile) ht.getHabits().get(i);
				sh.addItem(item);
			}
			i++;
		}
	}
	
	//--------------------------- METODI PUBBLICI ------------------------------
	/**
	 * Legge tutti i progetti dal file di testo
	 */
    public void leggiProgetti(BufferedReader reader, ITimeTracker tt) throws IOException {
		String line = reader.readLine();
		while(!line.equals("---attivita---")) {
			String[] params = line.split(",");
			String nome = params[0];
			Colore colore = Colore.valueOf(params[1]);
			String id = params[2];
			IProgetto p = new Progetto(nome, colore, id);
			tt.aggiungiProgetto(p);
			line = reader.readLine();
		}
	}
	
    /**
	 * Legge tutti le attività dal file di testo
	 */
	public void leggiAttività(BufferedReader reader, ITimeTracker tt) throws IOException {
		String line = reader.readLine();
		while(!(line.equals("---obiettivi---"))) {	
			String[] params = line.split(",");
			String nome = params[0];
			int giorno = Integer.parseInt(params[1]);
			int mese = Integer.parseInt(params[2]);
			int anno = Integer.parseInt(params[3]);
			int ore = Integer.parseInt(params[4]);
			int minuti = Integer.parseInt(params[5]);
			long durata = Long.parseLong(params[6]);
			String idProgetto = params[7];
			String id = params[8];
			IProgetto progetto;
			IAttività a;
			if(!idProgetto.equals("null")) {
				progetto = trovaProgetto(idProgetto, tt);
				a = new Attività(nome, LocalDate.of(anno, mese, giorno), LocalTime.of(ore, minuti), durata, progetto, id);
			} else {
				a = new Attività(nome, LocalDate.of(anno, mese, giorno), LocalTime.of(ore, minuti), durata, id);
			}
			tt.aggiungiAttività(a);
			line = reader.readLine();
		}
	}
	
	/**
	 * Legge tutti gli obiettivi dal file di testo
	 */
	public void leggiObiettivi(BufferedReader reader, IGoalManager gm) throws IOException {
		String line = reader.readLine();
		while(!(line.equals("---abitudini---"))) {
			// lettura tipo entità
			String[] params = line.split(",");
			String tipo = params[0];
			
			if(tipo.equals("obiettivo-scomponibile")) {
				this.leggiObiettivoScomponibile(params, gm);
			} else if(tipo.equals("obiettivo-azione")) {
				this.leggiObiettivoAzione(params, gm);
			} else if(tipo.equals("azione-scomponibile")) {
				this.leggiAzioneScomponibile(params);
			} else if(tipo.equals("azione-sessione")) {
				this.leggiAzioneSessione(params);
			} else if(tipo.equals("item-azione")) {
				this.leggiItemAzione(params);
			}
			line = reader.readLine();
		}
	}
	
	/**
	 * Legge tutte le abitudini dal file di testo
	 */
	public void leggiAbitudini(BufferedReader reader, IHabitTracker ht) throws IOException {
		String line = reader.readLine();
		while(!(line.equals("---storico-abitudini---"))) {
		
			// lettura tipo entità
			String[] params = line.split(",");
			String tipo = params[0];
			String nome = params[1];
			
			if(tipo.equals("abitudine-semplice") || (tipo.equals("abitudine-sessione"))) {
				this.leggiAbitudine(params, ht);
			} else if(tipo.equals("item-abitudine")) {
				this.leggiItemAbitudine(params, nome, ht);
			}
			line = reader.readLine();
		}
	}
	
	/**
	 * Legge le informazioni di completamento storiche delle abitudini dal file di testo
	 */
	public void leggiStoricoAbitudini(BufferedReader reader, IHabitTracker ht) throws IOException {
		String line = reader.readLine();
		while(line != null) {
			String[] params = line.split(",");
			String idAbitudine = params[0];
			IAbitudine abitudine = ht.getHabit(idAbitudine);
			
			// itero gli anni
			for(int i = 1; i < params.length; i++) {
				String datiAnno = params[i];
				String[] arrayGiorni = datiAnno.split("-");
				int anno = Integer.parseInt(arrayGiorni[0]);
				
				// itero i giorni 
				for(int j = 1; j < arrayGiorni.length; j++) {
					int giorno = Integer.parseInt(arrayGiorni[j]);
					LocalDate data = LocalDate.ofYearDay(anno, giorno);
					abitudine.addHabitRecord(data);
				}
			}
			line = reader.readLine();
		}
	}
    
}
