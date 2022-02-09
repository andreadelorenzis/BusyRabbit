package main.Models.timetracker.classes;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import main.Colori;
import main.Models.timetracker.interfaces.IAttivitą;
import main.Models.timetracker.interfaces.ICronometro;
import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;
import main.Models.timetracker.interfaces.ITrackable;
import main.Models.timetracker.interfaces.ITracker;

public class TimeTracker implements ITimeTracker, ITrackable {
	
    //------------------------------- CAMPI ----------------------------------	
	/*
	 * Progetto di default in cui vanno le attivitą senza progetto
	 */
	public static IProgetto progettoDefault;
	
	/*
	 * Lista di progetti
	 */
	private List<IProgetto> progetti = new ArrayList<>();
	
	/*
	 * Lista di attivitą
	 */
	private List<IAttivitą> attivitą = new ArrayList<>();
	
	/*
	 * Lista degli anni di monitoraggio.
	 */
	private Map<Integer, AnnoAttivitą> anni = new TreeMap<>(Collections.reverseOrder());
	
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
	private IAttivitą attivitąCorrente = null;
	
	/*
	 * Numero totale di giorni monitorati
	 */
	private int nGiorni = 0;
	
	private static TimeTracker timeTracker = null;
	
    //---------------------------- COSTRUTTORI -------------------------------
	private TimeTracker() {
		progettoDefault = new Progetto("Altro", Colori.Grigio);
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
	 * Crea un GiornoAttivitą inserendovi l'attivitą
	 */
	private GiornoAttivitą creaGiorno(IAttivitą a) {
		// creo il giorno e vi aggiungo l'attivitą
		GiornoAttivitą g = new GiornoAttivitą(a.getData().getDayOfMonth());
		g.aggiungiAttivitą(a);
		this.attivitą.add(a);
		a.getProgetto().aggiungiDurata(a);
		nGiorni++;
		return g;
	}
	
	/*
	 * Crea un MeseAttivitą inserendovi l'attivitą
	 */
	private MeseAttivitą creaMese(IAttivitą a) {
		// creo il giorno
		GiornoAttivitą g = creaGiorno(a);
		
		// creo il mese e vi aggiungo il giorno
		MeseAttivitą m = new MeseAttivitą(a.getData().getMonth());
		m.aggiungiGiorno(g);
		return m;
	}
	
	/*
	 * Crea un AnnoAttivitą inserendovi l'attivitą
	 */
	private AnnoAttivitą creaAnno(IAttivitą a) {
		// creo il mese
		MeseAttivitą m = creaMese(a);
		
		// creo l'anno e vi aggiungo il mese
		AnnoAttivitą anno = new AnnoAttivitą(a.getData().getYear());
		anno.aggiungiMese(m);
		return anno;
	}
	
    //-------------------------- METODI PUBBLICI ----------------------------
	@Override
	public void avviaTracker(IAttivitą a) {
		attivitąCorrente = a;
		tracker.avvia();	
	}

	@Override
	public void terminaTracker() {
		Long durata = tracker.termina();
		attivitąCorrente.setDurata(durata);
		aggiungiAttivitą(attivitąCorrente);
	}

	@Override
	public void timerTerminato(long durata) {
		attivitąCorrente.setDurata(durata);
		aggiungiAttivitą(attivitąCorrente);
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
	public void aggiungiAttivitą(IAttivitą attivitą) {
		int anno = attivitą.getData().getYear();
		
		// se l'anno esiste
		if(anni.containsKey(anno)) {
			AnnoAttivitą annoAttivitą = anni.get(anno);
			int mese = attivitą.getData().getMonthValue();
			// se l'anno contiene il mese
			if(annoAttivitą.getMesi().containsKey(mese)) {
				MeseAttivitą meseAttivitą = annoAttivitą.getMesi().get(mese);
				int giorno = attivitą.getData().getDayOfMonth();
				
				// se il mese contiene il giorno
				if(meseAttivitą.getGiorni().containsKey(giorno)) {
					GiornoAttivitą giornoAttivitą = meseAttivitą.getGiorni().get(giorno);
					
					// aggiungi attivitą al giorno
					giornoAttivitą.aggiungiAttivitą(attivitą);
					// aggiungi attivitą alla lista di attivitą
					this.attivitą.add(attivitą);
					// aggiunge durata al progetto
					attivitą.getProgetto().aggiungiDurata(attivitą);
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
			anni.put(anno, creaAnno(attivitą));
		}

	}

	@Override
	public void eliminaAttivitą(IAttivitą a) {
		int anno = a.getData().getYear();
		int mese = a.getData().getMonthValue();
		int giorno = a.getData().getDayOfMonth();
		
		AnnoAttivitą aAttivitą = anni.get(anno);
		MeseAttivitą mAttivitą = aAttivitą.getMesi().get(mese);
		GiornoAttivitą gAttivitą = mAttivitą.getGiorni().get(giorno);
		
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
	public List<List<IAttivitą>> getGiorniAttivitą(int pagina) {
		List<List<IAttivitą>> listaGiorni = new ArrayList<>();
		int conteggioGiorni = 0;	
		for(int anno : anni.keySet()) {
			AnnoAttivitą aAttivitą = anni.get(anno);
			for(int mese : aAttivitą.getMesi().keySet()) {
				MeseAttivitą mAttivitą = aAttivitą.getMesi().get(mese);
				for(int giorno : mAttivitą.getGiorni().keySet()) {
					GiornoAttivitą gAttivitą = mAttivitą.getGiorni().get(giorno);
					if(conteggioGiorni >= (pagina * 10) - 10 && conteggioGiorni < pagina * 10) {
						List<IAttivitą> listaAttivitą = new ArrayList<>(gAttivitą.getAttivitą());
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
		for(int anno : anni.keySet()) {
			AnnoAttivitą aAttivitą = anni.get(anno);
			for(int mese : aAttivitą.getMesi().keySet()) {
				MeseAttivitą mAttivitą = aAttivitą.getMesi().get(mese);
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
	public List<IAttivitą> getAttivitą() {
		return attivitą;
	}
	
	@Override
	public ITracker getTracker() {
		return tracker;
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
		
	}

}
