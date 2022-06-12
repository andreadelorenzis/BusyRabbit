package main.model.habittracker.interfacce;

import java.util.List;

import main.model.goalmanager.interfacce.Item;

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
	public void addItem(Item item);
	
	/**
	 * 
	 * @param idItem of the item to remove
	 */
	public void removeItem(String idItem);
	
	/**
	 * 
	 * @return list of items
	 */
	public List<Item> getItems();
	
}
