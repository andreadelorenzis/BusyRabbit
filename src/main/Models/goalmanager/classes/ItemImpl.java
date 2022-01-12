package main.Models.goalmanager.classes;

import java.util.UUID;
import main.Models.goalmanager.interfaces.IAzioneScomponibile;
import main.Models.goalmanager.interfaces.Item;

public class ItemImpl implements Item {
    
    //-------------------------------- CAMPI -----------------------------------
    /***
     * Nome dell'item
     */
    private String nome;
    
    /**
     * Se l'item è completato
     */
    private boolean completato = false;
    
    /**
     * L'AzioneScomponibile che si compone di questo item
     */
    private IAzioneScomponibile azione;
    
    /**
     * Identificativo dell'item
     */
    private final String id;
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome 
     */
    public ItemImpl(String nome) {
        this.nome = nome;
        this.id = UUID.randomUUID().toString();
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void completa() {
        completato = !completato;
    }

    @Override
    public boolean getCompletato() {
        return completato;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public IAzioneScomponibile getAzione() {
        return azione;
    }
    
    @Override
    public void setAzione(IAzioneScomponibile azione) {
        this.azione = azione;
    }
}
