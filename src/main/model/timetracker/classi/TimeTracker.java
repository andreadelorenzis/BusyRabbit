package main.model.timetracker.classi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import main.model.timetracker.interfacce.IActivity;
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
	 * Progetto di default in cui vanno le attivitą senza progetto
	 */
	public final static IProgetto progettoDefault = new Progetto("Altro", Colore.Grigio);
	
	/*
	 * Lista di progetti
	 */
	private List<IProgetto> progetti = new ArrayList<>();
	
	/*
	 * Lista di attivitą
	 */
	private List<IActivity> attivitą = new ArrayList<>();
	
	/*
	 * Lista degli anni di monitoraggio.
	 */
	private Map<Integer, AnnoTracking> anniAttivitą = new TreeMap<>(Collections.reverseOrder());
	
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
	 * Attivitą attualmente monitorata
	 */
	private IActivity attivitąCorrente = null;
	
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
	 * Crea un GiornoAttivitą inserendovi l'attivitą
	 */
	private GiornoTracking creaGiorno(IActivity a) {
		// creo il giorno e vi aggiungo l'attivitą
		GiornoTracking g = new GiornoTracking(a.getData().getDayOfMonth());
		g.aggiungiAttivitą(a);
		this.attivitą.add(a);
		a.getProgetto().aggiungiAttivitą(a);
		nGiorni++;
		return g;
	}
	
	/*
	 * Crea un MeseAttivitą inserendovi l'attivitą
	 */
	private MeseTracking creaMese(IActivity a) {
		// creo il giorno
		GiornoTracking g = creaGiorno(a);
		
		// creo il mese e vi aggiungo il giorno
		MeseTracking m = new MeseTracking(a.getData().getMonth());
		m.aggiungiGiorno(g);
		return m;
	}
	
	/*
	 * Crea un AnnoAttivitą inserendovi l'attivitą
	 */
	private AnnoTracking creaAnno(IActivity a) {
		// creo il mese
		MeseTracking m = creaMese(a);
		
		// creo l'anno e vi aggiungo il mese
		AnnoTracking anno = new AnnoTracking(a.getData().getYear());
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
	public void avviaTracker(IActivity a) {
		attivitąCorrente = a;
		tracker.avvia();	
	}

	@Override
	public void terminaTracker() {
		Long durata = tracker.termina();
		if(!(this.tracker instanceof PomodoroTimer && 
			!((IPomodoroTimer) tracker).getInSessione())) {
			attivitąCorrente.setDurata(durata);
			aggiungiAttivitą(attivitąCorrente);
		}
	}

	@Override
	public void timerTerminato(long durata) {
		attivitąCorrente.setDurata(durata);
		aggiungiAttivitą(attivitąCorrente);
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
	public void aggiungiAttivitą(IActivity attivitą) {
		int anno = attivitą.getData().getYear();
		
		// se l'anno esiste
		if(anniAttivitą.containsKey(anno)) {
			AnnoTracking annoAttivitą = anniAttivitą.get(anno);
			int mese = attivitą.getData().getMonthValue();
			// se l'anno contiene il mese
			if(annoAttivitą.getMesi().containsKey(mese)) {
				MeseTracking meseAttivitą = annoAttivitą.getMesi().get(mese);
				int giorno = attivitą.getData().getDayOfMonth();
				
				// se il mese contiene il giorno
				if(meseAttivitą.getGiorni().containsKey(giorno)) {
					GiornoTracking giornoAttivitą = meseAttivitą.getGiorni().get(giorno);
					
					// aggiungi attivitą al giorno
					giornoAttivitą.aggiungiAttivitą(attivitą);
					// aggiungi attivitą alla lista di attivitą
					this.attivitą.add(attivitą);
					// aggiunge durata al progetto
					attivitą.getProgetto().aggiungiAttivitą(attivitą);
				} else {
					// aggiungi un nuovo giorno attivitą
					meseAttivitą.aggiungiGiorno(creaGiorno(attivitą));
				}
			} else {
				// aggiungi un nuovo mese attivitą
				annoAttivitą.aggiungiMese(creaMese(attivitą));
			}
		} else {
			// aggiungi un nuovo anno
			anniAttivitą.put(anno, creaAnno(attivitą));
		}
	}

	@Override
	public void eliminaAttivitą(IActivity a) {
		int anno = a.getData().getYear();
		int mese = a.getData().getMonthValue();
		int giorno = a.getData().getDayOfMonth();
		
		AnnoTracking aAttivitą = anniAttivitą.get(anno);
		MeseTracking mAttivitą = aAttivitą.getMesi().get(mese);
		GiornoTracking gAttivitą = mAttivitą.getGiorni().get(giorno);
		
		// rimuove attivitą dal GiornoAttivitą
		gAttivitą.rimuoviAttivitą(a.getId());
		
		// se il la lista di attivitą del giorno č vuota, rimuove il GiornoAttivitą
		if(gAttivitą.getAttivitą().isEmpty()) {
			mAttivitą.rimuoviGiorno(giorno);
			nGiorni--;
		}
		
		// se non ci sono giorni nel mese, rimuove il MeseAttivitą
		if(mAttivitą.getGiorni().keySet().isEmpty()) {
			aAttivitą.rimuoviMese(mese);
		}
		
		// se non ci sono mesi nell'anno, rimuove l'AnnoAttivitą
		if(aAttivitą.getMesi().keySet().isEmpty()) {
			anniAttivitą.remove(anno);
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
	public List<List<IActivity>> getGiorniAttivitą(int pagina) {
		List<List<IActivity>> listaGiorni = new ArrayList<>();
		int conteggioGiorni = 0;	
		for(int anno : anniAttivitą.keySet()) {
			AnnoTracking aAttivitą = anniAttivitą.get(anno);
			for(int mese : aAttivitą.getMesi().keySet()) {
				MeseTracking mAttivitą = aAttivitą.getMesi().get(mese);
				for(int giorno : mAttivitą.getGiorni().keySet()) {
					GiornoTracking gAttivitą = mAttivitą.getGiorni().get(giorno);
					if(conteggioGiorni >= (pagina * 10) - 10 && conteggioGiorni < pagina * 10) {
						List<IActivity> listaAttivitą = new ArrayList<>(gAttivitą.getAttivitą());
						listaGiorni.add(listaAttivitą);
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
		for(int anno : anniAttivitą.keySet()) {
			AnnoTracking aAttivitą = anniAttivitą.get(anno);
			for(int mese : aAttivitą.getMesi().keySet()) {
				MeseTracking mAttivitą = aAttivitą.getMesi().get(mese);
				for(int giorno : mAttivitą.getGiorni().keySet()) {
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
	public List<IActivity> getAttivitą() {
		return attivitą;
	}
	
	@Override
	public ITracker getTracker() {
		return tracker;
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
		
	}

	@Override
	public IActivity getAttivitąCorrente() {
		return this.attivitąCorrente;
	}

}
