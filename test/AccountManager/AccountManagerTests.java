package AccountManager;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.model.accountmanager.classi.AccountManager;
import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.InvalidEmailException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.model.accountmanager.interfacce.IAccountManager;
import main.model.goalmanager.classi.GoalManager;
import main.model.goalmanager.classi.ObiettivoScomponibile;
import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.goalmanager.interfacce.IObiettivoAzione;
import main.model.goalmanager.interfacce.IObiettivoScomponibile;
import main.model.habittracker.classi.HabitTracker;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineScomponibile;
import main.model.habittracker.interfacce.IHabitTracker;
import main.model.timetracker.classi.TimeTracker;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITimeTracker;
import main.views.Colore;

class AccountManagerTests {	
	AccountManager app = null;
	
	private void accedi(IAccountManager app) {
		// accede all'account presente nel database
		try {
			app.accedi("test@gmail.com", "pass123");
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
	}
	
    @BeforeEach
    public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	// Cleanup AccountManager
    	Field instance = AccountManager.class.getDeclaredField("accountManager");
    	instance.setAccessible(true);
    	instance.set(instance, null);
    	
    	// Cleanup TimeTracker
    	Field tt = TimeTracker.class.getDeclaredField("timeTracker");
    	tt.setAccessible(true);
    	tt.set(tt, null);
    	
    	// Cleanup GoalManager
    	Field gm = GoalManager.class.getDeclaredField("goalManager");
    	gm.setAccessible(true);
    	gm.set(gm, null);
    	
    	// Cleanup HabitTracker
    	Field ht = HabitTracker.class.getDeclaredField("habitTracker");
    	ht.setAccessible(true);
    	ht.set(ht, null);
    }

	@Test
	public void testAccesso() {
		app = AccountManager.getInstance();
		
		// prova ad accedere ad un account sbagliando email
		assertThrows(WrongCredentialsException.class, () -> app.accedi("test@gmail.co", "pass123"));
		assertFalse(app.getAccessoEffettuato());
		
		// prova ad accedere ad un account sbagliando password
		assertThrows(WrongCredentialsException.class, () -> app.accedi("test@gmail.com", "pass13"));
		assertFalse(app.getAccessoEffettuato());
		
		// accede ad un account esistente
		accedi(app);
		
		// accesso effettuato
		assertTrue(app.getAccessoEffettuato());

	}
	
	@Test
	public void testRegistrazione() {
		app = AccountManager.getInstance();
		
		// prova a creare un nuovo account, ma le due password non corrispondono
		assertThrows(WrongCredentialsException.class, () -> app.registraAccount("Andre", "andre@gmail.com", "pass123", "pass124"));
		//assertFalse(app.getAccessoEffettuato());
		assertFalse(new File("database/andre@gmail.com.txt").exists());
		
		// prova a creare un nuovo account, ma l'email "newemail@gmail.com" è già stata usata
		assertThrows(ExistingAccountException.class, () -> app.registraAccount("Andre", "test@gmail.com", "pass123", "pass123"));
		
		// registra un nuovo account con successo
		try {
			app.registraAccount("Andre", "andre@gmail.com", "pass123", "pass123");
		} catch (WrongCredentialsException | ExistingAccountException | InvalidEmailException e) {
			e.printStackTrace();
		}
		assertTrue(app.getAccessoEffettuato());
		assertTrue(new File("database/andre@gmail.com.txt").exists());
		
		// elimina l'account
		try {
			app.eliminaAccount("andre@gmail.com", "pass123");
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testEliminazione() {
		app = AccountManager.getInstance();
		// crea un nuovo account
		try {
			app.registraAccount("Andre", "andre@gmail.com", "pass123", "pass123");
		} catch (WrongCredentialsException | ExistingAccountException | InvalidEmailException e) {
			e.printStackTrace();
		}
		
		// prova a eliminare un account, sbagliando la password
		assertThrows(WrongCredentialsException.class, () -> app.eliminaAccount("andre@gmail.com", "pass12"));
		assertTrue(new File("database/andre@gmail.com.txt").exists());
		
		// elimina l'account
		try {
			app.eliminaAccount("andre@gmail.com", "pass123");
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
		assertFalse(new File("database/andre@gmail.com.txt").exists());
	}
	
	/**
	 * Verifica la corretta lettura da file dei dati del TimeTracker
	 */
	@Test
	public void testLetturaTimeTracker() {
		app = AccountManager.getInstance();
		ITimeTracker tt = app.getTT();
		
		// accede all'account presente nel database
		accedi(app);
		
		// sono presenti 3 progetti, compreso quello di default ("Altro")
		assertEquals(3, tt.getProgetti().size());
		// controlla primo progetto letto
		IProgetto p = tt.getProgetti().get(1);
		assertEquals("PMO", p.getNome());
		assertEquals(Colore.Blu, p.getColore());
		assertEquals("idP1", p.getId());
		
		// sono presenti 2 attività
		assertEquals(2, tt.getAttività().size());
		// controlla prima attività
		IAttività a = tt.getAttività().get(0);
		assertEquals("Studiare analisi", a.getNome());
		assertEquals(25, a.getData().getDayOfMonth());
		assertEquals(5, a.getData().getMonthValue());
		assertEquals(2022, a.getData().getYear());
		assertEquals(7200, a.getDurata());
		assertEquals(LocalTime.of(23, 17), a.getOraInizio());
		assertEquals("idP2", a.getProgetto().getId());
		assertEquals("idA1", a.getId());
		
	}
	
	/**
	 * Verifica la corretta lettura da file dei dati del GoalManager
	 */
	@Test
	public void testLetturaObiettivi() {
		app = AccountManager.getInstance();
		IGoalManager gm = app.getGM();
		
		// accede all'account presente nel database
		accedi(app);
		
		// ci sono 3 obiettivi
		assertEquals(2, gm.getObiettivi().size());
		// primo obiettivo, "Dare gli esami"
		assertTrue(gm.getObiettivi().get(0) instanceof ObiettivoScomponibile);
		IObiettivoScomponibile o1 = (IObiettivoScomponibile) gm.getObiettivi().get(0);
		assertEquals("Dare gli esami", o1.getNome());
		assertEquals("descrizione", o1.getDescrizione());
		// "Dare gli esami" ha due sotto-obiettivi: "dare analisi" e "dare pmo"
		assertEquals(2, o1.getSottoObiettivi().size());
		IObiettivoScomponibile o3 = (IObiettivoScomponibile) o1.getSottoObiettivi().get(0);
		IObiettivoScomponibile o4 = (IObiettivoScomponibile) o1.getSottoObiettivi().get(1);
		assertEquals("dare analisi", o3.getNome());
		assertEquals("dare pmo", o4.getNome());
		// secondo obiettivo, "creare una startup"
		IObiettivoAzione o2 = (IObiettivoAzione) gm.getObiettivi().get(1);
		// l'obiettivo ha 2 azioni collegate, "lavorare al prodotto" e "fare pubblicità"
		assertEquals(2, o2.getAzioni().size());
		IAzioneScomponibile a1 = (IAzioneScomponibile) o2.getAzioni().get(0);
		IAzioneScomponibile a2 = (IAzioneScomponibile) o2.getAzioni().get(1);
		assertEquals("lavorare al prodotto", a1.getNome());
		assertEquals("fare marketing", a2.getNome());
		// la prima azione si compone di 2 item, "programmare" e "fare design"
		assertEquals(2, a1.getItems().size());
		assertEquals("programmare", a1.getItems().get(0).getNome());
		assertEquals("fare design", a1.getItems().get(1).getNome());
	}
	
	@Test 
	public void testLetturaAbitudini() {
		app = AccountManager.getInstance();
		IHabitTracker ht = app.getHT();
		
		// accede all'account presente nel database
		accedi(app);
		
		// ci sono 2 abitudini
		assertEquals(2, ht.getHabits().size());
		// controllo l'abitudine scomponibile
		IAbitudineScomponibile h = (IAbitudineScomponibile) ht.getHabits().get(1);
		assertEquals("allenamento", h.getName());
		assertEquals("descrizione", h.getDescription());
		assertEquals(5, h.getStartDate().getDayOfMonth());
		assertEquals(2, h.getStartDate().getMonthValue());
		assertEquals(2022, h.getStartDate().getYear());
		assertEquals(1, h.getDays().size());
		// l'abitudine si compone di due item abitudine
		assertEquals(2, h.getItems().size());
		// primo item
		assertEquals("corsa", h.getItems().get(0).getNome());
	}
	
	@Test
	public void testLetturaStoricoAbitudini() {
		app = AccountManager.getInstance();
		IHabitTracker ht = app.getHT();
		
		// accede all'account presente nel database
		accedi(app);
		
		// testo i dati salvati negli anni 2021 e 2022
		Map<Integer, List<IAbitudine>> anno2021 = ht.getYearRecords(2021);
		Map<Integer, List<IAbitudine>> anno2022 = ht.getYearRecords(2022);
		// il giorno 8 dell'anno 2022 ha 2 abitudini completate
		assertEquals(2, anno2022.get(8).size());
		assertEquals("routine mattutina", anno2022.get(8).get(0).getName());
		assertEquals("allenamento", anno2022.get(8).get(1).getName());
		// il giorno 5 del 2021 ha 1 abitudine completata
		assertEquals(1, anno2021.get(5).size());
		assertEquals("allenamento", anno2021.get(5).get(0).getName());
	}

}
