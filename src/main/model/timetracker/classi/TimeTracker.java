package main.model.timetracker.classi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import main.model.timetracker.interfacce.IAttivit‡;
import main.model.timetracker.interfacce.ICronometro;
import main.model.timetracker.interfacce.IPomodoroTimer;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITimeTracker;
import main.model.timetracker.interfacce.ITrackable;
import main.model.timetracker.interfacce.ITracker;
import main.views.Colore;

public class TimeTracker implements ITimeTracker, ITrackable {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Progetto di default in cui vanno le attivit‡ senza progetto
	 */
	public final static IProgetto progettoDefault = new Progetto("Altro", Colore.Grigio);
	
	/*
	 * Lista di progetti
	 */
	private List<IProgetto> progetti = new ArrayList<>();
	
	/*
	 * Lista di attivit‡
	 */
	private List<IAttivit‡> attivit‡ = new ArrayList<>();
	
	/*
	 * Lista degli anni di monitoraggio.
	 */
	private Map<Integer, AnnoAttivit‡> anniAttivit‡ = new TreeMap<>(Collections.reverseOrder());
	
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
	 * Attivit‡ attualmente monitorata
	 */
	private IAttivit‡ attivit‡Corrente = null;
	
	/*
	 * Numero totale di giorni monitorati
	 */
	private int nGiorni = 0;
	
	/*
	 * SINGLETONE
	 */
	private static TimeTracker timeTracker = null;
	
    //---------------------------- COSTRUTTORI -------------------------------
	private TimeTracker() {
		progetti.add(progettoDefault);
		cronometro = new Cronometro();
		pomodoroTimer = new PomodoroTimer();
		tracker = cronometro;
	}

    //--------------------------- METODI PRIVATI -----------------------------
	/*
	 * Crea un GiornoAttivit‡ inserendovi l'attivit‡
	 */
	private GiornoAttivit‡ creaGiorno(IAttivit‡ a) {
		// creo il giorno e vi aggiungo l'attivit‡
		GiornoAttivit‡ g = new GiornoAttivit‡(a.getData().getDayOfMonth());
		g.aggiungiAttivit‡(a);
		this.attivit‡.add(a);
		a.getProgetto().aggiungiAttivit‡(a);
		nGiorni++;
		return g;
	}
	
	/*
	 * Crea un MeseAttivit‡ inserendovi l'attivit‡
	 */
	private MeseAttivit‡ creaMese(IAttivit‡ a) {
		// creo il giorno
		GiornoAttivit‡ g = creaGiorno(a);
		
		// creo il mese e vi aggiungo il giorno
		MeseAttivit‡ m = new MeseAttivit‡(a.getData().getMonth());
		m.aggiungiGiorno(g);
		return m;
	}
	
	/*
	 * Crea un AnnoAttivit‡ inserendovi l'attivit‡
	 */
	private AnnoAttivit‡ creaAnno(IAttivit‡ a) {
		// creo il mese
		MeseAttivit‡ m = creaMese(a);
		
		// creo l'anno e vi aggiungo il mese
		AnnoAttivit‡ anno = new AnnoAttivit‡(a.getData().getYear());
		anno.aggiungiMese(m);
		return anno;
	}
	
    //-------------------------- METODI PUBBLICI ----------------------------
	public static TimeTracker getInstance() {
		if(timeTracker == null) {
			timeTracker = new TimeTracker();
		}
		return timeTracker;
	}
	
	@Override
	public void avviaTracker(IAttivit‡ a) {
		attivit‡Corrente = a;
		tracker.avvia();	
	}

	@Override
	public void terminaTracker() {
		Long durata = tracker.termina();
		if(!(this.tracker instanceof PomodoroTimer && 
			!((IPomodoroTimer) tracker).getInSessione())) {
			attivit‡Corrente.setDurata(durata);
			aggiungiAttivit‡(attivit‡Corrente);
		}
	}

	@Override
	public void timerTerminato(long durata) {
		attivit‡Corrente.setDurata(durata);
		aggiungiAttivit‡(attivit‡Corrente);
	}
	
	@Override
	public void scegliTracker(TrackerType t) {	
		if(!tracker.getAvviato()) {
			if(t.equals(TrackerType.CRONOMETRO)) {
				tracker = cronometro;
			} else if(t.equals(TrackerType.POMODOROTIMER)) {
				tracker = pomodoroTimer;
			}
			tracker.registraAscoltatore(this);
		}
	}

	@Override
	public void aggiungiAttivit‡(IAttivit‡ attivit‡) {
		int anno = attivit‡.getData().getYear();
		
		// se l'anno esiste
		if(anniAttivit‡.containsKey(anno)) {
			AnnoAttivit‡ annoAttivit‡ = anniAttivit‡.get(anno);
			int mese = attivit‡.getData().getMonthValue();
			// se l'anno contiene il mese
			if(annoAttivit‡.getMesi().containsKey(mese)) {
				MeseAttivit‡ meseAttivit‡ = annoAttivit‡.getMesi().get(mese);
				int giorno = attivit‡.getData().getDayOfMonth();
				
				// se il mese contiene il giorno
				if(meseAttivit‡.getGiorni().containsKey(giorno)) {
					GiornoAttivit‡ giornoAttivit‡ = meseAttivit‡.getGiorni().get(giorno);
					
					// aggiungi attivit‡ al giorno
					giornoAttivit‡.aggiungiAttivit‡(attivit‡);
					// aggiungi attivit‡ alla lista di attivit‡
					this.attivit‡.add(attivit‡);
					// aggiunge durata al progetto
					attivit‡.getProgetto().aggiungiAttivit‡(attivit‡);
				} else {
					// aggiungi un nuovo giorno attivit‡
					meseAttivit‡.aggiungiGiorno(creaGiorno(attivit‡));
				}
			} else {
				// aggiungi un nuovo mese attivit‡
				annoAttivit‡.aggiungiMese(creaMese(attivit‡));
			}
		} else {
			// aggiungi un nuovo anno
			anniAttivit‡.put(anno, creaAnno(attivit‡));
		}
	}

	@Override
	public void eliminaAttivit‡(IAttivit‡ a) {
		int anno = a.getData().getYear();
		int mese = a.getData().getMonthValue();
		int giorno = a.getData().getDayOfMonth();
		
		AnnoAttivit‡ aAttivit‡ = anniAttivit‡.get(anno);
		MeseAttivit‡ mAttivit‡ = aAttivit‡.getMesi().get(mese);
		GiornoAttivit‡ gAttivit‡ = mAttivit‡.getGiorni().get(giorno);
		
		// rimuove attivit‡ dal GiornoAttivit‡
		gAttivit‡.rimuoviAttivit‡(a.getId());
		
		// se il la lista di attivit‡ del giorno Ë vuota, rimuove il GiornoAttivit‡
		if(gAttivit‡.getAttivit‡().isEmpty()) {
			mAttivit‡.rimuoviGiorno(giorno);
			nGiorni--;
		}
		
		// se non ci sono giorni nel mese, rimuove il MeseAttivit‡
		if(mAttivit‡.getGiorni().keySet().isEmpty()) {
			aAttivit‡.rimuoviMese(mese);
		}
		
		// se non ci sono mesi nell'anno, rimuove l'AnnoAttivit‡
		if(aAttivit‡.getMesi().keySet().isEmpty()) {
			anniAttivit‡.remove(anno);
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
	public List<List<IAttivit‡>> getGiorniAttivit‡(int pagina) {
		List<List<IAttivit‡>> listaGiorni = new ArrayList<>();
		int conteggioGiorni = 0;	
		for(int anno : anniAttivit‡.keySet()) {
			AnnoAttivit‡ aAttivit‡ = anniAttivit‡.get(anno);
			for(int mese : aAttivit‡.getMesi().keySet()) {
				MeseAttivit‡ mAttivit‡ = aAttivit‡.getMesi().get(mese);
				for(int giorno : mAttivit‡.getGiorni().keySet()) {
					GiornoAttivit‡ gAttivit‡ = mAttivit‡.getGiorni().get(giorno);
					if(conteggioGiorni >= (pagina * 10) - 10 && conteggioGiorni < pagina * 10) {
						List<IAttivit‡> listaAttivit‡ = new ArrayList<>(gAttivit‡.getAttivit‡());
						listaGiorni.add(listaAttivit‡);
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
		for(int anno : anniAttivit‡.keySet()) {
			AnnoAttivit‡ aAttivit‡ = anniAttivit‡.get(anno);
			for(int mese : aAttivit‡.getMesi().keySet()) {
				MeseAttivit‡ mAttivit‡ = aAttivit‡.getMesi().get(mese);
				for(int giorno : mAttivit‡.getGiorni().keySet()) {
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
	public List<IAttivit‡> getAttivit‡() {
		return attivit‡;
	}
	
	@Override
	public ITracker getTracker() {
		return tracker;
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
		
	}

	@Override
	public IAttivit‡ getAttivit‡Corrente() {
		return this.attivit‡Corrente;
	}

}
