/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.habittracker.interfaces;

/**
 * Abitudine svolgibile quante volte si vuole che non si ripete periodicamente.
 */
public interface IAbitudineContatore {
    
    /**
     * 
     * @param tipo Permette di stabilire se l'abitudine è positiva (+), negativa (-) o entrambe (+ e -).
     */
    public void setTipo(String tipo);
    
}
