package main.controller.goalmanager;

import main.model.goalmanager.classi.AzioneSessione;
import main.model.goalmanager.classi.GoalManager;
import main.model.goalmanager.classi.Item;
import main.model.goalmanager.classi.ObiettivoAzione;
import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IAzioneSessione;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.goalmanager.interfacce.IObiettivo;
import main.model.goalmanager.interfacce.IObiettivoAzione;
import main.model.goalmanager.interfacce.IObiettivoScomponibile;
import main.views.IView;
import main.views.goalmanager.interfacce.IGoalManagerView;

public class GoalManagerController implements IGoalManagerController {
    /*
     * Istanza del GoalManager dell'app.
     */
    private IGoalManager gm = GoalManager.getInstance();
    
	/*
	 * View collegata a questo controller.
	 */
    private IGoalManagerView view;
    
	@Override
	public void setView(IView v) {
		this.view = (IGoalManagerView) v;
	}

	@Override
	public IView getView() {
		return (IView) this.view;
	}

	@Override
	public void aggiungiObiettivo(IObiettivo o) {
		gm.aggiungiObiettivo(o);
		view.successo("Obiettivo creato");
		view.aggiornaObiettivi(gm.getObiettivi());
	}
	
	@Override
	public void aggiungiSottoObiettivo(IObiettivo padre, IObiettivo figlio) {
		IObiettivoScomponibile os = (IObiettivoScomponibile) padre;
		os.aggiungiSottoObiettivo(figlio);
		view.successo("Sotto-obiettivo creato");
		view.aggiornaObiettivi(gm.getObiettivi());
	}

	@Override
	public void modificaObiettivo(IObiettivo o1, IObiettivo o2) {
		o1.setNome(o2.getNome());
		o1.setDescrizione(o2.getDescrizione());
		o1.setData(o2.getData());
		o1.setObiettivoPadre(o2.getObiettivoPadre());
		
		if(o1 instanceof ObiettivoAzione && o2 instanceof ObiettivoAzione) {
			IObiettivoAzione oAzione1 = (IObiettivoAzione) o1;
			IObiettivoAzione oAzione2 = (IObiettivoAzione) o2;
			
			oAzione1.setUnita(oAzione2.getUnità());
			oAzione1.setValoreTotale(oAzione2.getValoreTotale());
		}
		
		view.successo("Obiettivo modificato");
		view.aggiornaObiettivi(gm.getObiettivi());
	}

	@Override
	public void eliminaObiettivo(IObiettivo o) {
		gm.eliminaObiettivo(o.getId());
		view.info("Obiettivo eliminato");
		view.aggiornaObiettivi(gm.getObiettivi());
	}

	@Override
	public void collegaAzione(IObiettivo o, IAzione a) {
		IObiettivoAzione oa = (IObiettivoAzione) o;
		oa.collegaAzione(a);
		a.setObiettivo(oa);
		view.successo("Azione collegata");
		view.aggiornaObiettivi(gm.getObiettivi());
	}

	@Override
	public void modificaAzione(IAzione a1, IAzione a2) {
		a1.setNome(a2.getNome());
		a1.setDataInizio(a2.getDataInizio());
		a1.setGiorniRipetizione(a2.getGiorniRipetizione());
		a1.setIncremento(a2.getIncremento());
		
		if(a1 instanceof AzioneSessione & a2 instanceof AzioneSessione) {
			IAzioneSessione aSessione1 = (IAzioneSessione) a1;
			IAzioneSessione aSessione2 = (IAzioneSessione) a2;
			aSessione1.setDurata(aSessione2.getDurata());
		}
		
		view.successo("Azione modificata");
	}

	@Override
	public void eliminaAzione(IAzione a) {
		a.getObiettivo().eliminaAzione(a.getId());
		view.info("Azione eliminata");
		view.aggiornaObiettivi(gm.getObiettivi());
	}

	@Override
	public void completaObiettivo(IObiettivo o) {
		o.completa();
		
		if(o.getCompletato()) {
			view.successo("Obiettivo completato");
		}
		view.aggiornaObiettivi(gm.getObiettivi());
	}

	@Override
	public void completaAzione(IAzione a) {
		a.completa();
		
		if(a.getCompletata()) {
			view.successo("Azione completata");
		}
		view.aggiornaObiettivi(gm.getObiettivi());
	}
	
	@Override 
	public void creaItem(IAzioneScomponibile azione, Item item) {
		azione.aggiungiItem(item);
		view.successo("Item creato");
		view.aggiornaObiettivi(gm.getObiettivi());
	}
    
	@Override
	public void completaItem(Item item) {
		item.completa();
	}
	
	@Override
	public void eliminaItem(IAzioneScomponibile a, Item item) {
		a.eliminaItem(item.getId());
		view.info("Item eliminato");
		view.aggiornaObiettivi(gm.getObiettivi());
	}
	
	@Override
	public void avviaAzioneSessione(IAzioneSessione a) {
		if(!a.getAvviato())
			a.avviaSessione();
	}
	
	@Override
	public void terminaAzioneSessione(IAzioneSessione a) {
		if(a.getAvviato()) 
			a.terminaSessione();
	}

}
