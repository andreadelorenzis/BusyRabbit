package main.Models.goalmanager.interfaces;

import java.util.List;

/**
 * Azione suddivisibile ulteriormente in degli item. Il completamento degli item NON vieta all'utente
 * di completare l'azione direttamente.
 */
public interface IAzioneScomponibile extends IAzione {
    
    /**
     * 
     * @param item l'item da aggiungere
     */
    public void aggiungiItem(Item item);
    
    /**
     * 
     * @param idItem l'item da eliminare
     */
    public void eliminaItem(String idItem);
    
    /**
     * 
     * @return la lista degli item di questa AzioneScomponibile
     */
    public List<Item> getItems();
    
}
