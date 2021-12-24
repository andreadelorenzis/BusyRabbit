/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.habittracker.interfaces;

public interface IAbitudine {
    
    /**
     * 
     * @param nome Nome dell'abitudine.
     */
    public void setNome(String nome);
    
    /**
     * 
     * @param descrizione Descrizione dell'abitudine.
     */
    public void setDescrizione(String descrizione);
    
    /**
     * 
     * @param count Conteggio di completamento consecutivo.
     */
    public void setCountAttuale(int count);
    
    /**
     * 
     * @param count Conteggio di completamento consecutivo che si vuole raggiungere.
     */
    public void setCountObiettivo(int count);
    
    /**
     * 
     * @param count Conteggio di completamento consecutivo record.
     */
    public void setCountRecord(int count);
    
}
