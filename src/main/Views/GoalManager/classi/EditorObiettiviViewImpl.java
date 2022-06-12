package main.Views.GoalManager.classi;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Models.goalmanager.classes.ObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivo;

public class EditorObiettiviViewImpl {
    
    @FXML
    private TextField nomeField, unit‡Field;
    
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
    
    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000);
        valueFactory1.setValue(1);
        this.valoreSpinner.setValueFactory(valueFactory1);
    }
   
    @FXML
    private void cambiaTipo(ActionEvent event) {
        if(this.tipoBtn1.isSelected()) {
            this.formObiettivoMisurabile.setVisible(false);
        } else if(this.tipoBtn2.isSelected()) {
            this.formObiettivoMisurabile.setVisible(true);
        }
    }
    
    public void setObiettivo(IObiettivo obiettivo) {
    	if(obiettivo != null) {
            this.nomeField.setText(obiettivo.getNome());
            this.descrizioneArea.setText(obiettivo.getDescrizione());
            this.dataPicker.setValue(obiettivo.getData());
            if(obiettivo instanceof ObiettivoAzione) {
            	tipoBtn2.setSelected(true);
            	tipoBtn1.setDisable(true);
            	this.formObiettivoMisurabile.setVisible(true);
                this.unit‡Field.setText(((ObiettivoAzione) obiettivo).getUnit‡());
                this.valoreSpinner.getValueFactory().setValue(((ObiettivoAzione) obiettivo).getValoreTotale());
            } else {
            	tipoBtn2.setDisable(true);
            }
    	}
    }
    
    public String getNome() {
    	return nomeField.getText();
    }
    
    public String getDescrizione() {
    	return descrizioneArea.getText();
    }
    
    public LocalDate getData() {
    	return dataPicker.getValue();
    }
    
    public boolean isTipoAzione() {
    	return tipoBtn2.isSelected();
    }
    
    public int getValore() {
    	return valoreSpinner.getValue();
    }
    
    public String getUnit‡() {
    	return unit‡Field.getText();
    }
    
}
