/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 *
 * @author andre
 */
public class ImpostazioniTimerController implements Initializable {
    
    @FXML 
    private Spinner<Integer> pomodoroSpinner;
    
    @FXML
    private Spinner<Integer> pausaBreveSpinner;
    
    @FXML
    private Spinner<Integer> pausaLungaSpinner;

    private int pomodoro;
    private int pausaBreve;
    private int pausaLunga;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        
        valueFactory1.setValue(1);
        valueFactory2.setValue(1);
        valueFactory3.setValue(1);
        
        this.pomodoroSpinner.setValueFactory(valueFactory1);
        this.pausaBreveSpinner.setValueFactory(valueFactory2);
        this.pausaLungaSpinner.setValueFactory(valueFactory3);
        
        this.pomodoroSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                pomodoro = pomodoroSpinner.getValue();
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
    
    public void setPomodoro(int pomodoro) {
        this.pomodoro = pomodoro;
        this.pomodoroSpinner.getValueFactory().setValue(pomodoro);
    }

    public void setPausaBreve(int pausaBreve) {
        this.pausaBreve = pausaBreve;
        this.pausaBreveSpinner.getValueFactory().setValue(pausaBreve);
    }

    public void setPausaLunga(int pausaLunga) {
        this.pausaLunga = pausaLunga;
        this.pausaLungaSpinner.getValueFactory().setValue(pausaLunga);
    }

    public int getPomodoro() {
        return this.pomodoro;
    }

    public int getPausaBreve() {
        return this.pausaBreve;
    }

    public int getPausaLunga() {
        return this.pausaLunga;
    }
    
}
