package main.controller.timetracker;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.application.Platform;
import main.model.timetracker.classi.Attività;
import main.model.timetracker.classi.TimeTracker;
import main.model.timetracker.classi.TrackerType;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IPomodoroTimer;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITimeTracker;
import main.model.timetracker.interfacce.ITrackable;
import main.views.IView;
import main.views.timetracker.interfacce.ITimeTrackerView;

public class TimeTrackerController implements ITimeTrackerController, ITrackable {

    /*
     * Istanza del TimeTracker dell'app.
     */
    private ITimeTracker tt = TimeTracker.getInstance();
	
	/*
	 * Pagina corrente della cronologia attività.
	 */
	private int pagina = 1;
    
	/*
	 * View collegata a questo controller.
	 */
    private ITimeTrackerView view;
    
    private void aggiornaView() {
    	view.aggiornaView(tt.getGiorniAttività(pagina), pagina);
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
		view.trackerTerminato();
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
		aggiornaView();
	}

	@Override
	public void modificaProgetto(IProgetto p1, IProgetto p2) {
		p1.setNome(p2.getNome());
		p1.setColore(p2.getColore());
		aggiornaView();
	}

	@Override
	public void eliminaProgetto(IProgetto p) {
		tt.eliminaProgetto(p.getId());
		aggiornaView();
	}

	@Override
	public void aggiungiAttività(IAttività a) {
		tt.aggiungiAttività(a);
		aggiornaView();
	}

	@Override
	public void modificaAttività(IAttività a1, IAttività a2) {
		String nome = a2.getNome();
		IProgetto nuovoProgetto = a2.getProgetto();
		long durata = a2.getDurata();
		IProgetto vecchioProgetto = a1.getProgetto();
		a1.setNome(nome);
		a1.setDurata(durata);
		if(!(vecchioProgetto.getId().equals(nuovoProgetto.getId()))) {
			// elimino durata dal vecchio progetto
			vecchioProgetto.eliminaAttività(a1);
			
			// aggiungo durata al nuovo progetto
			nuovoProgetto.aggiungiAttività(a1);
			
			// cambio il progetto nell'attività
			a1.setProgettoPadre(nuovoProgetto);
		}
		aggiornaView();
	}

	@Override
	public void eliminaAttività(IAttività a) {
		// elimina durata dal progetto
		a.getProgetto().eliminaAttività(a);
		
		// elimina attività dal modello
		tt.eliminaAttività(a);
		
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
		IAttività a = new Attività(nome, LocalDate.now(), LocalTime.now(), 0L, p);
		tt.avviaTracker(a);
		tt.getTracker().setAscoltatore(this);
	}

	@Override
	public void terminaTracker() {
		tt.terminaTracker();
		view.aggiornaView(tt.getGiorniAttività(pagina), pagina);
	}

	@Override
	public void scegliCronometro() {
		tt.scegliTracker(TrackerType.CRONOMETRO);
	}

	@Override
	public void scegliPomodoro() {
		tt.scegliTracker(TrackerType.POMODOROTIMER);
	}

	@Override
	public void impostaPomodoroTimer(int sessione, int pausaBreve, int pausaLunga) {
		IPomodoroTimer pt = (IPomodoroTimer) tt.getTracker();
		pt.setSessione(sessione);
		pt.setPausaBreve(pausaBreve);
		pt.setPausaLunga(pausaLunga);
		aggiornaView();
	}
    
}
