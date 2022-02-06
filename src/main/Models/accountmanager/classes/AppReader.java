/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Models.accountmanager.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.Colori;
import main.Giorno;
import main.Models.AccountManager.interfaces.IApp;
import main.Models.goalmanager.classes.AzioneScomponibile;
import main.Models.goalmanager.classes.AzioneSessione;
import main.Models.goalmanager.classes.GoalManager;
import main.Models.goalmanager.classes.ItemImpl;
import main.Models.goalmanager.classes.ObiettivoAzione;
import main.Models.goalmanager.classes.ObiettivoScomponibile;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IAzioneScomponibile;
import main.Models.goalmanager.interfaces.IAzioneSessione;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;
import main.Models.goalmanager.interfaces.Item;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.habittracker.classes.SessionHabit;
import main.Models.habittracker.classes.SimpleHabit;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.habittracker.interfaces.ISessionHabit;
import main.Models.habittracker.interfaces.ISimpleHabit;
import main.Models.timetracker.classes.Attivit�;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TimeTracker;
import main.Models.timetracker.interfaces.IAttivit�;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;

/**
 *
 * @author Mars_DB
 */
public class AppReader {
    
    	public void leggiProgetti(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while(!line.equals("---attivita---")) {
			String[] params = line.split(",");
			String nome = params[0];
			Colori colore = Colori.valueOf(params[1]);
			String id = params[2];
			IProgetto p = new Progetto(nome, colore, id);
			tt.aggiungiProgetto(p);
			line = reader.readLine();
		}
	}
	
	public void leggiAttivit�(BufferedReader reader) throws IOException {
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
			IAttivit� a;
			if(!idProgetto.equals("null")) {
				progetto = trovaProgetto(idProgetto);
				a = new Attivit�(nome, LocalDate.of(anno, mese, giorno), LocalTime.of(ore, minuti), durata, progetto, id);
			} else {
				a = new Attivit�(nome, LocalDate.of(anno, mese, giorno), LocalTime.of(ore, minuti), durata, id);
			}
			tt.aggiungiAttivit�(a);
			line = reader.readLine();
		}
	}
	
	public void leggiObiettivi(BufferedReader reader) throws IOException {
		List<IObiettivo> obiettivi = new ArrayList<>();
		List<IAzione> azioni = new ArrayList<>();
		String line = reader.readLine();
		while(!(line.equals("---abitudini---"))) {
			String[] params = line.split(",");
			String tipo = params[0];
			
			if(tipo.equals("obiettivo-scomponibile")) {
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
					// lo aggiungo anche al obiettivo padre, gi� aggiunto nella lista
					int i = 0;
					for(IObiettivo o : obiettivi) {
						if(o.getId().equals(idPadre)) {
							IObiettivoScomponibile os = (IObiettivoScomponibile) obiettivi.get(i);
							os.aggiungiSottoObiettivo(nuovo);
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
			} else if(tipo.equals("obiettivo-azione")) {
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
					String unit� = params[9];
					id = params[10];
					// creo il sotto-obiettivo azione e lo aggiungo alla lista
					nuovo = new ObiettivoAzione(nome, descrizione, LocalDate.of(anno, mese, giorno), valore, unit�, id);
					obiettivi.add(nuovo);
					// lo aggiungo anche al obiettivo padre, gi� aggiunto nella lista
					int i = 0;
					for(IObiettivo o : obiettivi) {
						if(o.getId().equals(idPadre)) {
							IObiettivoScomponibile os = (IObiettivoScomponibile) obiettivi.get(i);
							os.aggiungiSottoObiettivo(nuovo);
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
					String unit� = params[8];
					id = params[9];
					// creo l'obiettivo scomponibile e lo aggiungo alla lista
					nuovo = new ObiettivoAzione(nome, descrizione, LocalDate.of(anno, mese, giorno), valore, unit�, id);
					obiettivi.add(nuovo);
					gm.aggiungiObiettivo(nuovo);
				}
			} else if(tipo.equals("azione-scomponibile")) {
				String idPadre = params[1];
				String nome = params[2];
				int incremento = Integer.parseInt(params[3]);
				int giorno = Integer.parseInt(params[4]);
				int mese = Integer.parseInt(params[5]);
				int anno = Integer.parseInt(params[6]);
				List<Giorno> giorni = new ArrayList<>();
				if(!params[7].isBlank()) {
					String[] stringheGiorni = params[7].split("-");
					for(int i = 0; i < stringheGiorni.length; i++) {
						giorni.add(Giorno.valueOf(stringheGiorni[i]));
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
				
			} else if(tipo.equals("azione-sessione")) {
				String idPadre = params[1];
				String nome = params[2];
				int incremento = Integer.parseInt(params[3]);
				int giorno = Integer.parseInt(params[4]);
				int mese = Integer.parseInt(params[5]);
				int anno = Integer.parseInt(params[6]);
				List<Giorno> giorni = new ArrayList<>();
				String[] stringheGiorni = params[7].split("-");
				for(int i = 0; i < stringheGiorni.length; i++) {
					giorni.add(Giorno.valueOf(stringheGiorni[i]));
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
			} else if(tipo.equals("item-azione")) {
				String idPadre = params[1];
				String nome = params[2];
				String id = params[3];
				Item nuovo = new ItemImpl(nome, id);
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
			line = reader.readLine();
		}
	}
	
	public void leggiAbitudini(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while(!(line.equals("---storico-abitudini---"))) {
		
			String[] params = line.split(",");
			String tipo = params[0];
			String nome = params[1];
			
			if(tipo.equals("abitudine-semplice") || (tipo.equals("abitudine-sessione"))) {
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
					ISimpleHabit h = new SimpleHabit(nome, descrizione, LocalDate.of(anno, mese, giorno), giorni, id);
					ht.addHabit(h);
				} else if(tipo.equals("abitudine-sessione")) {
					int durata = Integer.parseInt(params[7]);
					String id = params[8];
					ISessionHabit h = new SessionHabit(nome, descrizione, LocalDate.of(anno, mese, giorno), giorni, durata, id);
					ht.addHabit(h);
				} 
			}
			
			if(tipo.equals("item-abitudine")) {
				String idPadre = params[1];
				nome = params[2];
				String id = params[3];
				Item item = new ItemImpl(nome, id);
				int i = 0;
				for(IHabit h : ht.getHabits()) {
					if(h.getId().equals(idPadre)) {
						ISimpleHabit sh = (ISimpleHabit) ht.getHabits().get(i);
						sh.addItem(item);
					}
					i++;
				}
			}
			line = reader.readLine();
		}
	}
	
	public void leggiStoricoAbitudini(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while(line != null) {
			String[] params = line.split(",");
			String idAbitudine = params[0];
			IHabit abitudine = ht.getHabit(idAbitudine);
			
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
