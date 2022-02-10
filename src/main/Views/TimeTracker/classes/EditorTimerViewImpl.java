package main.Views.TimeTracker.classes;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import main.Models.timetracker.interfaces.IPomodoroTimer;

public class EditorTimerViewImpl {
	
	//--------------------------------- CAMPI ------------------------------------
    @FXML 
    private Spinner<Integer> pomodoroSpinner;
    
    @FXML
    private Spinner<Integer> pausaBreveSpinner;
    
    @FXML
    private Spinner<Integer> pausaLungaSpinner;
    
    /*
     * Durata sessione in minuti.
     */
    private int sessione;
    
    /*
     * Durata pausa breve in minuti.
     */
    private int pausaBreve;
    
    /*
     * Durata pausa lunga in minuti.
     */
    private int pausaLunga;
    
    public void setPomodoro(IPomodoroTimer p) {
    	
    	// creo le value factories
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        
        // imposto i valori iniziali (gli stessi del pomodoro)
        sessione = p.getDurataSessione() / 60;
        pausaBreve = p.getDurataPausaBreve() / 60;
        pausaLunga = p.getDurataPausaLunga() / 60;
        valueFactory1.setValue(sessione);
        valueFactory2.setValue(pausaBreve);
        valueFactory3.setValue(pausaLunga);
        
        // assegno le value factories
        this.pomodoroSpinner.setValueFactory(valueFactory1);
        this.pausaBreveSpinner.setValueFactory(valueFactory2);
        this.pausaLungaSpinner.setValueFactory(valueFactory3);
        
        // aggiungo i listener per il cambiamento dei valori
        this.pomodoroSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                sessione = pomodoroSpinner.getValue();
            }
        });
        this.pausaBreveSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                pausaBreve = pausaBreveSpinner.getValue();
            }
        });
        this.pausaLungaSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                pausaLunga = pausaLungaSpinner.getValue();
            }
        });
    }

    public int getSessione() {
    	return sessione * 60;
    }
    
    public int getPausaBreve() {
    	return pausaBreve * 60;
    }
    
    public int getPausaLunga() {
    	return pausaLunga * 60;
    }
    
}
