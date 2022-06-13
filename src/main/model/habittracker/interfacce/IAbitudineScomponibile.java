package main.model.habittracker.interfacce;

import java.util.List;

import main.model.goalmanager.interfacce.IItem;

/**
 * 
 * Habit that is composable of smaller items.
 *
 */
public interface IAbitudineScomponibile extends IAbitudine {
	
	/**
	 * 
	 * @param item to add
	 */
	public void addItem(IItem item);
	
	/**
	 * 
	 * @param idItem of the item to remove
	 */
	public void removeItem(String idItem);
	
	/**
	 * 
	 * @return list of items
	 */
	public List<IItem> getItems();
	
}
