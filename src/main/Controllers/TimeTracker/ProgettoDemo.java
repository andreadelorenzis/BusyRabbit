/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.TimeTracker;

import java.util.UUID;

/**
 *
 * @author andre
 */
public class ProgettoDemo {
    String nome;
    String colore;
    String id;

    public ProgettoDemo(String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
        this.id = UUID.randomUUID().toString();
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
