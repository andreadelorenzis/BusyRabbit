/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IAzioneScomponibile;
import main.Models.goalmanager.interfaces.IAzioneSessione;
import main.Models.goalmanager.interfaces.IObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andre
 */
public class GoalManagerTest {
    
    GoalManager g = null;
    
    private void inizializzaGoalManager(GoalManager g) {
        g.aggiungiObiettivoScomponibile(new ObiettivoScomponibile("Fare una maratona", 
                                                                  "Correre una maratona di 20 km", 
                                                                  LocalDate.of(2022, Month.MARCH, 22))));
        g.aggiungiObiettivoAzione(new ObiettivoAzione("Dare l'esame di analisi 2", 
                                      "", 
                                      LocalDate.of(2022, Month.FEBRUARY, 2), 60, "ore", new ArrayList<Azione>()));
    }
    
    private void aggiungiSottoObiettivi(GoalManager g) {
        // aggiungo 2 sotto-obiettivi all'ObiettivoScomponibile
        IObiettivoScomponibile obiettivoScomp = (IObiettivoScomponibile) g.getObiettivi().get(0);
        obiettivoScomp.aggiungiSottoObiettivo(new ObiettivoScomponibile("Correre 10km di fila", 
                                                                        "", 
                                                                        LocalDate.of(2022, Month.FEBRUARY, 2)));
        obiettivoScomp.aggiungiSottoObiettivo(new ObiettivoAzione("Correre 20 km di fila", 
                                                                  "", 
                                                                  LocalDate.of(2022, Month.FEBRUARY, 2), 60, "ore", new ArrayList<Azione>()));
        obiettivoScomp.aggiungiSottoObiettivo(new ObiettivoScomponibile("Fare 10 sessioni di crioterapia", 
                                                                        "", 
                                                                        LocalDate.of(2022, Month.FEBRUARY, 2)));
    }
    
    private void aggiungiAzioni(GoalManager g) {
        // aggiungo 2 azioni all'ObiettivoAzione, un azione "semplice" ed un'azione "sessione"
        IObiettivoAzione obiettivoAzione = (IObiettivoAzione) g.getObiettivi().get(1);
        obiettivoAzione.collegaAzione(new AzioneScomponibile("Leggere 10 pagine",
                                                         LocalDate.of(2022, Month.JANUARY, 2),
                                                         new ArrayList<>(List.of(Giorno.MARTEDI)), 
                                                         5));
        obiettivoAzione.collegaAzione(new AzioneSessione("Studiare 2 ore",
                                                         LocalDate.of(2022, Month.JANUARY, 2),
                                                         new ArrayList<>(List.of(Giorno.SABATO, Giorno.DOMENICA)),
                                                         2,
                                                         7200));
    }
    
    /**
     * Verifica la presenza degli obiettivi
     */
    @Test
    public void testObiettivi() {
        g = GoalManager.getInstance();
        // non ci sono obiettivi nel goal manager
        assertTrue(g.getObiettivi().isEmpty());
        inizializzaGoalManager(g);
        // ci sono 3 obiettivi nel goal manager
        assertEquals(2, g.getObiettivi().size());
        // il primo è un ObiettivoScomponibile
        assertEquals("main.Models.goalmanager.classes.ObiettivoScomponibile", g.getObiettivi().get(0).getClass());
        // il secondo è un ObiettivoAzione
        assertEquals("main.Models.goalmanager.classes.ObiettivoAzione", g.getObiettivi().get(1).getClass());
    }
    
    /**
     * Verifica l'aggiunta ed il completamento dei sotto-obiettivi in un ObiettivoScomponibile.
     */
    @Test
    public void testSottoObiettivi() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiSottoObiettivi(g);
        
        // ci sono 2 sotto-obiettivi
        assertEquals(2, obiettivoScomp.getSottoObiettivi().size());
        
        // Completo il primo sotto-obiettivo
        obiettivoScomp.getSottoObiettivi().get(0).completa();
        // il sotto-obiettivo risulta completato ed il progresso principale è pari al 33.3%
        assertTrue(obiettivoScomp.getSottoObiettivi().get(0).getCompletato());
        assertEquals(0.333, obiettivoScomp.calcolaProgresso(), 0.01);

        // Completo il secondo sotto-obiettivo
        obiettivoScomp.getSottoObiettivi().get(1).completa();
        // il sotto-obiettivo risulta completato ed il progresso principale è pari al 66.6%
        assertTrue(obiettivoScomp.getSottoObiettivi().get(1).getCompletato());
        assertEquals(0.666, obiettivoScomp.calcolaProgresso(), 0.01);
        
        // Completo il terzo sotto-obiettivo
        obiettivoScomp.getSottoObiettivi().get(2).completa();
        // il sotto-obiettivo risulta completato ed il progresso principale è pari al 100%
        assertTrue(obiettivoScomp.getSottoObiettivi().get(2).getCompletato());
        assertEquals(0.1, obiettivoScomp.calcolaProgresso(), 0.01);
    }
    
    /**
     * Verifica il fallimento degli obiettivi nel caso la loro data di raggiungimento sia stata raggiunta.
     */
    @Test
    public void testScadenzaObiettivi() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        
        // calcolo quali obiettivi sono scaduti alla data del 3 Febbraio 2022
        g.calcolaScadenzeObiettivi(LocalDate.of(2022, Month.FEBRUARY, 3));
        
        // il primo obiettivo NON è ancora scaduto
        assertFalse(g.getObiettivi().get(0).getFallimento());
        
        // il secondo obiettivo è scaduto
        assertTrue(g.getObiettivi().get(0).getFallimento());
    }
    
    /**
     * Verifica l'aggiunta di azioni e l'incremento dell'ObiettivoAzione al loro completamento.
     */
    @Test
    public void testAzioni() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        IObiettivoAzione obiettivo = (IObiettivoAzione) g.getObiettivi().get(1);
        
        // ci sono 2 azioni
        assertEquals(2, obiettivo.getAzioni().size());
        
        // attivo e completo la prima azione
        obiettivo.getAzioni().get(0).attiva();
        obiettivo.getAzioni().get(0).completa();
        
        // l'azione risulta completata e il progresso dell'obiettivo è pari al 8.3%
        assertTrue(obiettivo.getAzioni().get(0).getCompletata());
        assertEquals(0.083, obiettivo.calcolaProgresso(), 0.01);
        
        // attivo e completo la seconda azione
        //obiettivo.getAzioni().get(1).attiva();
        //obiettivo.getAzioni().get(1).completa();
        
        // l'azione risulta completata e il progresso dell'obiettivo è pari al 11.6%
        //assertTrue(obiettivo.getAzioni().get(0).getCompletata());
        //assertEquals(0.116, obiettivo.calcolaProgresso(), 0.01);
    }
    
    /**
     * Verifica il calcolo delle azioni giornaliere.
     */
    @Test
    public void testPresenzaAzioniGiornaliere() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        
        // calcolo le azioni da completare oggi (Martedì 11 Gennaio 2022)
        List<IAzione> azioniGiornaliere = g.calcolaAzioniGiornaliere(LocalDate.of(2022, Month.JANUARY, 11));
        
        // nella lista è presente una sola azione
        assertEquals(1, azioniGiornaliere.size());
        
        // l'azione è attiva
        assertTrue(azioniGiornaliere.get(0).getAttiva());
        
        // la seconda azione dell'obiettivo non è attiva
        IObiettivoAzione obiettivo = (IObiettivoAzione) g.getObiettivi().get(1);
        assertFalse(obiettivo.getAzioni().get(1).getAttiva());
    }
    
    /**
     * Verifica che al termine di un obiettivo, le azioni smettano di attivarsi.
     */
    @Test
    public void testCompletamentoObiettivoAzione() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        IObiettivoAzione obiettivo = (IObiettivoAzione) g.getObiettivi().get(1);
        
        // completo l'obiettivo
        obiettivo.completa();
        
        // calcolo le azioni da completare oggi (Martedì 11 Gennaio 2022)
        List<IAzione> azioniGiornaliere = g.calcolaAzioniGiornaliere(LocalDate.of(2022, Month.JANUARY, 11));
        
        // non ci sono azioni giornaliere
        assertTrue(azioniGiornaliere.isEmpty());
    }
    
    // Verifica l'aggiunta ed il completamento di Item in un'AzioneScomponibile
    @Test 
    public void testAzioneScomponibile() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        IObiettivoAzione obiettivo = (IObiettivoAzione) g.getObiettivi().get(1);
        IAzioneScomponibile azione = (IAzioneScomponibile) obiettivo.getAzioni().get(0);
        
        // aggiungo 2 item all'AzioneScomponibile
        azione.aggiungiItem(new ItemImpl("Leggere 5 pagine"));
        azione.aggiungiItem(new ItemImpl("Leggere altre 5 pagine"));
        
        // ci sono 2 item
        assertEquals(2, azione.getItems().size());
        
        // completo il primo item
        azione.getItems().get(0).completa();
        
        // primo item "Leggere 5 pagine" è completato
        assertTrue(azione.getItems().get(0).getCompletato());
        assertEquals("Leggere 5 pagine", azione.getItems().get(0).getNome());
        
        // secondo item non è completato
        assertFalse(azione.getItems().get(1).getCompletato());
        
    }   
    
    // Verifica il completamento di un'AzioneSessione
    @Test
    public void testAzioneSession() throws InterruptedException {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        IObiettivoAzione obiettivo = (IObiettivoAzione) g.getObiettivi().get(1);
        IAzioneSessione azione = (IAzioneSessione) obiettivo.getAzioni().get(1);
        
        // Imposto la durata a 0.01 secondi
        azione.setDurata(0.0010);
        
        // faccio partire la sessione
        azione.avviaSessione();
        
        // attendo 0.0020 secondi
        Thread.sleep(20);
        
        // l'azione è completata
        assertTrue(azione.getCompletata());
        
    }
    
    /**
     * Verifica l'eliminazione di obiettivi, azioni e items.
     */
    @Test
    public void testEliminazione() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        IObiettivoAzione obiettivoAzione = (IObiettivoAzione) g.getObiettivi().get(1);
        String idAzione = obiettivoAzione.getAzioni().get(1).getId();
        IObiettivoScomponibile obiettivoScomp = (IObiettivoScomponibile) g.getObiettivi().get(0);
        String idSottoOb = obiettivoScomp.getSottoObiettivi().get(0).getId();
        
        // elimino il primo sotto-obiettivo dell'ObiettivoScomponibile
        obiettivoScomp.eliminaSottoObiettivo(idSottoOb);
        
        // c'è solo un sotto-obiettivo, il primo
        assertTrue(obiettivoScomp.getSottoObiettivi().size() == 1);
        assertTrue(obiettivoScomp.getSottoObiettivi().get(0).equals("Correre 10km di fila"));
        
        // elimino la seconda azione dell'ObiettivoAzione
        obiettivoAzione.eliminaAzione(idAzione);
        
        // c'è solo un'azione, la prima
        assertTrue(obiettivoAzione.getAzioni().size() == 1);
        assertTrue(obiettivoAzione.getAzioni().get(0).equals("Leggere 10 pagine"));
    }

    /**
     * Verifica che venga lanciata un'eccezione se si prova a completare un'azione non attiva.
     */
    @Test
    public void testActionNotActiveException() {
        g = GoalManager.getInstance();
        inizializzaGoalManager(g);
        aggiungiAzioni(g);
        IObiettivoAzione obiettivo = (IObiettivoAzione) g.getObiettivi().get(1);
        
        // completo un'azione non attiva
        obiettivo.getAzioni().get(0).completa();
        
        // lancia un eccezione quando provo a completarla
        assertThrows(ActionNotActiveException.class, () -> obiettivo.getAzioni().get(0).completa());
    }
    
}
