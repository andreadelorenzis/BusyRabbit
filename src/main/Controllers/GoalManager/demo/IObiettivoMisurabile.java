/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Controllers.GoalManager.demo;

/**
 *
 * @author andre
 */
public interface IObiettivoMisurabile extends IObiettivo {
    public void setUnità(String unità);
    public void setValore(int valore);
    public String getUnità();
    public int getValore();
}
