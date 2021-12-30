/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.habittracker.interfaces;

/**
 * Rappresenta un abitudine da completare periodicamente nel tempo, che si 
 * differenzia per il fatto di doverla svolgere per un periodo prolungato di tempo 
 * per poterla completare con successo.
 */
public interface IAbitudineSessione extends IAbitudine {
    
    /**
     * 
     * @param durata Durata di svolgimento dell'abitudine.
     */
    public void setDurata(int durata);

    public void avviaSessione();

}
