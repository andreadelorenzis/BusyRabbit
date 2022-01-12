package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IObiettivoAzione;

public class ObiettivoAzione extends Obiettivo implements IObiettivoAzione {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * La lista di azioni collegate a questo obiettivo
     */
    private List<IAzione> azioni = new ArrayList<>();
    
    /**
     * Il valore totale da raggiungere 
     */
    private int valoreTotale;
    
    /**
     * Il valore attuale raggiunto completando le azioni
     */
    private int valoreAttuale;
    
    /**
     * L'unità di misura del valore di questo obiettivo
     */
    private String unità;

    //----------------------------- COSTRUTTORI --------------------------------
    public ObiettivoAzione(String nome, String descrizione, LocalDate data, int valoreTotale, String unità) {
        super(nome, descrizione, data);
        this.unità = unità;
        this.valoreTotale = valoreTotale;
    }
    
    public ObiettivoAzione(String nome, LocalDate data, int valore, String unità) {
        this(nome, "", data, valore, unità);
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public double calcolaProgresso() {
        return (double) valoreAttuale / valoreTotale;
    }

    @Override
    public void collegaAzione(IAzione azione) {
        azione.setObiettivo(this);
        azioni.add(azione);
    }

    @Override
    public List<IAzione> getAzioni() {
        return azioni;
    }

    @Override
    public void eliminaAzione(String idAzione) {
        azioni = azioni.stream()
                       .filter(az -> !(az.getId().equals(idAzione)))
                       .collect(Collectors.toList());
    }

    @Override
    public String getUnità() {
        return unità;
    }

    @Override
    public void setUnità(String unità) {
        this.unità = unità;
    }

    @Override
    public int getValoreAttuale() {
        return valoreAttuale;
    }

    @Override
    public int getValoreTotale() {
        return valoreTotale;
    }

    @Override
    public void setValoreAttuale(int valore) {
        this.valoreAttuale = valoreAttuale;
    }

    @Override
    public void setValoreTotale(int valore) {
        this.valoreTotale = valoreTotale;
    }
    
}
