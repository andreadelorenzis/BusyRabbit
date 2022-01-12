/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.goalmanager.interfaces;

import java.time.LocalDate;
import java.util.List;
import main.Giorno;

/**
 * Le azioni collegabili agli ObiettiviAzione. Queste azioni si attivano nei giorni scelti e 
 * completandole si incrementa l'obiettivo a cui sono collegate del valore specificato. Se 
 * l'azione non è attiva, non può essere completata.
 */
public interface IAzione {
    
    /**
     * Completa l'azione. Solo se l'azione è attiva.
     */
    public void completa();
    
    /**
     * 
     * @return se l'azione è stata completata o meno
     */
    public boolean getCompletata();
    
    /**
     * 
     * @return nome dell'azione
     */
    public String getNome();
    
    /**
     * 
     * @return valore di incremento dell'obiettivo quando l'azione viene collegata
     */
    public int getIncremento();
    
    /**
     * 
     * @return identificativo dell'azione
     */
    public String getId();
    
    /**
     * 
     * @return data di inizio dell'azione
     */
    public LocalDate getDataInizio();
    
    /**
     * 
     * @return i giorni in cui l'azione si ripete
     */
    public List<Giorno> getGiorniRipetizione();
    
    /**
     * 
     * @param nome nome dell'azione
     */
    public void setNome(String nome);
    
    /**
     * 
     * @param valore valore di incremento dell'obiettivo quando l'azione viene collegata
     */
    public void setIncremento(int incremento);
    
    /**
     * 
     * @param giorni i giorni in cui l'azione si ripete
     */
    public void setGiorniRipetizione(List<Giorno> giorni);
    
    /**
     * 
     * @param date la data di inizio dell'azione
     */
    public void setDataInizio(LocalDate date);
    
    /**
     * 
     * @param obiettivo l'obiettivo a cui questa azione è collegata
     */
    public void setObiettivo(IObiettivoAzione obiettivo);
    
    /**
     * 
     * @return l'obiettivo a cui questa azione è collegata
     */
    public IObiettivoAzione getObiettivo();
    
}
