package main.views.timetracker.classi;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.model.timetracker.interfacce.IAttività;
import main.views.notification.Notification;
import main.views.notification.NotificationType;

public class EditorAttività {
    
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField oraField1;
    @FXML
    private TextField oraField2;
    @FXML
    private TextField durataField1;
    @FXML
    private TextField durataField2;
    @FXML
    private TextField durataField3;

    public void setAttività(IAttività a) {
    	nameField.setText(a.getNome());
        datePicker.setValue(a.getData());
        oraField1.setText("" + a.getOraInizio().getHour());
        oraField2.setText("" + a.getOraInizio().getMinute());
        durataField1.setText("" + (a.getDurata() / 3600) % 60);
        durataField2.setText("" + (a.getDurata() / 60) % 60);
        durataField3.setText("" + (a.getDurata()) % 60);
    }
    
    public String getNome() {
    	return nameField.getText();
    }
    
    public LocalDate getData() {
    	return datePicker.getValue();
    }
    
    public LocalTime getOra() {
    	int ore = 0;
    	int minuti = 0;
    	try {
    		ore = Integer.parseInt(oraField1.getText());
        	minuti = Integer.parseInt(oraField2.getText());
    	} catch(NumberFormatException e) {
    		new Notification("Inserisci un orario valida.", NotificationType.ERROR).show();
    	}
    	return LocalTime.of(ore, minuti);
    }
    
    public int getDurata() {
    	int ore = 0;
    	int minuti = 0;
    	int secondi = 0;
    	try {
        	ore = Integer.parseInt(durataField1.getText());
        	minuti = Integer.parseInt(durataField2.getText());
        	secondi = Integer.parseInt(durataField3.getText());
    	} catch(NumberFormatException e) {
    		new Notification("Inserisci una durata valida.", NotificationType.ERROR).show();
    	}
    	return ore * 3600 + minuti * 60 + secondi;
    }
    
}
