/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Controllers.GoalManager.demo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author andre
 */
public interface IObiettivo {
    public String getNome();

    public String getDescrizione();

    public Date getData();

    public ArrayList<ObiettivoDemo> getSottoObiettivi();

    public void setNome(String nome);

    public void setDescrizione(String descrizione);

    public void setData(Date data);

    public void setSottoObiettivi(ArrayList<ObiettivoDemo> sottoObiettivi);
}
