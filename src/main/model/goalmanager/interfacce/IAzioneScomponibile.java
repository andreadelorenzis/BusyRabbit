package main.model.goalmanager.interfacce;

import java.util.List;

/**
 * Azione suddivisibile ulteriormente in degli item. Il completamento degli item non vieta all'utente
 * di completare l'azione direttamente.
 */
public interface IAzioneScomponibile extends IAzione {
    
    /**
     * 
     * @param item l'item da aggiungere
     */
    public void aggiungiItem(IItem item);
    
    /**
     * 
     * @param idItem l'item da eliminare
     */
    public void eliminaItem(String idItem);
    
    /**
     * 
     * @return la lista degli item di questa AzioneScomponibile
     */
    public List<IItem> getItems();
    
}
