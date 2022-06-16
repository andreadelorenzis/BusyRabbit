package main.model.goalmanager.classi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.goalmanager.interfacce.IObiettivo;
import main.model.goalmanager.interfacce.IObiettivoAzione;
import main.model.goalmanager.interfacce.IObiettivoScomponibile;

public class GoalManager implements IGoalManager {
	//-------------------------------- CAMPI -----------------------------------
	/*
	 * SINGLETONE
	 */
    private static GoalManager goalManager = null;
    
    /*
     * Struttura che contiene la gerarchia degli obiettivi
     */
    private List<IObiettivo> alberoObiettivi = new ArrayList<>();
    
    /*
     * Struttura che contiene tutti gli obiettivi
     */
    private List<IObiettivo> listaObiettivi = new ArrayList<>();
    
    //----------------------------- COSTRUTTORI --------------------------------
    private GoalManager() {
        
    };
    
    //--------------------------- METODI PUBBLICI ------------------------------
    public static GoalManager getInstance() {
    	if(goalManager == null) {
    		goalManager = new GoalManager();
    	}
    	return goalManager;
    }
    
    public static boolean giornoPresente(LocalDate data, List<DayOfWeek> giorni) {
        boolean presente = false;
        for(DayOfWeek giorno : giorni) {
            if(data.getDayOfWeek().toString().equals(giorno.toString())) {
                presente = true;
            }
        }
        return presente;
    }
    
    @Override
    public void aggiungiObiettivo(IObiettivo obiettivo) {
        alberoObiettivi.add(obiettivo);
    }
    
    @Override
    public void aggiungiSottoObiettivo(IObiettivoScomponibile padre, IObiettivo figlio) {
    	listaObiettivi.add(figlio);
    	padre.aggiungiSottoObiettivo(figlio);
    }

    @Override
    public List<IObiettivo> getObiettivi() {
        return alberoObiettivi;
    }

    @Override
    public void verificaScadenzeObiettivi(LocalDate data) {
    	int i = 0;
        for(IObiettivo ob : listaObiettivi) {
        	if(ob.getData().isBefore(data) || ob.getData().isEqual(data)) {
                listaObiettivi.get(i).faiFallire();
            }
        	i++;
        }
    }

    @Override
    public List<IAzione> calcolaAzioniGiornaliere(LocalDate data) {
        List<IAzione> azioni = new ArrayList<>();
        listaObiettivi.stream()
                 .forEach(ob -> {
                    if(ob instanceof ObiettivoAzione) {
                        IObiettivoAzione obAzione = (IObiettivoAzione) ob;
                        obAzione.getAzioni().stream()
                                            .forEach(azione -> {
                                               if((azione.getDataInizio().isBefore(data) || azione.getDataInizio().isEqual(data))
                                                  && giornoPresente(data, azione.getGiorniRipetizione())
                                                  && !ob.getCompletato()) {
                                                   azioni.add(azione);
                                               } 
                                            });
                    }
                 });
        return azioni;             
    }
    

    @Override
    public void eliminaObiettivo(String idObiettivo) {
        alberoObiettivi = alberoObiettivi.stream()
                             .filter(ob -> !(ob.getId().equals(idObiettivo)))
                             .collect(Collectors.toList());
    }
    
}
