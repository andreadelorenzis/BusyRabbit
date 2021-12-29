/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.Models.timetracker.interfaces;

import main.Models.timetracker.classes.Attività;

public interface ITimeTracker extends ITracker {
    
    public void avviaTimeTracker(Attività attività);
    
    public void arrestaTimeTracker(Attività attività);
    
}
