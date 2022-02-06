package main.Models.goalmanager.interfaces;

/**
 * Item da completare che può essere assegnato ad un'AzioneScomponibile. 
 */
public interface Item {
    
    /**
     * Completa l'item
     */
    public void completa();
    
    /**
     * 
     * @return se l'item è completato
     */
    public boolean getCompletato();
    
    /**
     * 
     * @return il nome dell'item
     */
    public String getNome();
    
    /**
     * 
     * @return l'id dell'item
     */
    public String getId();
    
    /**
     * 
     * @param nome il nome dell'item
     */
    public void setNome(String nome);
    
    /**
     * L'AzioneScomponibile di cui questo item fa parte
     * 
     * @return azione
     */
    public Object getPadre();
    
    /**
     * 
     * @param azione 
     */
    public void setPadre(Object obj);
    
}
