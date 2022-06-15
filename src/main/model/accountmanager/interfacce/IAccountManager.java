package main.model.accountmanager.interfacce;

import main.model.accountmanager.classi.ExistingAccountException;
import main.model.accountmanager.classi.InvalidEmailException;
import main.model.accountmanager.classi.WrongCredentialsException;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.habittracker.interfacce.IAbitudineTracker;
import main.model.timetracker.interfacce.ITimeTracker;

/**
 * Permette di recuperare e salvare le informazioni degli account comunicando con il database
 */
public interface IAccountManager {
    
    /**
     * Aggiunge un nuovo account al database, identificato dall'email, se l'email
     * non è già stata usata e le due password combaciano. 
     * 
     * @param email
     * @param password
     * @param ripetiPass 
     */
    public void registraAccount(String nome, String email, String password, String ripetiPass) throws WrongCredentialsException, 
	   																								  ExistingAccountException,
	   																								  InvalidEmailException;
    
    /**
     * Prende l'account del database, la cui email combacia con quella passata, se la
     * password è corretta, null altrimenti
     * 
     * @param email 
     * @param password
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
    public boolean eliminaAccount(String email, String password) throws WrongCredentialsException;
    
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
    public IAbitudineTracker getHT();
    
    /**
     * 
     * @return se è stato effettuato l'accesso
     */
    public boolean getAccessoEffettuato();
    
    /**
     * 
     * @return l'email dell'account
     */
    public String getEmail();
    
}
