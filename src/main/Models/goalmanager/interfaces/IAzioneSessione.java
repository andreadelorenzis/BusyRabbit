package main.Models.goalmanager.interfaces;

import main.Models.timetracker.classes.TimerSemplice;

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
     * Fa terminare il timer.
     */
    public void terminaSessione();
    
    /**
     * 
     * @return la durata della sessione
     */
    public int getDurata();
    
    /**
     * 
     * @return il timer associato a questa azione
     */
    public TimerSemplice getTimer();
    
    /**
     * 
     * @param durata
     */
    public void setDurata(int durata);
    
}
