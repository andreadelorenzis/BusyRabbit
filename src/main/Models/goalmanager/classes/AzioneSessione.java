package main.Models.goalmanager.classes;

import java.time.LocalDate;
import java.util.List;
import main.Giorno;
import main.Models.goalmanager.interfaces.IAzioneSessione;
import main.Models.timetracker.classes.Timer;

public class AzioneSessione extends Azione implements IAzioneSessione {
    
    //-------------------------------- CAMPI -----------------------------------
    /**
     * La durata della sessione
     */
    private int durata;

    /**
     * Il timer per misurare il tempo
     */
    private Timer timer;
    
    //----------------------------- COSTRUTTORI --------------------------------
    /**
     * 
     * @param nome
     * @param incremento
     * @param dataInizio
     * @param giorni
     * @param durata 
     */
    public AzioneSessione(String nome, int incremento, LocalDate dataInizio, List<Giorno> giorni, int durata) {
        super(nome, incremento, dataInizio, giorni);
        this.durata = durata;
        timer = new Timer(durata);
    }

    //--------------------------- METODI PUBBLICI ------------------------------
    @Override
    public void avviaSessione() {
    	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sospendiSessione() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void terminaSessione() {
    	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDurata() {
        return durata;
    }

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public void setDurata(int durata) {
        this.durata = durata;
    }
    
}
