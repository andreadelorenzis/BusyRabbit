package main.model.goalmanager.classi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IItem;

public class AzioneScomponibile extends Azione implements IAzioneScomponibile {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * La lista di item di cui si compone l'azione
     */
    private List<IItem> items = new ArrayList<>();

    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param incremento
     * @param dataInizio 
     * @param giorni 
     */
    public AzioneScomponibile(String nome, int incremento, LocalDate dataInizio, List<DayOfWeek> giorni) {
        super(nome, incremento, dataInizio, giorni);
    }
    
    public AzioneScomponibile(String nome, int incremento, LocalDate dataInizio, List<DayOfWeek> giorni, String id) {
        super(nome, incremento, dataInizio, giorni, id);
    }


    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void aggiungiItem(IItem item) {
    	item.setPadre(this);
        items.add(item);
    }

    @Override
    public void eliminaItem(String idItem) {
        items = items.stream()
                     .filter(item -> !(item.getId().equals(idItem)))
                     .collect(Collectors.toList());
    }

    @Override
    public List<IItem> getItems() {
        return items;
    }
    
}
