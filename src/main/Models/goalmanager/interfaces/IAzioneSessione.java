package main.Models.goalmanager.interfaces;

import main.Models.timetracker.classes.Timer;

/**
 * Azione associata ad una durata di tempo. L'azione può essere completata al termine di un timer
 * settato con la durata dell'azione (Es: studiare 2 ore)
 */
public interface IAzioneSessione extends IAzione {
    
    /**
     * Fa partire il timer con la durata di tempo impostata per l'azione.
     */
    public void avviaSessione();
    
    /**
     * Mette in pausa il timer.
     */
    public void sospendiSessione();
    
    /**
     * 
     * @return la durata della sessione
     */
    public double getDurata();
    
    /**
     * 
     * @return il timer associato a questa azione
     */
    public Timer getTimer();
    
    /**
     * 
     * @param durata
     */
    public void setDurata(double durata);
    
}
