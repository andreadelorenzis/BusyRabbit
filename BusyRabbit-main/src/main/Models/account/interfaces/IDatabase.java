/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.account.interfaces;

import main.Models.account.classes.Account;

/**
 * Contiene tutti gli account creati.
 */
public interface IDatabase {
    
    /**
     * 
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Account che contiene la struttura dei dati dell'utente.
     */
    public Account aggiungiAccount(String nome, String email, String password);
    
    /**
     * 
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Account che contiene la struttura dei dati dell'utente.
     */
    public Account getAccount(String email, String password);
    
    /**
     * 
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Valore che rappresenta l'avvenuta eliminazione dell'account o meno.
     */
    public Boolean eliminaAccount(String email, String password);
    
}
