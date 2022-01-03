/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.TimeTracker;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author andre
 */
public class ProgettoDemo {
    private String nome;
    private String colore;
    private String hexColore;
    private String id;
    
    // Contiene gli anni di monitoraggio del progetto
    private ArrayList<Map<String, Integer>> anni;
    
    // Contiene il tempo totale progetto per ogni mese dell'anno
    private Map<String, Double> anno;
    
    // Contiene il tempo totale progetto per ogni giorno del mese
    private Map<String, Integer> mese; 

    public ProgettoDemo(String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
        this.id = UUID.randomUUID().toString();
        
        // ----> DEMO
        
        // <---- DEMO
    }
    
    public void aggiungiAttività() {
        
    }

    public String getNome() {
        return nome;
    }

    public String getColore() {
        return colore;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Map<String, Integer>> getAnni() {
        return anni;
    }

    public Map<String, Double> getAnno() {
        return anno;
    }

    public Map<String, Integer> getMese() {
        return mese;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAnni(ArrayList<Map<String, Integer>> anni) {
        this.anni = anni;
    }

    public void setAnno(Map<String, Double> anno) {
        this.anno = anno;
    }

    public void setMese(Map<String, Integer> mese) {
        this.mese = mese;
    }

    public void setHexColore(String hexColore) {
        this.hexColore = hexColore;
    }

    public String getHexColore() {
        return hexColore;
    }
    
    
    
}
