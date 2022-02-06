package AccountManager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.Colori;
import main.Models.accountmanager.classes.App;
import main.Models.accountmanager.classes.ExistingAccountException;
import main.Models.accountmanager.classes.WrongCredentialsException;
import main.Models.accountmanager.interfaces.IApp;
import main.Models.goalmanager.classes.ObiettivoScomponibile;
import main.Models.goalmanager.interfaces.IAzioneScomponibile;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.habittracker.interfaces.ISimpleHabit;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;

/**
 * TODO:
 * - testare lettura ora inizio attività
 * 
 */
class AccountManagerTests {	
	
	private void accedi(IApp app) {
		// accede all'account presente nel database
		try {
			app.accedi("test@gmail.com", "pass123");
		} catch (WrongCredentialsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAccesso() {
		IApp app = new App();
		
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
		IApp app = new App();
		
		// prova a creare un nuovo account, ma le due password non corrispondono
		assertThrows(WrongCredentialsException.class, () -> app.registraAccount("Andre", "andre@gmail.com", "pass123", "pass124"));
		assertFalse(app.getAccessoEffettuato());
		assertFalse(new File("database/andre@gmail.com.txt").exists());
		
		// prova a creare un nuovo account, ma l'email "newemail@gmail.com" è già stata usata
		assertThrows(ExistingAccountException.class, () -> app.registraAccount("Andre", "test@gmail.com", "pass123", "pass123"));
		
		// registra un nuovo account con successo
		try {
			app.registraAccount("Andre", "andre@gmail.com", "pass123", "pass123");
		} catch (WrongCredentialsException | ExistingAccountException e) {
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
		IApp app = new App();
		
		// crea un nuovo account
		try {
			app.registraAccount("Andre", "andre@gmail.com", "pass123", "pass123");
		} catch (WrongCredentialsException | ExistingAccountException e) {
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
		IApp app = new App();
		ITimeTracker tt = app.getTT();
		
		// accede all'account presente nel database
		accedi(app);
		
		// sono presenti 3 progetti, compreso quello di default ("Altro")
		assertEquals(3, tt.getProgetti().size());
		// controlla primo progetto letto
		IProgetto p = tt.getProgetti().get(1);
		assertEquals("PMO", p.getNome());
		assertEquals(Colori.Blu, p.getColore());
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
		assertEquals(LocalTime.of(23, 1), a.getOraInizio());
		assertEquals("idP1", a.getProgetto().getId());
		assertEquals("idA1", a.getId());
		
	}
	
	/**
	 * Verifica la corretta lettura da file dei dati del GoalManager
	 */
	@Test
	public void testLetturaObiettivi() {
		IApp app = new App();
		IGoalManager gm = app.getGM();
		
		// accede all'account presente nel database
		accedi(app);
		
		// ci sono 3 obiettivi
		assertEquals(3, gm.getObiettivi().size());
		// primo obiettivo, "Dare gli esami"
		assertTrue(gm.getObiettivi().get(0) instanceof ObiettivoScomponibile);
		IObiettivoScomponibile o1 = (IObiettivoScomponibile) gm.getObiettivi().get(0);
		assertEquals("Dare gli esami", o1.getNome());
		assertEquals("nessuna descrizione", o1.getDescrizione());
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
		assertEquals("fare pubblicita", a2.getNome());
		// la prima azione si compone di 2 item, "programmare" e "fare design"
		assertEquals(2, a1.getItems().size());
		assertEquals("programmare", a1.getItems().get(0).getNome());
		assertEquals("fare design", a1.getItems().get(1).getNome());
	}
	
	@Test 
	public void testLetturaAbitudini() {
		IApp app = new App();
		IHabitTracker ht = app.getHT();
		
		// accede all'account presente nel database
		accedi(app);
		
		// ci sono 2 abitudini
		assertEquals(2, ht.getHabits().size());
		// controllo la prima abitudine semplice
		ISimpleHabit h = (ISimpleHabit) ht.getHabits().get(0);
		assertEquals("routine mattutina", h.getName());
		assertEquals("descrizione", h.getDescription());
		assertEquals(20, h.getStartDate().getDayOfMonth());
		assertEquals(2, h.getStartDate().getMonthValue());
		assertEquals(2022, h.getStartDate().getYear());
		assertEquals(2, h.getDays().size());
		assertEquals("idAb1", h.getId());
		// l'abitudine si compone di due item abitudine
		assertEquals(2, h.getItems().size());
		// primo item
		assertEquals("doccia fredda", h.getItems().get(0).getNome());
		assertEquals("idI1", h.getItems().get(0).getId());
	}
	
	@Test
	public void testLetturaStoricoAbitudini() {
		IApp app = new App();
		IHabitTracker ht = app.getHT();
		
		// accede all'account presente nel database
		accedi(app);
		
		// testo i dati salvati nell'anno 2021
		Map<Integer, List<IHabit>> anno2021 = ht.getYearRecords(2021);
		Map<Integer, List<IHabit>> anno2022 = ht.getYearRecords(2022);
		// il giorno 8 dell'anno 2022 ha 2 abitudini completate
		assertEquals(2, anno2022.get(8).size());
		assertEquals("routine mattutina", anno2022.get(8).get(0).getName());
		assertEquals("allenamento", anno2022.get(8).get(1).getName());
		// il giorno 5 del 2021 ha 1 abitudine completata
		assertEquals(1, anno2021.get(5).size());
		assertEquals("allenamento", anno2021.get(5).get(0).getName());
	}

}
