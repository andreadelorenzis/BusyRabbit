package main.Models.habittracker.interfaces;

import java.util.List;

import main.Models.goalmanager.interfaces.Item;

/**
 * 
 * Habit that is composable of smaller items.
 *
 */
public interface ISimpleHabit extends IHabit {
	
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
