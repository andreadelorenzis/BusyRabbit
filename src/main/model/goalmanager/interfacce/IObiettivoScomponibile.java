
package main.model.goalmanager.interfacce;

import java.util.List;

/**
 * Obiettivo a cui è possibile assegnare dei sotto-obiettivi. Questi sotto-obiettivi possono essere sia altri 
 * obiettivi "scomponibili" e sia obiettivi "azione".
 */
public interface IObiettivoScomponibile extends IObiettivo {
	
    /**
     * Verifica che tutti i sotto-obiettivi siano stati completati
     */
    public void verificaCompletamento();
	
	/**
	 * Completa tutti i sotto-obiettivi
	 */
	public void completaSottoObiettivi();
    
    /**
     * Aggiunge un sotto-obiettivo al seguente obiettivo scomponibile. 
     * 
     * @param obiettivo 
     */
    public void aggiungiSottoObiettivo(IObiettivo obiettivo);
    
    /**
     * @return sotto-obiettivi.
     */
    public List<IObiettivo> getSottoObiettivi();
    
    /**
     * 
     * @param idObiettivo identificativo del sotto-obiettivo da eliminare
     */
    public void eliminaSottoObiettivo(String idObiettivo);
    
}
