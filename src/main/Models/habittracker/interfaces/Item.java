package main.Models.habittracker.interfaces;

/**
 * 
 * Item to complete that you can give to a SimpleHabit
 *
 */
public interface Item {
    
    /**
     * Complete the item
     */
    public void complete();
    
    /**
     * 
     * @return if the item is completed or not
     */
    public boolean isCompleted();
    
    /**
     * 
     * @return the name of the item
     */
    public String getName();
    
    /**
     * 
     * @return the id of the item
     */
    public String getId();
    
    /**
     * 
     * @param name
     */
    public void setName(String nome);
    
    /**
     * SimpleHabit that contains this item
     * 
     * @return habit
     */
    public ISimpleHabit getHabit();
    
    /**
     * 
     * @param habit 
     */
    public void setHabit(ISimpleHabit habit);
	
}
