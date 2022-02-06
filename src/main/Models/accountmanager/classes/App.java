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
import main.Models.accountmanager.interfaces.IApp;
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
    private AppWriter writerApp = new AppWriter(tt, gm, ht);
    private AppReader readerApp = new AppReader(tt, gm, ht);

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
			throw new ExistingAccountException("L'email ï¿½ giï¿½ in uso da un altro account.");
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
				readerApp.leggiProgetti(reader);
				
				// lettura Attività
				readerApp.leggiAttività(reader);
				
				// lettura obiettivi
				readerApp.leggiObiettivi(reader);
				
				// lettura abitudini
				readerApp.leggiAbitudini(reader);
				
				// lettura storico abitudini
				readerApp.leggiStoricoAbitudini(reader);
				
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
				writerApp.scriviProgetti(writer);
				writer.write("---attivita---\n");
				writerApp.scriviAttività(writer);
				writer.write("---obiettivi---\n");
				writerApp.scriviObiettivi(writer, gm.getObiettivi());
				writer.write("---abitudini---\n");
				writerApp.scriviAbitudini(writer);
				writer.write("---storico-abitudini---\n");
				writerApp.scriviStoricoAbitudini(writer);
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
					throw new ExistingAccountException("Email giï¿½ usata in un altro account.");
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

}
