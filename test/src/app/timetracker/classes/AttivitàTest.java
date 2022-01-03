/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package src.app.timetracker.classes;

import java.time.LocalDate;
import main.Models.timetracker.classes.Attività;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mars_DB
 */
public class AttivitàTest {
    
    private LocalDate data;
    private Attività attività = new Attività(data, 5, "Nome attività");
    
    /**
     * Test of setNome method, of class Attività.
     */
    @Test
    public void testSetNome() {
        attività.setNome("Nome attività modificato");
        assertEquals("Nome attività modificato", attività.getNome());
    }

    /**
     * Test of setDurata method, of class Attività.
     */
    @Test
    public void testSetDurata() {
    }

    /**
     * Test of setProgettoPadre method, of class Attività.
     */
    @Test
    public void testSetProgettoPadre() {
    }
    
}
