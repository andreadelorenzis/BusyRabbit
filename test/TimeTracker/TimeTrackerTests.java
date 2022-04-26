package TimeTracker;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import main.Models.timetracker.classes.Attività;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TimeTracker;
import main.Models.timetracker.classes.TrackerEnum;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.ICronometro;
import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;
import main.Views.Colore;

import java.util.List;
import java.util.Map;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeTrackerTests {

	ITimeTracker t = null;
	
	private void inizializza(ITimeTracker t) {
		// creo 2 progetti
		t.aggiungiProgetto(new Progetto("Analisi", Colore.Rosso));
		t.aggiungiProgetto(new Progetto("Allenamento", Colore.Verde));

		// aggiungo 3 attività al primo progetto
		t.aggiungiAttività(new Attività("Studiare",
										LocalDate.of(2021, Month.JANUARY, 2),
										LocalTime.of(19, 0),
										7200L,
										t.getProgetti().get(1)));
		t.aggiungiAttività(new Attività("Fare esercizi",
										LocalDate.of(2021, Month.JANUARY, 10),
										LocalTime.of(12, 15),
										7200L,
										t.getProgetti().get(1)));
		t.aggiungiAttività(new Attività("Studiare ancora",
										LocalDate.of(2021, Month.FEBRUARY, 5),
										LocalTime.of(7, 2),
										7200L,
										t.getProgetti().get(1)));
		t.aggiungiAttività(new Attività("Gambe",
										LocalDate.of(2022, Month.MARCH, 7),
										LocalTime.of(15, 55),
										3600L));
	}
	
	private void aggiungiAttività(ITimeTracker t) {
		
		// creo un nuovo progetto
		t.aggiungiProgetto(new Progetto("Progetto", Colore.Rosso));
		
		// aggiungo 30 attività, in 30 giorni diversi, associate al progetto
		for(int i = 1; i <= 31; i++) {
			t.aggiungiAttività(new Attività("Studiare",
										  LocalDate.of(2021, Month.JANUARY, i),
										  LocalTime.of(19, 0),
										  7200L,
										  t.getProgetti().get(1)));
		}
	};
	
    @Before
    public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
    	Field instance = TimeTracker.class.getDeclaredField("timeTracker");
    	instance.setAccessible(true);
    	instance.set(instance, null);
    }
	
	/**
	 * Verifica l'aggiunta di attività e progetti
	 */
	@Order(1)
	@Test
	public void testAttività() {
		t = TimeTracker.getInstance();
		inizializza(t);
		
		// ci sono 4 attività
		assertEquals(4, t.getAttività().size());
		
		// ci sono 4 giorni di attività
		List<List<IAttività>> giorniAttività = t.getGiorniAttività(1);
		assertEquals(4, t.getNumGiorni());
		assertEquals(4, giorniAttività.size());
		List<IAttività> giorno1 = giorniAttività.get(0);
		List<IAttività> giorno2 = giorniAttività.get(1);
		
		// L'ordine dei giorni va dal più recente al meno recente 
		assertTrue(giorno1.get(0).getData().isAfter(giorno2.get(0).getData()));
		
		// La data della prima attività nel primo giorno attività è 7 Marzo 2022, 15:55
		IAttività a1 = giorno1.get(0);
		assertEquals(7, a1.getData().getDayOfMonth());
		assertEquals(Month.MARCH, a1.getData().getMonth());
		assertEquals(2022, a1.getData().getYear());
		assertEquals(15, a1.getOraInizio().getHour());
		assertEquals(55, a1.getOraInizio().getMinute());
		
		// La data della prima attività nel secondo giorno attività è 5 Febbraio 2021, 07:02
		IAttività a2 = giorno2.get(0);
		assertEquals(5, a2.getData().getDayOfMonth());
		assertEquals(Month.FEBRUARY, a2.getData().getMonth());
		assertEquals(2021, a2.getData().getYear());
		assertEquals(2022, a1.getData().getYear());
		assertEquals(7, a2.getOraInizio().getHour());
		assertEquals(2, a2.getOraInizio().getMinute());
	}
	
	/**
	 * Verifica che vengano calcolati correttamente i tempi annuali e mensili di un progetto
	 */
	@Test
	@Order(2)
	public void testProgetto() {
		t = TimeTracker.getInstance();
		inizializza(t);
		IProgetto p = t.getProgetti().get(1);
		
		// ci sono 3 progetti
		assertEquals(3, t.getProgetti().size());
		
		// il primo è quello di default "Altro"
		IProgetto defP = t.getProgetti().get(0);
		assertEquals("Altro", defP.getNome());
		
		// calcolo i tempi totali del primo progetto per ogni mese dell'anno: 2021
		Map<Integer, Long> annoAttività = p.getAnnoProgetto(2021);
		
		// nel Gennaio del 2021 il tempo totale del progetto ammonta a 4 ore
		assertEquals((Long)14400L, annoAttività.get(1));
		
		// nel Febbraio del 2021 il tempo totale del progetto ammonta a 2 ore
		assertEquals((Long)7200L, annoAttività.get(2));
		
		// calcolo i tempi totali del primo progetto per ogni giorno del mese: Gennaio 2021
		Map<Integer, Long> meseAttività = p.getMeseProgetto(2021, Month.JANUARY);
		
		// il 2 Gennaio 2021 il tempo totale del progetto ammonta a 2 ore
		assertEquals((Long)7200L, meseAttività.get(2));
		
		// a Marzo 2022 il tempo totale ammonta a 1 ora
		Map<Integer, Long> annoAttività2 = defP.getAnnoProgetto(2022);
		Map<Integer, Long> meseAttività2 = defP.getMeseProgetto(2022, Month.MARCH);
		assertEquals((Long)3600L, meseAttività2.get(7));
		assertEquals((Long)3600L, annoAttività2.get(3));
	}
	
	/**
	 * Verifica l'eliminazione di progetti
	 */
	@Test
	@Order(3)
	public void testEliminazioneProgetti() {
		t = TimeTracker.getInstance();
		inizializza(t);
		
		// il time tracker contiene 2 progetti
		assertEquals(3, t.getProgetti().size());
		
		// elimino il terzo progetto
		t.eliminaProgetto(t.getProgetti().get(2).getId());
		
		// il time tracker contiene 2 
		assertEquals(2, t.getProgetti().size());
		// il secondo progetto è: "Analisi"
		assertEquals("Analisi", t.getProgetti().get(1).getNome());
		
	}
//	
	/**
	 * Verifica l'eliminazione di attività
	 */
	@Test 
	@Order(4)
	public void testEliminazioneAttività() {
		t = TimeTracker.getInstance();
		inizializza(t);
		
		// ci sono 4 giorni di attività
		List<List<IAttività>> giorniAttività = t.getGiorniAttività(1);
		assertEquals(4, giorniAttività.size());
		
		// attività "Studiare ancora" del 5 Febbraio 2021
		IAttività a = giorniAttività.get(1).get(0);
		
		// elimino l'attività
		t.eliminaAttività(a);
		
		// ci sono 3 giorni attività
		giorniAttività = t.getGiorniAttività(1);
		assertEquals(3, giorniAttività.size());
		List<IAttività> giorno = giorniAttività.get(0);
		List<IAttività> giorno2 = giorniAttività.get(1); 
		List<IAttività> giorno3 = giorniAttività.get(2);
		
		// la prima attività rimanente è "Gambe" del 7 Marzo 2022
		assertEquals("Gambe", giorno.get(0).getNome());
		assertEquals(7, giorno.get(0).getData().getDayOfMonth());
		assertEquals(Month.MARCH, giorno.get(0).getData().getMonth());
		assertEquals(2022, giorno.get(0).getData().getYear());
		
		// la seconda attività rimanente è "Fare esercizi" del 10 Gennaio 2021
		assertEquals("Fare esercizi", giorno2.get(0).getNome());
		assertEquals(10, giorno2.get(0).getData().getDayOfMonth());
		assertEquals(Month.JANUARY, giorno2.get(0).getData().getMonth());
		assertEquals(2021, giorno2.get(0).getData().getYear());
		
		// la terza attività rimanente è "Studiare" del 2 Gennaio 2021
		assertEquals("Studiare", giorno3.get(0).getNome());
		assertEquals(2, giorno3.get(0).getData().getDayOfMonth());
		assertEquals(Month.JANUARY, giorno3.get(0).getData().getMonth());
		assertEquals(2021, giorno3.get(0).getData().getYear());
	}
	
	/**
	 * Verifica la paginazione dei giorni di attività
	 */
	@Test
	@Order(5)
	public void verificaPaginazione() {
		t = TimeTracker.getInstance();
		aggiungiAttività(t);
		
		// ottengo la prima pagina con i primi 10 giorni di attività
		List<List<IAttività>> giorniAttività = t.getGiorniAttività(1);
		List<IAttività> giorno1 = giorniAttività.get(0);
		List<IAttività> giorno2 = giorniAttività.get(1);
		
		// il primo giorno della prima pagina è 31 Gennaio
		assertEquals(31, giorno1.get(0).getData().getDayOfMonth());
		// il secondo giorno della prima pagina è il 30 Gennaio
		assertEquals(30, giorno2.get(0).getData().getDayOfMonth());
		
		// ottengo la seconda pagina con i successivi 10 giorni di attività
		giorniAttività = t.getGiorniAttività(2);
		giorno1 = giorniAttività.get(0);
		giorno2 = giorniAttività.get(1);
		
		// il primo giorno della seconda pagina è il 21 Gennaio
		assertEquals(21, giorno1.get(0).getData().getDayOfMonth());
		// il secondo giorno della seconda pagina è il 20 Gennaio
		assertEquals(20, giorno2.get(0).getData().getDayOfMonth());
	}
	
	/**
	 * Verifica funzionamento del pomodoro timer
	 */
	@Test
	@Order(6)
	public void verificaPomodoro1() {
		t = TimeTracker.getInstance();
		t.scegliTracker(TrackerEnum.POMODOROTIMER);
		
		// il timer di default ha
		// sessione: 30M
		// pausa breve: 5M
		// pausa lunga: 10M
		// cicli prima della pausa lunga: 3
		IPomodoroTimer tracker = (IPomodoroTimer) t.getTracker();
		assertEquals(30*60, tracker.getDurataSessione());
		assertEquals(5*60, tracker.getDurataPausaBreve());
		assertEquals(10*60, tracker.getDurataPausaLunga());
		assertEquals(3, tracker.getNCicli());
		
		// Avvio il tracker sull'attività "Studiare"
		t.avviaTracker(new Attività("Studiare",
									LocalDate.of(2021, Month.JANUARY, 2),
									LocalTime.of(19, 0),
									7200L,
									t.getProgetti().get(0)));
		
		// Attendo 1 secondo e qualcosa
		try {
			Thread.sleep(1100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		// Termino il tracker
		t.terminaTracker();	
		
		// E' presente un'attività "Studiare", la cui durata è 1 secondo
		List<List<IAttività>> giorni = t.getGiorniAttività(1);
		IAttività a = giorni.get(0).get(0);
		assertEquals("Studiare", a.getNome());
		assertEquals(1, a.getDurata());
	}

	/**
	 * Verifica funzionamento del pomodoro timer
	 */
	@Test
	@Order(7)
	public void verificaPomodoro2() {
		t = TimeTracker.getInstance();
		t.scegliTracker(TrackerEnum.POMODOROTIMER);
		IPomodoroTimer tracker = (IPomodoroTimer) t.getTracker();
		
		// imposto il timer: sessione 1s, pausa breve 1s, pausa lunga 1s, nCicli 1
		tracker.setDurataSessione(1);
		tracker.setDurataPausaBreve(1);
		tracker.setDurataPausaLunga(1);
		tracker.setNCicli(1);
		
		// le impostazioni del timer sono corrette
		assertEquals(1, tracker.getDurataSessione());
		assertEquals(1, tracker.getDurataPausaBreve());
		assertEquals(1, tracker.getDurataPausaLunga());
		assertEquals(1, tracker.getNCicli());
		
		// avvio il tracker sull'attività "Studiare"
		t.avviaTracker(new Attività("Studiare",
									LocalDate.of(2021, Month.JANUARY, 5),
									LocalTime.of(19, 0),
									7200L,
									t.getProgetti().get(0)));
		
		// Prima sessione, attendo 2 secondi e qualcosa in modo che termini
		try {
			Thread.sleep(2100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// è presente un giorno attività, con un'attività "Studiare", durata 1s, data 5 Gennaio 2021
		List<List<IAttività>> giorni = t.getGiorniAttività(1);
		assertEquals(1, giorni.get(0).size());
		assertEquals("Studiare", giorni.get(0).get(0).getNome());
		assertEquals(2021, giorni.get(0).get(0).getData().getYear());
		assertEquals(Month.JANUARY, giorni.get(0).get(0).getData().getMonth());
		assertEquals(5, giorni.get(0).get(0).getData().getDayOfMonth());
		assertEquals(1, giorni.get(0).get(0).getDurata());
		
		// timer in pausa breve (1s) e lunga (1s), attendo 2 secondi e qualcosa
		try {
			Thread.sleep(3100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// non sono state aggiunte nuove attività
		giorni = t.getGiorniAttività(1);
		assertEquals(1, giorni.get(0).size());
		
		// timer in sessione, attendo 2 secondi e qualcosa
		try {
			Thread.sleep(3100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// è stata aggiunta un'altra attività, "Studiare", durata 1s, nello stesso giorno 2 Gennaio 2021
		giorni = t.getGiorniAttività(1);
		assertEquals(2, giorni.get(0).size());
		assertEquals("Studiare", giorni.get(0).get(1).getNome());
		assertEquals(2021, giorni.get(0).get(1).getData().getYear());
		assertEquals(Month.JANUARY, giorni.get(0).get(1).getData().getMonth());
		assertEquals(5, giorni.get(0).get(1).getData().getDayOfMonth());
		assertEquals(1, giorni.get(0).get(1).getDurata());
		
		// termino il timer
		t.terminaTracker();
		
		// timer terminato
		assertFalse(t.getTracker().getAvviato());
	
	}
	
	/**
	 * Verifica il funzionamento del cronometro
	 */
	@Test 
	@Order(8)
	public void verificaCronometro() {
		t = TimeTracker.getInstance();
		t.scegliTracker(TrackerEnum.CRONOMETRO);
		ICronometro c = (ICronometro) t.getTracker();
		
		// Avvio il tracker sull'attività "Studiare"
		t.avviaTracker(new Attività("Studiare",
									LocalDate.of(2021, Month.JANUARY, 2),
									LocalTime.of(19, 0),
									7200L,
									t.getProgetti().get(0)));
		
		// sono stati monitorati 0 secondi
		assertEquals(0, c.getTempoAttuale());
		
		// attendo 1 secondo
		try {
			Thread.sleep(1100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// sono stati monitorati 1 secondi
		assertEquals(1, c.getTempoAttuale());
		
		// Termino il tracker
		t.terminaTracker();	
		
		// E' presente un'attività "Studiare", la cui durata è 1 secondo
		List<List<IAttività>> giorni = t.getGiorniAttività(1);
		IAttività a = giorni.get(0).get(0);
		assertEquals("Studiare", a.getNome());
		assertEquals(1, a.getDurata());
	}

}
