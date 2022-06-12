package main.views.goalmanager.classi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.model.goalmanager.classi.AzioneSessione;
import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IAzioneSessione;

public class EditorAzioniViewImpl {
    @FXML
    private TextField azioneField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner<Integer> valoreSpinner;
    @FXML
    private Spinner<Integer> durataSpinner;
    @FXML
    private HBox formSessione;
    @FXML
    private Label lunBtn;
    @FXML
    private Label marBtn;
    @FXML
    private Label merBtn;
    @FXML
    private Label gioBtn;
    @FXML
    private Label venBtn;
    @FXML
    private Label sabBtn;
    @FXML
    private Label domBtn;
    @FXML
    private RadioButton tipoRadio1;
    @FXML
    private RadioButton tipoRadio2;
    
    // giorni della settimana selezionati
    private boolean lun, mar, mer, gio, ven, sab, dom = false;
    private List<DayOfWeek> giorni = new ArrayList<>();
    
    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory1.setValue(1);
        valueFactory2.setValue(1);
        valoreSpinner.setValueFactory(valueFactory1);
        durataSpinner.setValueFactory(valueFactory2);
        datePicker.setValue(LocalDate.now());
    }
    
    private void toggleBtn(Label label, boolean giorno) {
        if(!giorno) {
            giorno = true;
            label.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
        } else {
            giorno = false;
            label.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE; -fx-border-color: #4361EE;");
        }
    }
    
    @FXML
    private void cambiaGiorni(MouseEvent event) {
        String btnGiornoCliccato = ((Label) event.getSource()).getId();
        switch(btnGiornoCliccato) {
            case "lunBtn": 
                toggleBtn(lunBtn, lun);
                giorni.add(DayOfWeek.MONDAY);
                break;
            case "marBtn": 
            	toggleBtn(marBtn, mar);
            	giorni.add(DayOfWeek.TUESDAY);
            	break;
            case "merBtn": 
            	toggleBtn(merBtn, mer);
            	giorni.add(DayOfWeek.WEDNESDAY);
            	break;
            case "gioBtn": 
            	toggleBtn(gioBtn, gio);
            	giorni.add(DayOfWeek.THURSDAY);
            	break;
            case "venBtn": 
            	toggleBtn(venBtn, ven);
            	giorni.add(DayOfWeek.FRIDAY);
            	break;
            case "sabBtn": 
            	toggleBtn(sabBtn, sab);
            	giorni.add(DayOfWeek.SATURDAY);
            	break;
            case "domBtn": 
            	toggleBtn(domBtn, dom);
            	giorni.add(DayOfWeek.SUNDAY);
            	break;
        }
    }
    
    @FXML
    private void cambiaTipo(ActionEvent event) {
        if(tipoRadio1.isSelected()) {
            formSessione.setVisible(false);
        } else if(tipoRadio2.isSelected()) {
            formSessione.setVisible(true);
        }
    }
    
    public void setAzione(IAzione azione) {
        this.azioneField.setText(azione.getNome());
        this.valoreSpinner.getValueFactory().setValue(azione.getIncremento());
        List<DayOfWeek> giorni = azione.getGiorniRipetizione();
        for(int i = 0; i < giorni.size(); i++) {
            switch(giorni.get(i)) {
                case MONDAY: 
                    toggleBtn(lunBtn, lun);
                    break;
                case TUESDAY:
                    toggleBtn(marBtn, mar);
                    break;
                case WEDNESDAY:
                    toggleBtn(merBtn, mer);
                    break;
                case THURSDAY:
                    toggleBtn(gioBtn, gio);
                    break;
                case FRIDAY:
                    toggleBtn(venBtn, ven);
                    break;
                case SATURDAY:
                    toggleBtn(sabBtn, sab);
                    break;
                case SUNDAY:
                    toggleBtn(domBtn, dom);
                    break;
            }
        }
        if(azione instanceof AzioneSessione) {
        	IAzioneSessione azioneSessione = (IAzioneSessione) azione;
        	tipoRadio2.setSelected(true);
        	tipoRadio1.setDisable(true);
        	durataSpinner.getValueFactory().setValue(azioneSessione.getDurata());
        } else {
        	tipoRadio2.setDisable(true);
        }
    }
    
    public String getNome() {
    	return azioneField.getText();
    }
    
    public int getValore() {
    	return valoreSpinner.getValue();
    }
    
    public List<DayOfWeek> getGiorni() {
    	return giorni;
    }
    
    public boolean isTipoSessione() {
    	return tipoRadio2.isSelected();
    }
    
    public int getDurata() {
    	return durataSpinner.getValue();
    }
    
    public LocalDate getData() {
    	return datePicker.getValue();
    }
    
}
