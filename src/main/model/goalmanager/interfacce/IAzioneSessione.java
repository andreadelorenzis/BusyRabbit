package main.model.goalmanager.interfacce;

import main.model.timetracker.classi.TimerSemplice;

/**
 * Azione associata ad una durata di tempo. L'azione può essere completata al termine di un timer
 * impostato con la durata dell'azione (Es: studiare 2 ore)
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
     * @param durata durata del timer in minuti
     */
    public void setDurata(int durata);
    
    /**
     * 
     * @return se il timer è avviato o meno
     */
    public boolean getAvviato();
    
    /**
     * Crea un nuovo TimerSemplice
     */
    public TimerSemplice nuovoTimer(int durata);
    
}
