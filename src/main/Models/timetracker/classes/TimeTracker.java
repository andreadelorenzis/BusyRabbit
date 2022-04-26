package main.Models.timetracker.classes;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.ICronometro;
import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;
import main.Models.timetracker.interfaces.ITrackable;
import main.Models.timetracker.interfaces.ITracker;
import main.Views.Colore;

public class TimeTracker implements ITimeTracker, ITrackable {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Progetto di default in cui vanno le attività senza progetto
	 */
	public final static IProgetto progettoDefault = new Progetto("Altro", Colore.Grigio);
	
	/*
	 * Lista di progetti
	 */
	private List<IProgetto> progetti = new ArrayList<>();
	
	/*
	 * Lista di attività
	 */
	private List<IAttività> attività = new ArrayList<>();
	
	/*
	 * Lista degli anni di monitoraggio.
	 */
	private Map<Integer, AnnoAttività> anni = new TreeMap<>(Collections.reverseOrder());
	
	/*
	 * Istanza di Cronometro
	 */
	private ICronometro cronometro;
	
	/*
	 * Istanza di PomodoroTimer
	 */
	private IPomodoroTimer pomodoroTimer;
	
	/*
	 * Tracker selezionato (Pomodoro o Cronometro)
	 */
	private ITracker tracker;
	
	/*
	 * Attività attualmente monitorata
	 */
	private IAttività attivitàCorrente = null;
	
	/*
	 * Numero totale di giorni monitorati
	 */
	private int nGiorni = 0;
	
	private static TimeTracker timeTracker = null;
	
    //---------------------------- COSTRUTTORI -------------------------------
	private TimeTracker() {
		progetti.add(progettoDefault);
		cronometro = new Cronometro();
		pomodoroTimer = new PomodoroTimer();
		tracker = cronometro;
	}
	
	public static TimeTracker getInstance() {
		if(timeTracker == null) {
			timeTracker = new TimeTracker();
		}
		return timeTracker;
	}

    //--------------------------- METODI PRIVATI -----------------------------
	/*
	 * Crea un GiornoAttività inserendovi l'attività
	 */
	private GiornoAttività creaGiorno(IAttività a) {
		// creo il giorno e vi aggiungo l'attività
		GiornoAttività g = new GiornoAttività(a.getData().getDayOfMonth());
		g.aggiungiAttività(a);
		this.attività.add(a);
		a.getProgetto().aggiungiDurata(a);
		nGiorni++;
		return g;
	}
	
	/*
	 * Crea un MeseAttività inserendovi l'attività
	 */
	private MeseAttività creaMese(IAttività a) {
		// creo il giorno
		GiornoAttività g = creaGiorno(a);
		
		// creo il mese e vi aggiungo il giorno
		MeseAttività m = new MeseAttività(a.getData().getMonth());
		m.aggiungiGiorno(g);
		return m;
	}
	
	/*
	 * Crea un AnnoAttività inserendovi l'attività
	 */
	private AnnoAttività creaAnno(IAttività a) {
		// creo il mese
		MeseAttività m = creaMese(a);
		
		// creo l'anno e vi aggiungo il mese
		AnnoAttività anno = new AnnoAttività(a.getData().getYear());
		anno.aggiungiMese(m);
		return anno;
	}
	
    //-------------------------- METODI PUBBLICI ----------------------------
	@Override
	public void avviaTracker(IAttività a) {
		attivitàCorrente = a;
		tracker.avvia();	
	}

	@Override
	public void terminaTracker() {
		Long durata = tracker.termina();
		attivitàCorrente.setDurata(durata);
		aggiungiAttività(attivitàCorrente);
	}

	@Override
	public void timerTerminato(long durata) {
		attivitàCorrente.setDurata(durata);
		aggiungiAttività(attivitàCorrente);
	}
	
	@Override
	public void scegliTracker(TrackerEnum t) {	
		if(!tracker.getAvviato()) {
			if(t.equals(TrackerEnum.CRONOMETRO)) {
				tracker = cronometro;
			} else if(t.equals(TrackerEnum.POMODOROTIMER)) {
				tracker = pomodoroTimer;
			}
			tracker.setAscoltatore(this);
		}
	}

	@Override
	public void aggiungiAttività(IAttività attività) {
		int anno = attività.getData().getYear();
		
		// se l'anno esiste
		if(anni.containsKey(anno)) {
			AnnoAttività annoAttività = anni.get(anno);
			int mese = attività.getData().getMonthValue();
			// se l'anno contiene il mese
			if(annoAttività.getMesi().containsKey(mese)) {
				MeseAttività meseAttività = annoAttività.getMesi().get(mese);
				int giorno = attività.getData().getDayOfMonth();
				
				// se il mese contiene il giorno
				if(meseAttività.getGiorni().containsKey(giorno)) {
					GiornoAttività giornoAttività = meseAttività.getGiorni().get(giorno);
					
					// aggiungi attività al giorno
					giornoAttività.aggiungiAttività(attività);
					// aggiungi attività alla lista di attività
					this.attività.add(attività);
					// aggiunge durata al progetto
					attività.getProgetto().aggiungiDurata(attività);
				} else {
					// aggiungi un nuovo giorno attività
					meseAttività.aggiungiGiorno(creaGiorno(attività));
				}
			} else {
				// aggiungi un nuovo mese attività
				annoAttività.aggiungiMese(creaMese(attività));
			}
		} else {
			// aggiungi un nuovo anno
			anni.put(anno, creaAnno(attività));
		}
	}

	@Override
	public void eliminaAttività(IAttività a) {
		int anno = a.getData().getYear();
		int mese = a.getData().getMonthValue();
		int giorno = a.getData().getDayOfMonth();
		
		AnnoAttività aAttività = anni.get(anno);
		MeseAttività mAttività = aAttività.getMesi().get(mese);
		GiornoAttività gAttività = mAttività.getGiorni().get(giorno);
		
		// rimuove attività dal GiornoAttività
		gAttività.rimuoviAttività(a.getId());
		
		// se il la lista di attività del giorno è vuota, rimuove il GiornoAttività
		if(gAttività.getAttività().isEmpty()) {
			mAttività.rimuoviGiorno(giorno);
			nGiorni--;
		}
		
		// se non ci sono giorni nel mese, rimuove il MeseAttività
		if(mAttività.getGiorni().keySet().isEmpty()) {
			aAttività.rimuoviMese(mese);
		}
		
		// se non ci sono mesi nell'anno, rimuove l'AnnoAttività
		if(aAttività.getMesi().keySet().isEmpty()) {
			anni.remove(anno);
		}
	} 

	@Override
	public void aggiungiProgetto(IProgetto progetto) {
		if(!progetti.contains(progetto)) {
			progetti.add(progetto);
		}
	}

	@Override
	public void eliminaProgetto(String idProgetto) {
		progetti = progetti.stream()	
						   .filter(p -> !(p.getId().equals(idProgetto)))
						   .collect(Collectors.toList());
	}

	@Override
	public List<List<IAttività>> getGiorniAttività(int pagina) {
		List<List<IAttività>> listaGiorni = new ArrayList<>();
		int conteggioGiorni = 0;	
		for(int anno : anni.keySet()) {
			AnnoAttività aAttività = anni.get(anno);
			for(int mese : aAttività.getMesi().keySet()) {
				MeseAttività mAttività = aAttività.getMesi().get(mese);
				for(int giorno : mAttività.getGiorni().keySet()) {
					GiornoAttività gAttività = mAttività.getGiorni().get(giorno);
					if(conteggioGiorni >= (pagina * 10) - 10 && conteggioGiorni < pagina * 10) {
						List<IAttività> listaAttività = new ArrayList<>(gAttività.getAttività());
						listaGiorni.add(listaAttività);
					}
					conteggioGiorni++;
					
					if(conteggioGiorni >= pagina * 10 || conteggioGiorni == nGiorni) {
						return listaGiorni;
					}
				}
			}
		}
		
		return listaGiorni;
	}
	
	@Override 
	public int getNumGiorni() {
		int numGiorni = 0;
		for(int anno : anni.keySet()) {
			AnnoAttività aAttività = anni.get(anno);
			for(int mese : aAttività.getMesi().keySet()) {
				MeseAttività mAttività = aAttività.getMesi().get(mese);
				for(int giorno : mAttività.getGiorni().keySet()) {
					numGiorni++;
				}
			}
		}
		return numGiorni;
	}

	@Override
	public List<IProgetto> getProgetti() {
		return progetti;
	}
	
	@Override
	public List<IAttività> getAttività() {
		return attività;
	}
	
	@Override
	public ITracker getTracker() {
		return tracker;
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
		
	}

}
