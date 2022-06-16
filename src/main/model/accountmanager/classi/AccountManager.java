package main.model.accountmanager.classi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.model.accountmanager.interfacce.IAccountManager;
import main.model.goalmanager.classi.GoalManager;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.habittracker.classi.HabitTracker;
import main.model.habittracker.interfacce.IHabitTracker;
import main.model.timetracker.classi.TimeTracker;
import main.model.timetracker.interfacce.ITimeTracker;

public class AccountManager implements IAccountManager {
	//-------------------------------- CAMPI -----------------------------------
	/*
	 * SINGLETONE
	 */
	private static AccountManager accountManager = null;
	
	/*
	 * Istanza di TimeTacker
	 */
	private ITimeTracker tt = TimeTracker.getInstance();
	
    /*
     * Istanza di GoalManager
     */
	private IGoalManager gm = GoalManager.getInstance();
	
	/*
	 * Istanza di HabitTracker
	 */
	private IHabitTracker ht = HabitTracker.getInstance();
	
	/*
	 * Email dell'account
	 */
	private String email = "";
	
	/*
	 * Password dell'account
	 */
	private String password = "";
	
	/*
	 * Utente loggato o meno
	 */
	private boolean accessoEffettuato = false;
	
	/*
	 * Oggetto per scrivere i dati
	 */
    private AccountWriter accountWriter = new AccountWriter();
    
    /*
     * Oggeto per leggere i dati 
     */
    private AccountReader accountReader = new AccountReader();

    //----------------------------- COSTRUTTORI --------------------------------
    private AccountManager() {

    }
    
    //---------------------------- METODI PRIVATI ------------------------------
    private void inizializza(String email, String password) {
    	this.accessoEffettuato = true;
    	this.email = email;
    	this.password = password;
    }
    
    //--------------------------- METODI PUBBLICI ------------------------------
    public static AccountManager getInstance() {
    	if(accountManager == null) {
    		accountManager = new AccountManager();
    	}
    	return accountManager;
    }
    
	@Override
	public void registraAccount(String nome, String email, String password, String ripetiPass) throws WrongCredentialsException, 
		  																							   ExistingAccountException, 
		  																							   InvalidEmailException {
		// valida email
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email); 
		if(!matcher.matches()) {
			throw new InvalidEmailException("Email non valida");
		}
	
		// fai ricerca in database
		File f = new File("database/" + email + ".txt");
		if(!f.exists()) {
			// leggi dati
			if(password.equals(ripetiPass)) {
				inizializza(email, password);
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter("database/" + email + ".txt"));
					writer.write("password," + password + "\n");
					writer.write("---progetti---\n");
					writer.write("---attivita---\n");
					writer.write("---obiettivi---\n");
					writer.write("---abitudini---\n");
					writer.write("---storico-abitudini---\n");
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
	
	@Override
	public void accedi(String email, String password) throws WrongCredentialsException {
		try {	
			// trova il file e lo apro in lettura
			BufferedReader reader = new BufferedReader(new FileReader("database/" + email + ".txt"));
			
			// lettura password
			String primaLinea = reader.readLine();
			this.password = primaLinea.split(",")[1];
			if(this.password.equals(password)) {
				inizializza(email, password);
				
				reader.readLine();

				// lettura progetti
				accountReader.leggiProgetti(reader, tt);
				
				// lettura Attività
				accountReader.leggiAttività(reader, tt);
				
				// lettura obiettivi
				accountReader.leggiObiettivi(reader, gm);
				
				// lettura abitudini
				accountReader.leggiAbitudini(reader, ht);
				
				// lettura storico abitudini
				accountReader.leggiStoricoAbitudini(reader, ht);
				
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
	public boolean eliminaAccount(String email, String password) throws WrongCredentialsException {
		if(accessoEffettuato) {
			if(this.password.equals(password)) {
				File f = new File("database/" + email + ".txt");
				if(f.delete()) {
					return true;
				}
			} else {
				throw new WrongCredentialsException("Password non corretta");
			}
		}
		return false;
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
	
	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public void salvaDati() {
		
		if(accessoEffettuato) {
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("database/" + email + ".txt", false));
				
				// scrittura password
				writer.write("password," + password + "\n");
				
				// scrittura progetti
				writer.write("---progetti---\n");
				accountWriter.scriviProgetti(writer, tt);
				
				// scrittura attività
				writer.write("---attivita---\n");
				accountWriter.scriviAttività(writer, tt);
				
				// scrittura obiettivi
				writer.write("---obiettivi---\n");
				accountWriter.scriviObiettivi(writer, gm.getObiettivi());
				
				// scrittura abitudini
				writer.write("---abitudini---\n");
				accountWriter.scriviAbitudini(writer, ht);
				
				// scrittura storico abitudini
				writer.write("---storico-abitudini---\n");
				accountWriter.scriviStoricoAbitudini(writer, ht);
				
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
