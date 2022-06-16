package main.controller.habittracker;

import main.controller.IController;
import main.model.goalmanager.classi.Item;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineScomponibile;
import main.model.habittracker.interfacce.IAbitudineSessione;

/**
 * Controller collegato a HabiTrackerView
 */
public interface IHabitTrackerController extends IController {
	
	public void aggiungiAbitudine(IAbitudine h);
	
	public void modificaAbitudine(IAbitudine h1, IAbitudine h2);
	
	public void eliminaAbitudine(IAbitudine h);
	
	public void completaAbitudine(IAbitudine h);
	
	public void creaItem(IAbitudineScomponibile a, Item item);
	
	public void completaItem(Item item);
	
	public void eliminaItem(IAbitudineScomponibile a, Item i);
	
	public void avviaAzioneSessione(IAbitudineSessione a);
	
	public void terminaAzioneSessione(IAbitudineSessione a);
	
}
