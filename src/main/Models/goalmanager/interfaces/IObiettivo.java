/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.goalmanager.interfaces;

import java.util.Date;

/**
 * Rappresenta un qualsiasi obiettivo, avente un nome, una descrizione, una data di completamento ed un valore che rappresenta 
 * il progresso dell'obiettivo. 
 * Le sue implementazioni sono ObiettivoScomponibile, un obiettivo scomponibile
 * in più sotto-obiettivi, e ObiettivoAzione, il quale ha un valore e a cui possono essere collegate delle azioni 
 * ricorrenti.
 */
public interface IObiettivo {
    
    /**
     * Calcola il progresso dell'obiettivo rispetto al valore totale da raggiungere
     * 
     * @return un valore decimale che rappresenta la percentuale di progresso (es: 0.5)
     */
    public double calcolaProgresso();
    
    /**
     * completa questo obiettivo. Gli obiettivi completati possono essere riportati allo stato
     * di non completamento in ogni momento
     */
    public void completa();
    
    /**
     * fa fallire questo obiettivo. Se un obiettivo fallisce il suo stato di completamento non 
     * può essere modificato. 
     */
    public void faiFallire();
    
    /**
     * @return nome dell'obiettivo
     */
    public String getNome();

    /**
     * @return descrizione dell'obiettivo 
     */
    public String getDescrizione();

    /**
     * @return data di completamento dell'obiettivo
     */
    public Date getData();
    
    /**
     * @return identificativo dell'obiettivo
     */
    public String getId();
    
    /**
     * 
     * @return se l'obiettivo è completato o no
     */
    public boolean getCompletato();
    
    /**
     * 
     * @return se l'obiettivo è fallito o no
     */
    public boolean getFallimento();
    
    /**
     * 
     * @return il valore attuale dell'obiettivo
     */
    public int getValoreAttuale();
    
    /**
     * 
     * @return il valore totale da raggiungere dell'obiettivo
     */
    public int getValoreTotale();
    
    /**
     * @param nome nome dell'obiettivo
     */
    public void setNome(String nome);

    /**
     * @param descrizione descrizione dell'obiettivo
     */
    public void setDescrizione(String descrizione);

    /**
     * @param data data dell'obiettivo
     */
    public void setData(Date data);
    
}
