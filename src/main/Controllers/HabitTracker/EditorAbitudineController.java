package main.Controllers.HabitTracker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Models.goalmanager.classes.AzioneSessione;
import main.Models.goalmanager.interfaces.IAzioneSessione;
import main.Models.habittracker.classes.SessionHabit;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.ISessionHabit;

public class EditorAbitudineController {
    
    @FXML
    private TextField abitudineField;
    @FXML
    private TextArea descrizioneArea;
    @FXML
    private DatePicker dataPicker; 
    @FXML
    private RadioButton tipoRadio1;
    @FXML
    private RadioButton tipoRadio2;
    @FXML
    private HBox formSessione;
    @FXML
    private Spinner<Integer> durataSpinner;
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
    
    // giorni della settimana selezionati
    private boolean lun, mar, mer, gio, ven, sab, dom = false;
    private List<DayOfWeek> giorni = new ArrayList<>();
    
    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        durataSpinner.setValueFactory(valueFactory);
        dataPicker.setValue(LocalDate.now());
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
    public void setAbitudine(IHabit abitudine) {
        abitudineField.setText(abitudine.getName());
        List<DayOfWeek> giorni = abitudine.getDays();
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
        if(abitudine instanceof SessionHabit) {
        	ISessionHabit abitudineSessione = (ISessionHabit) abitudine;
        	tipoRadio2.setSelected(true);
        	tipoRadio1.setDisable(true);
        	durataSpinner.getValueFactory().setValue(abitudineSessione.getDuration());
        	formSessione.setVisible(true);
        } else {
        	tipoRadio2.setDisable(true);
        }
    }
    
    public String getNome() {
    	return abitudineField.getText();
    }
    
    public String getDescrizione() {
    	return descrizioneArea.getText();
    }
    
    public LocalDate getData() {
    	return dataPicker.getValue();
    }
    
    public boolean isSessione() {
    	return tipoRadio2.isSelected();
    }
    
    public List<DayOfWeek> getGiorni() {
    	return giorni;
    }
    
    public int getDurata() {
    	return durataSpinner.getValue();
    }
    
}
