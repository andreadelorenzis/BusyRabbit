package main.controller.goalmanager;

import main.controller.IController;
import main.model.goalmanager.classi.Item;
import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IObiettivo;

public interface IGoalManagerController extends IController {
	
	public void aggiungiObiettivo(IObiettivo o);
	
	public void aggiungiSottoObiettivo(IObiettivo padre, IObiettivo figlio);
	
	public void modificaObiettivo(IObiettivo o1, IObiettivo o2);
	
	public void eliminaObiettivo(IObiettivo o);
	
	public void collegaAzione(IObiettivo o, IAzione a);
	
	public void modificaAzione(IAzione a1, IAzione a2);
	
	public void eliminaAzione(IAzione a);
	
	public void completaObiettivo(IObiettivo o);
	
	public void completaAzione(IAzione a);
	
	public void creaItem(IAzioneScomponibile a, Item item);
	
	public void completaItem(Item item);
}
