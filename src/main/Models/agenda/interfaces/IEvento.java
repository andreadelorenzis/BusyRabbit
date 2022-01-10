/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.agenda.interfaces;

import java.time.LocalDate;

public interface IEvento {
    
    public void setNome(String nome);

    public String getNome();
    
    public void setDescrizione(String descrizione);
    
    public String getDescrizione();

    public void setData(LocalDate data);

    public LocalDate getData();
    
    public void setObiettivo(String obiettivo);

    public String getObiettivo();
    
    public void setValore(int valore);

    public int getValore();
    
    public void setCompletato(Boolean completato);
    
    public boolean getCompletato();
    
    public String getId();
}
