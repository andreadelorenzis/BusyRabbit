package main.controller.timetracker;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.application.Platform;
import main.model.timetracker.classi.Activity;
import main.model.timetracker.classi.PomodoroTimer;
import main.model.timetracker.classi.TimeTracker;
import main.model.timetracker.classi.TrackerType;
import main.model.timetracker.interfacce.IActivity;
import main.model.timetracker.interfacce.IPomodoroTimer;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITimeTracker;
import main.model.timetracker.interfacce.ITrackable;
import main.model.timetracker.interfacce.ITracker;
import main.views.IView;
import main.views.timetracker.interfacce.ITimeTrackerView;

public class TimeTrackerController implements ITimeTrackerController, ITrackable {

    /*
     * Istanza del TimeTracker dell'app.
     */
    private ITimeTracker tt = TimeTracker.getInstance();
	
	/*
	 * Pagina corrente della cronologia attivitā.
	 */
	private int pagina = 1;
    
	/*
	 * View collegata a questo controller.
	 */
    private ITimeTrackerView view;
    
    private void aggiornaView() {
    	view.aggiornaView(tt.getGiorniAttivitā(pagina), pagina);
    }
    
	@Override
	public void setView(IView v) {
		this.view = (ITimeTrackerView) v;
	}

	@Override
	public IView getView() {
		return (IView) this.view;
	}
    
	@Override
	public void timerTerminato(long tempo) {
		Platform.runLater(() -> {			
//			IAttivitā a = tt.getAttivitāCorrente();
//			a.setDurata(tempo);
			view.successo("Attivitā aggiunta");
			aggiornaView();
		});
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
		Platform.runLater(() -> {
			view.visualizzaOrologio(o, m, s);
		});
	}

	@Override
	public void aggiungiProgetto(IProgetto p) {
		tt.aggiungiProgetto(p);
		view.successo("Progetto aggiunto");
		aggiornaView();
	}

	@Override
	public void modificaProgetto(IProgetto p1, IProgetto p2) {
		p1.setNome(p2.getNome());
		p1.setColore(p2.getColore());
		view.successo("Progetto modificato");
		aggiornaView();
	}

	@Override
	public void eliminaProgetto(IProgetto p) {
		tt.eliminaProgetto(p.getId());
		view.info("Progetto eliminato");
		aggiornaView();
	}

	@Override
	public void aggiungiAttivitā(IActivity a) {
		tt.aggiungiAttivitā(a);
		view.successo("Attivitā aggiunta");
		aggiornaView();
	}

	@Override
	public void modificaAttivitā(IActivity a1, IActivity a2) {
		IProgetto nuovoProgetto = a2.getProgetto();
		IProgetto vecchioProgetto = a1.getProgetto();

		if(!(vecchioProgetto.getId().equals(nuovoProgetto.getId()))) {
			// elimino durata dal vecchio progetto
			vecchioProgetto.eliminaAttivitā(a1);
			
			// aggiungo durata al nuovo progetto
			nuovoProgetto.aggiungiAttivitā(a1);
			
			// cambio il progetto nell'attivitā
			a1.setProgettoPadre(nuovoProgetto);
		}
		
		this.tt.eliminaAttivitā(a1);
		this.tt.aggiungiAttivitā(a2);
		
		view.successo("Attivitā modificata");
		aggiornaView();
	}

	@Override
	public void eliminaAttivitā(IActivity a) {
		a.getProgetto().eliminaAttivitā(a);
		tt.eliminaAttivitā(a);
		view.info("Attivitā eliminata");
		aggiornaView();
	}

	@Override
	public void incrementaPagina() {
    	if(pagina * 10 < tt.getNumGiorni()) {
    		pagina++;
    		aggiornaView();
    	}
	}

	@Override
	public void decrementaPagina() {
	  	if(pagina > 1) {
    		pagina--;
    		aggiornaView();
    	}
	}
	
	@Override
	public int getPagina() {
		return pagina;
	}

	@Override
	public void avviaTracker(String nome, IProgetto p) {
		IActivity a = new Activity(nome, LocalDate.now(), LocalTime.now(), 0L, p);
		tt.avviaTracker(a);
	}

	@Override
	public void terminaTracker() {
		tt.terminaTracker();
		ITracker tracker = tt.getTracker();
		if(!(tracker instanceof PomodoroTimer && 
			 !((IPomodoroTimer) tracker).getInSessione())) {			
			view.aggiornaView(tt.getGiorniAttivitā(pagina), pagina);
			view.successo("Attivitā aggiunta");
		}
	}

	@Override
	public void scegliCronometro() {
		tt.scegliTracker(TrackerType.CRONOMETRO);
		tt.getTracker().registraAscoltatore(this);
	}

	@Override
	public void scegliPomodoro() {
		tt.scegliTracker(TrackerType.POMODOROTIMER);
		tt.getTracker().registraAscoltatore(this);
	}

	@Override
	public void impostaPomodoroTimer(int sessione, int pausaBreve, int pausaLunga) {
		IPomodoroTimer pt = (IPomodoroTimer) tt.getTracker();
		pt.setSessione(sessione);
		pt.setPausaBreve(pausaBreve);
		pt.setPausaLunga(pausaLunga);
		view.successo("Pomodoro timer modificato");
		aggiornaView();
	}
    
}
