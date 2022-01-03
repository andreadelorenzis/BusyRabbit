/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.agenda.interfaces;

import java.time.LocalDate;
import java.util.Date;
import main.Models.goalmanager.classes.ObiettivoMisurabile;

/**
 * Permette di annotare degli eventi futuri.
 */
public interface IAgenda {

     public void aggiungiEvento(String nome, String descrizione, LocalDate data, String Obiettivo, int valore);
     
     public void modificaEvento(String nome, String descrizione, LocalDate data, String Obiettivo, int valore);
     
     public void eliminaEvento(String id);
}
