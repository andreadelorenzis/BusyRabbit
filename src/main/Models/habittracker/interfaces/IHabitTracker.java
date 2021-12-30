/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.habittracker.interfaces;

import java.util.Date;

public interface IHabitTracker {
    
    /**
     * 
     * @param nome Nome dell'abitudine periodica.
     * @param data Data di partenza dell'abitudine periodica.
     * @param giorni Giorni in cui si ripeterà l'abitudine.
     */
    public void aggiungiAbitudinePeriodica(String nome, Date dataInizio, int[] giorni);
    
    /**
     * 
     * @param nome Nome dell'abitudine contatore.
     * @param tipo Permette di dire se l'abitudine è positiva (+), negativa (-) o entrambe (+ e -).
     */
    public void aggiungiAbitudineContatore(String nome, Boolean tipo);
    
    /**
     * 
     * @param nome Nome dell'abitudine periodica.
     * @param data Data di partenza dell'abitudine periodica.
     * @param giorni Giorni in cui si ripeterà l'abitudine.
     * @param ID Identificativo dell'abitudine.
     */
    public void modificaAbitudinePeriodica(String nome, Date data, int[] giorni, int ID);
    
    /**
     * 
     * @param nome Nome dell'abitudine contatore.
     * @param tipo Permette di dire se l'abitudine è positiva (+), negativa (-) o entrambe (+ e -).
     * @param ID Identificativo dell'abitudine.
     */
    public void modificaAbitudineContatore(String nome, Boolean tipo, int ID);
    
    /**
     * 
     * @param ID Identificativo dell'abitudine.
     */
    public void eliminaAbitudine(int ID);
    
}
