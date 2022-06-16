package main.model.goalmanager.classi;

import java.util.UUID;
import main.model.goalmanager.interfacce.IItem;

public class Item implements IItem {
    
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
     * Il padre che contiene questo item
     */
    private Object padre;
    
    /**
     * Identificativo dell'item
     */
    private String id;
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome 
     */
    public Item(String nome) {
        this.nome = nome;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * 
     * @param nome
     * @param id
     */
    public Item(String nome, String id) {
        this(nome);
        this.id = id;
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
    public Object getPadre() {
        return padre;
    }
    
    @Override
    public void setPadre(Object padre) {
        this.padre = padre;
    }
    
	@Override
    public boolean equals(Object obj) {
  	      if (obj == this) {
  	         return true;
  	      }
  	      if (!(obj instanceof IItem)) {
  	         return false;
  	      }
  	      IItem o = (IItem) obj;
  	      return this.id.equals(o.getId());
   }
}
