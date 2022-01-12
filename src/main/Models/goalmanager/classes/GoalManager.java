package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.Giorno;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;

public class GoalManager implements IGoalManager {
    // CAMPI
    private static GoalManager istanza = null;
    
    private List<IObiettivo> obiettivi = new ArrayList<>();
    
        // COSTRUTTORI
    public static GoalManager getInstance() {
        // Crea l'oggetto solo se NON esiste:
        if (istanza == null) {
            istanza = new GoalManager();
        }
        return istanza;
    };
    
    /**
     * 
     * @param data la data da comparare
     * @param giorni la lista di giorni
     * @return se il giorno della data è presente nella lista di giorni
     */
    private boolean giornoPresente(LocalDate data, List<Giorno> giorni) {
        boolean presente = false;
        for(Giorno giorno : giorni) {
            if(data.getDayOfWeek().toString().equals(giorno.toString())) {
                presente = true;
            }
        }
        return presente;
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
        obiettivi.stream()
                 .forEach(ob -> {
                     if(ob.getData().isBefore(data) || ob.getData().isEqual(data)) {
                         ob.faiFallire();
                     }
                 });
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
                                                  && giornoPresente(data, azione.getGiorniRipetizione())) {
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
