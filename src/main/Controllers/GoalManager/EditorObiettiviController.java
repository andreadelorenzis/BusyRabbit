/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.GoalManager;

import java.util.ArrayList;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Controllers.GoalManager.demo.IObiettivo;
import main.Controllers.GoalManager.demo.ObiettivoDemo;
import main.Controllers.GoalManager.demo.ObiettivoMisurabileDemo;

/**
 *
 * @author andre
 */
public class EditorObiettiviController {
    
    @FXML
    private TextField nomeField, unitàField;
    
    @FXML
    private DatePicker dataPicker;
    
    @FXML
    private TextArea descrizioneArea;
    
    @FXML
    private RadioButton tipoBtn1, tipoBtn2;
    
    @FXML
    private Spinner<Integer> valoreSpinner;
    
    @FXML
    private VBox formObiettivoMisurabile;
    
    private IObiettivo obiettivo;
    
    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory1.setValue(1);
        this.valoreSpinner.setValueFactory(valueFactory1);
        
        this.valoreSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                ((ObiettivoMisurabileDemo)obiettivo).setValore(valoreSpinner.getValue());
            }
        });
    }
    
    @FXML
    private void cambiaNome() {
        this.obiettivo.setNome(this.nomeField.getText());
    }
    
    @FXML
    private void cambiaData() {
    
    }
            
    @FXML
    private void cambiaDescrizione() {
        this.obiettivo.setNome(this.descrizioneArea.getText());
    }
   
    @FXML
    private void cambiaTipo(ActionEvent event) {
        if(this.tipoBtn1.isSelected()) {
            this.formObiettivoMisurabile.setVisible(false);
            this.obiettivo = new ObiettivoDemo("", "", new Date(), new ArrayList<ObiettivoDemo>());
        } else if(this.tipoBtn2.isSelected()) {
            this.formObiettivoMisurabile.setVisible(true);
            this.obiettivo = new ObiettivoMisurabileDemo("", "", new Date(), new ArrayList<ObiettivoDemo>(), "", 0);
        }
    }
    
    @FXML
    private void cambiaUnità() {
        System.out.println(this.unitàField.getText());
        ((ObiettivoMisurabileDemo) this.obiettivo).setUnità(this.unitàField.getText());
    }
    
    public void setObiettivo(IObiettivo obiettivo) {
        this.obiettivo = obiettivo;
        
        this.nomeField.setText(obiettivo.getNome());
        this.descrizioneArea.setText(obiettivo.getDescrizione());
        
        if(this.obiettivo.getClass().getName() == "ObiettivoMisurabileDemo") {
            this.unitàField.setText(((ObiettivoMisurabileDemo) obiettivo).getUnità());
            this.valoreSpinner.getValueFactory().setValue(((ObiettivoMisurabileDemo) obiettivo).getValore());
        }
        
    }
    
    public IObiettivo getObiettivo() {
        this.obiettivo.setNome(this.nomeField.getText());
        this.obiettivo.setDescrizione(this.descrizioneArea.getText());
        this.obiettivo.setData(new Date());
        
        if(this.obiettivo.getClass().getName() == "ObiettivoMisurabileDemo") {
            ((ObiettivoMisurabileDemo)this.obiettivo).setUnità(this.unitàField.getText());
            ((ObiettivoMisurabileDemo)this.obiettivo).setValore((int)this.valoreSpinner.getValue());
        }
        
        return this.obiettivo;
    }
    
}
