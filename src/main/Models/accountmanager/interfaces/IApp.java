package main.Models.AccountManager.interfaces;

import main.Models.AccountManager.classes.ExistingAccountException;
import main.Models.AccountManager.classes.WrongCredentialsException;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.timetracker.interfaces.ITimeTracker;

/**
 * Permette di recuperare e salvare le informazioni degli account comunicando con il database
 */
public interface IApp {
    
    /**
     * Aggiunge un nuovo account al database, identificato dall'email, se l'email
     * non è già stata usata e le due password combaciano. 
     * 
     * @param email
     * @param password
     * @param ripetiPass 
     * @return Account
     */
    public void registraAccount(String nome, String email, String password, String ripetiPass) throws WrongCredentialsException, 
	   																								  ExistingAccountException ;
    
    /**
     * Prende l'account del database, la cui email combacia con quella passata, se la
     * password è corretta, null altrimenti
     * 
     * @param email 
     * @param password
     * @return Account
     */
    public void accedi(String email, String password) throws WrongCredentialsException;
    
    /**
     * Elimina l'account del database, la cui email combacia con quella passata, se
     * la password è corretta
     * 
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Valore che rappresenta l'avvenuta eliminazione dell'account o meno.
     */
    public void eliminaAccount(String email, String password) throws WrongCredentialsException;
    
    /**
     * 
     * @param nuovaEmail
     * @param password
     */
    public void cambiaEmail(String nuovaEmail, String password) throws ExistingAccountException, WrongCredentialsException;
    
    /**
     * 
     * @param vecchia
     * @param nuova
     */
    public void cambiaPassword(String vecchia, String nuova) throws WrongCredentialsException;
    
    /**
     * Scrive nel database i dati dell'app aggiornati.
     */
    public void salvaDati();
    
    /**
     * 
     * @return l'istanza di TimeTracker
     */
    public ITimeTracker getTT();
    
    /**
     * 
     * @return l'istanza di GoalManager
     */
    public IGoalManager getGM();
    
    /**
     * 
     * @return l'istanza di HabitTracker
     */
    public IHabitTracker getHT();
    
    /**
     * 
     * @return se è stato effettuato l'accesso
     */
    public boolean getAccessoEffettuato();
    
    /**
     * 
     * @return se lo sfondo scuro è attivo o no
     */
    public boolean getSfondoScuro();
    
    /**
     * 
     * @return username dell'account
     */
    public String getUsername();
}
