package main.Controllers.GoalManager;

import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IObiettivo;

public interface GoalManagerController {
	public void aggiungiObiettivo(IObiettivo o);
	
	public void aggiungiSottoObiettivo(IObiettivo padre, IObiettivo figlio);
	
	public void modificaObiettivo(IObiettivo o1, IObiettivo o2);
	
	public void eliminaObiettivo(IObiettivo o);
	
	public void collegaAzione(IObiettivo o, IAzione a);
	
	public void modificaAzione(IAzione a1, IAzione a2);
	
	public void eliminaAzione(IAzione a);
	
	public void completaObiettivo(IObiettivo o);
	
	public void completaAzione(IAzione a);
}
