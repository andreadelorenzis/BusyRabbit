package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.Giorno;
import main.Controllers.GoalManager.GMHelper;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;

public class GoalManager implements IGoalManager {
    // CAMPI
    private static GoalManager goalManager = null;
    
    private List<IObiettivo> obiettivi = new ArrayList<>();
    
    // COSTRUTTORI
    private GoalManager() {
        
    };
    
    /**
     * 
     * @param data la data da comparare
     * @param giorni la lista di giorni
     * @return se il giorno della data è presente nella lista di giorni
     */
    public static boolean giornoPresente(LocalDate data, List<Giorno> giorni) {
        boolean presente = false;
        for(Giorno giorno : giorni) {
            if(data.getDayOfWeek().toString().equals(giorno.toString())) {
                presente = true;
            }
        }
        return presente;
    }
    
    public static GoalManager getInstance() {
    	if(goalManager == null) {
    		goalManager = new GoalManager();
    	}
    	return goalManager;
    }
    
    @Override
    public void aggiungiObiettivo(IObiettivo obiettivo) {
        obiettivi.add(obiettivo);
    }

    @Override
    public List<IObiettivo> getObiettivi() {
        return obiettivi;
    }

    @Override
    public void calcolaScadenzeObiettivi(LocalDate data) {
    	int i = 0;
        for(IObiettivo ob : obiettivi) {
        	if(ob.getData().isBefore(data) || ob.getData().isEqual(data)) {
                obiettivi.get(i).faiFallire();
            }
        	i++;
        }
    }

    @Override
    public List<IAzione> calcolaAzioniGiornaliere(LocalDate data) {
        List<IAzione> azioni = new ArrayList<>();
        obiettivi.stream()
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
        obiettivi = obiettivi.stream()
                             .filter(ob -> !(ob.getId().equals(idObiettivo)))
                             .collect(Collectors.toList());
    }
    
}
