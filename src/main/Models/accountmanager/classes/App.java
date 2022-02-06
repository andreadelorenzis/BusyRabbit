package main.Models.AccountManager.classes;

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
import main.Models.timetracker.classes.Attività;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TimeTracker;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;

public class App implements IApp {
	private ITimeTracker tt = new TimeTracker();
	private IGoalManager gm = new GoalManager();
	private IHabitTracker ht = new HabitTracker();
	private String email = "";
	private String password = "";
	private boolean accessoEffettuato = false;

	@Override
	public void registraAccount(String nome, String email, String password, String ripetiPass) throws WrongCredentialsException, 
																									   ExistingAccountException {
		File f = new File("database/" + email + ".txt");
		if(!f.exists()) {
			if(password.equals(ripetiPass)) {
				inizializza(email, password);
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter("database/" + email + ".txt"));
					writer.write("password," + password + "\n");
					writer.write("---progetti---\n");
					writer.write("---attivita---\n");
					writer.write("---obiettivi---\n");
					writer.write("---abitudini---\n");
					writer.close();
					System.out.println("Registrazione effettuata.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new WrongCredentialsException("Le due password non combaciano.");
			}
		} else {
			throw new ExistingAccountException("L'email è già in uso da un altro account.");
		}
	}
	
	private void inizializza(String email, String password) {
		this.accessoEffettuato = true;
		this.email = email;
		this.password = password;
	}
	
	@Override
	public void accedi(String email, String password) throws WrongCredentialsException {
		try {	
			// trovo il file e lo apro in lettura
			BufferedReader reader = new BufferedReader(new FileReader("database/" + email + ".txt"));
			String primaLinea = reader.readLine();
			this.password = primaLinea.split(",")[1];
			if(this.password.equals(password)) {
				inizializza(email, password);
				
				reader.readLine();

				// lettura progetti
				leggiProgetti(reader);
				
				// lettura attività
				leggiAttività(reader);
				
				// lettura obiettivi
				leggiObiettivi(reader);
				
				// lettura abitudini
				leggiAbitudini(reader);
				
				// lettura storico abitudini
				leggiStoricoAbitudini(reader);
				
				reader.close();
			} else {
				reader.close();
				throw new WrongCredentialsException("Email o password non sono corrette.");
			}
		} catch (IOException e) {
			throw new WrongCredentialsException("Email o password non sono corrette.");
		}
	}

	@Override
	public void eliminaAccount(String email, String password) throws WrongCredentialsException {
		if(accessoEffettuato) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("database/" + email + ".txt"));
				File f = new File("database/" + email + ".txt");
				String primaLinea = reader.readLine();
				String passwordAccount = primaLinea.split(",")[1];
				reader.close();
				if(passwordAccount.equals(password)) {
					if(f.delete()) {
						System.out.println("Account eliminato: " + email);
					}
		
				} else {
					throw new WrongCredentialsException("Password non corretta");
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public ITimeTracker getTT() {
		return tt;
	}
	
	@Override
	public IGoalManager getGM() {
		return gm;
	}
	
	@Override
	public IHabitTracker getHT() {
		return ht;
	}
	
	@Override
	public boolean getAccessoEffettuato() {
		return accessoEffettuato;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public void salvaDati() {
		if(accessoEffettuato) {
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("database/" + email + ".txt", false));
				writer.write("password," + password + "\n");
				writer.write("---progetti---\n");
				scriviProgetti(writer);
				writer.write("---attivita---\n");
				scriviAttività(writer);
				writer.write("---obiettivi---\n");
				scriviObiettivi(writer, gm.getObiettivi());
				writer.write("---abitudini---\n");
				scriviAbitudini(writer);
				writer.write("---storico-abitudini---\n");
				scriviStoricoAbitudini(writer);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void cambiaEmail(String nuova, String password) throws ExistingAccountException, WrongCredentialsException {
		if(accessoEffettuato) {
			if(this.password.equals(password)) {
				File file = new File("database/" + this.email + ".txt");
				File nuovo = new File("database/" + nuova + ".txt");
				if(!nuovo.exists()) {
					if(file.renameTo(nuovo)) {
						System.out.println("Email cambiata.");
					}
				} else {
					throw new ExistingAccountException("Email già usata in un altro account.");
				}
			} else {
				throw new WrongCredentialsException("Password non corretta.");
			}
		}
	}
	
	@Override
	public void cambiaPassword(String vecchia, String nuova) throws WrongCredentialsException {
		if(accessoEffettuato) {
			if(this.password.equals(vecchia)) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader("database/" + email + ".txt"));
					// leggo il contenuto del file in una stringa
					String vecchioContenuto = "";
					String line = reader.readLine();
					while(line != null) {
						vecchioContenuto += line + System.lineSeparator();
						line = reader.readLine();
					}
					reader.close();
					
					// modifico la prima linea nella stringa
					String nuovoContenuto = vecchioContenuto.replaceFirst("password," + this.password, "password," + nuova);
					
					// sovrascrivo il file con la nuova stringa
					BufferedWriter writer = new BufferedWriter(new FileWriter("database/" + email + ".txt", false));
					writer.write(nuovoContenuto);
					writer.close();
					
					this.password = nuova;
					System.out.println("Password cambiata.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new WrongCredentialsException("Password non corretta.");
			}
		}
	}
	
	private void scriviProgetti(BufferedWriter writer) {
		try {
			int i = 0;
			for(IProgetto p : tt.getProgetti()) {
				// se p non è il progetto di default
				if(i > 0) {
					String progetto = p.getNome() + "," + p.getColore() + "," + p.getId() + "\n";
					writer.write(progetto);
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scriviAttività(BufferedWriter writer) {
		try {
			for(IAttività a : tt.getAttività()) {
				String idProgetto = "null";
				if(!(a.getProgetto().getId().equals(tt.getProgetti().get(0).getId()))) {
					idProgetto = a.getProgetto().getId();
				} 
				String attività = a.getNome() + "," + a.getData().getDayOfMonth() + "," + a.getData().getMonthValue()
								  + "," + a.getData().getYear() + "," + a.getOraInizio().getHour() 
								  + "," + a.getOraInizio().getMinute() + "," + a.getDurata() + "," + idProgetto + "," 
								  + a.getId() + "\n";
				writer.write(attività);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scriviObiettivi(BufferedWriter writer, List<IObiettivo> obiettivi) {
		try {
			if(!obiettivi.isEmpty()) {
				for(IObiettivo o : obiettivi) {
					String stringaObiettivo = "";
					if(o instanceof ObiettivoScomponibile) {
						IObiettivoScomponibile os = (IObiettivoScomponibile) o;
						
						// se il seguente obiettivo è un sotto-obiettivo
						if(o.getObiettivoPadre() != null) {
							String idPadre = o.getObiettivoPadre().getId();
							stringaObiettivo = "obiettivo-scomponibile" + "," + true + "," + idPadre
							+ "," + os.getNome() + "," + os.getDescrizione() + "," + os.getData().getDayOfMonth() 
							+ "," + os.getData().getMonthValue() + "," + os.getData().getYear() + "," + os.getId() + "\n";
						} else {
							stringaObiettivo = "obiettivo-scomponibile" + "," + false + "," + os.getNome() 
							+ "," + os.getDescrizione() + "," + os.getData().getDayOfMonth() + "," + os.getData().getMonthValue() 
							+ "," + os.getData().getYear() + "," + os.getId() + "\n";
						}
						
						writer.write(stringaObiettivo);
						
						// scrivo i sotto-obiettivi del seguente ObiettivoScomponibile
						scriviObiettivi(writer, os.getSottoObiettivi());
						
					} else if (o instanceof ObiettivoAzione) {
						IObiettivoAzione oa = (IObiettivoAzione) o;
						
						// se il seguente obiettivo è un sotto-obiettivo
						if(o.getObiettivoPadre() != null) {
							String idPadre =  o.getObiettivoPadre().getId();
							stringaObiettivo = "obiettivo-azione" + "," + true + "," + idPadre
							+ "," + oa.getNome() + "," + oa.getDescrizione() + "," + oa.getData().getDayOfMonth()
							+ "," + oa.getData().getMonthValue() + "," + oa.getData().getYear() 
							+ "," + oa.getValoreTotale() + "," + oa.getUnita() + "," + oa.getId() + "\n";
						} else {
							stringaObiettivo = "obiettivo-azione" + "," + false + "," + oa.getNome() + 
									"," + oa.getDescrizione() + "," + oa.getData().getDayOfMonth()
									+ "," + oa.getData().getMonthValue() + "," + oa.getData().getYear() 
									+ "," + oa.getValoreTotale() + "," + oa.getUnita() + "," + oa.getId() + "\n";
						}
						
						writer.write(stringaObiettivo);
						
						// scrivo le azioni del seguente ObiettivoAzione
						for(IAzione a : oa.getAzioni()) {
							String stringaAzione = "";
							String stringaGiorni = "";
							int i = 0;
							for(Giorno g : a.getGiorniRipetizione()) {
								stringaGiorni += g + "-";
								if(i == a.getGiorniRipetizione().size() - 1) {
									stringaGiorni += g;
								}
								i++;
							}
							if(a instanceof AzioneScomponibile) {
								IAzioneScomponibile as = (IAzioneScomponibile) a;
								stringaAzione = "azione-scomponibile" + "," + as.getObiettivo().getId() 
								+ "," + as.getNome() + "," + as.getIncremento() + "," + as.getDataInizio().getDayOfMonth()
								+ "," + as.getDataInizio().getMonthValue() + "," + as.getDataInizio().getYear() 
								+ "," + stringaGiorni + "," + as.getId() + "\n";
								writer.write(stringaAzione);
								
								// scrivi gli item della seguente AzioneScomponibile
								for(Item it : as.getItems()) {
									IAzioneScomponibile padre = (IAzioneScomponibile) it.getPadre();
									String stringaItem = "item-azione" + "," + padre.getId() 
									+ "," + it.getNome() + "," + it.getId() + "\n";
									writer.write(stringaItem);
								}
							} else if(a instanceof AzioneSessione) {
								IAzioneSessione as = (IAzioneSessione) a;
								stringaAzione = "azione-sessione" + "," + as.getObiettivo().getId() 
										+ "," + as.getNome() + "," + as.getIncremento() + "," + as.getDataInizio().getDayOfMonth()
										+ "," + as.getDataInizio().getMonthValue() + "," + as.getDataInizio().getYear() 
										+ "," + stringaGiorni + "," + as.getDurata() + "," + as.getId() + "\n";
								writer.write(stringaAzione);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scriviAbitudini(BufferedWriter writer) {
		try {
			for(IHabit h : ht.getHabits()) {
				String stringaAbitudine = "";
				String stringaGiorni = "";
				int i = 0;
				for(DayOfWeek g : h.getDays()) {
					stringaGiorni += g + "-";
					if(i == h.getDays().size() - 1) {
						stringaGiorni += g;
					}
					i++;
				}
				if(h instanceof SimpleHabit) {
					ISimpleHabit s = (ISimpleHabit) h;
					stringaAbitudine = "abitudine-semplice" + "," + s.getName() + "," + s.getDescription()
					+ "," + s.getStartDate().getDayOfMonth() + "," + s.getStartDate().getMonthValue()
					+ "," + s.getStartDate().getYear() + "," + stringaGiorni + "," + s.getId() + "\n"; 
					
					// scrive gli item della seguente abitudine semplice
					for(Item it : s.getItems()) {
						ISimpleHabit padre = (ISimpleHabit) it.getPadre();
						String stringaItem = "item-abitudine" + "," + padre.getId() 
								+ "," + it.getNome() + "," + it.getId() + "\n";
						writer.write(stringaItem);
					}
				} else if (h instanceof SessionHabit) {
					ISessionHabit s = (ISessionHabit) h;
					stringaAbitudine = "abitudine-sessione" + "," + s.getName() + "," + s.getDescription()
					+ "," + s.getStartDate().getDayOfMonth() + "," + s.getStartDate().getMonthValue()
					+ "," + s.getStartDate().getYear() + "," + stringaGiorni 
					+ "," + s.getDuration() + "," + s.getId() + "\n"; 
				}
				writer.write(stringaAbitudine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scriviStoricoAbitudini(BufferedWriter writer) {
		try {
			for(IHabit h : ht.getHabits()) {
				String stringaAbitudine = "";
				String stringaAnno = "";
				stringaAbitudine += h.getId() + ",";
				Map<Integer, List<Integer>> yearRecords = h.getYearData();
				int i = 0;
				for(int anno : yearRecords.keySet()) {
					stringaAnno += anno + "-";
					List<Integer> giorni = yearRecords.get(anno);
					int j = 0;
					for(int giorno : giorni) {
						if(j != giorni.size() - 1) {
							stringaAnno += giorno + "-";
						} else {
							stringaAnno += giorno;
						}
						j++;
					}
					if(i != yearRecords.keySet().size() - 1) {
						stringaAnno += ",";
					}
					i++;
				}
				stringaAbitudine += stringaAnno + "\n";
				writer.write(stringaAbitudine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private IProgetto trovaProgetto(String idProgetto) {
		int i = 0;
		for(IProgetto p : tt.getProgetti()) {
			if(p.getId().equals(idProgetto)) {
				return tt.getProgetti().get(i);
			}
			i++;
		}
		return null;
	}
	
	private void leggiProgetti(BufferedReader reader) throws IOException {
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
	
	private void leggiAttività(BufferedReader reader) throws IOException {
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
				progetto = trovaProgetto(idProgetto);
				a = new Attività(nome, LocalDate.of(anno, mese, giorno), LocalTime.of(ore, minuti), durata, progetto, id);
			} else {
				a = new Attività(nome, LocalDate.of(anno, mese, giorno), LocalTime.of(ore, minuti), durata, id);
			}
			tt.aggiungiAttività(a);
			line = reader.readLine();
		}
	}
	
	private void leggiObiettivi(BufferedReader reader) throws IOException {
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
					// lo aggiungo anche al obiettivo padre, già aggiunto nella lista
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
					String unità = params[9];
					id = params[10];
					// creo il sotto-obiettivo azione e lo aggiungo alla lista
					nuovo = new ObiettivoAzione(nome, descrizione, LocalDate.of(anno, mese, giorno), valore, unità, id);
					obiettivi.add(nuovo);
					// lo aggiungo anche al obiettivo padre, già aggiunto nella lista
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
					String unità = params[8];
					id = params[9];
					// creo l'obiettivo scomponibile e lo aggiungo alla lista
					nuovo = new ObiettivoAzione(nome, descrizione, LocalDate.of(anno, mese, giorno), valore, unità, id);
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
	
	private void leggiAbitudini(BufferedReader reader) throws IOException {
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
	
	private void leggiStoricoAbitudini(BufferedReader reader) throws IOException {
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
