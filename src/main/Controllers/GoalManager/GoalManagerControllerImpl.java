package main.Controllers.GoalManager;

import main.Models.goalmanager.classes.GoalManager;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;
import main.Views.GoalManager.interfaces.GoalManagerView;

public class GoalManagerControllerImpl implements GoalManagerController {
    /*
     * Istanza del GoalManager dell'app.
     */
    private IGoalManager gm;
    
	/*
	 * View collegata a questo controller.
	 */
    private GoalManagerView view;
    
    public GoalManagerControllerImpl(GoalManagerView view) {
    	this.view = view;
    	this.gm = GoalManager.getInstance();
    }

	@Override
	public void aggiungiObiettivo(IObiettivo o) {
		gm.aggiungiObiettivo(o);
		view.successo("Obiettivo creato");
		view.aggiorna(gm.getObiettivi());
	}
	
	@Override
	public void aggiungiSottoObiettivo(IObiettivo padre, IObiettivo figlio) {
		IObiettivoScomponibile os = (IObiettivoScomponibile) padre;
		os.aggiungiSottoObiettivo(figlio);
		view.successo("Sotto-obiettivo creato");
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void modificaObiettivo(IObiettivo o1, IObiettivo o2) {
		o1.setNome(o2.getNome());
		o1.setDescrizione(o2.getDescrizione());
		o1.setData(o2.getData());
		o1.setObiettivoPadre(o2.getObiettivoPadre());
		view.successo("Obiettivo modificato");
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void eliminaObiettivo(IObiettivo o) {
		gm.eliminaObiettivo(o.getId());
		view.info("Obiettivo eliminato");
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void collegaAzione(IObiettivo o, IAzione a) {
		IObiettivoAzione oa = (IObiettivoAzione) o;
		oa.collegaAzione(a);
		a.setObiettivo(oa);
		view.successo("Azione collegata");
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void modificaAzione(IAzione a1, IAzione a2) {
		a1.setNome(a2.getNome());
		a1.setDataInizio(a2.getDataInizio());
		a1.setGiorniRipetizione(a2.getGiorniRipetizione());
		a1.setIncremento(a2.getIncremento());
		a1.setObiettivo(a2.getObiettivo());
		view.successo("Azione modificata");
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void eliminaAzione(IAzione a) {
		a.getObiettivo().eliminaAzione(a.getId());
		view.info("Azione eliminata");
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void completaObiettivo(IObiettivo o) {
		o.completa();
		
		if(o.getCompletato()) {
			view.successo("Obiettivo completato");
		}
		view.aggiorna(gm.getObiettivi());
	}

	@Override
	public void completaAzione(IAzione a) {
		a.completa();
		
		if(a.getCompletata()) {
			view.successo("Azione completata");
		}
		view.aggiorna(gm.getObiettivi());
	}
    
}
