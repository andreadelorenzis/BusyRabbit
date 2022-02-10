package main.Views.TimeTracker.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Controllers.TimeTracker.TTHelper;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IProgetto;

public class EditorAttivitàViewImpl {
    
    @FXML
    private TextField nameField;
    
    @FXML 
    private HBox btnProgetto;
    
    @FXML
    private VBox listaProgetti;
    
    @FXML
    private BorderPane menuProgetti;
    
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
    
    /*
     * Lista progetti creati.
     */
    private List<IProgetto> progetti;
    
    /*
     * Progetto scelto per l'attività.
     */
    private IProgetto progetto;
    
    private void visualizzaListaProgetti() {
        for(int i = 0; i < progetti.size(); i++) {
        	if(i > 0) {
                creaViewProgetto(progetti.get(i));
        	}
        }
    }
    
    public void creaViewProgetto(IProgetto progetto) {     
    	
        // crea contenitore progetto
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("lista-progetti-elem");
        pane.setPadding(new Insets(0, 20, 0, 10));
        pane.setMinHeight(60);
       
        // crea nome e pallino progetto nella parte sinistra
        HBox nome = new HBox();
        nome.setAlignment(Pos.CENTER);
        Label label1 = TTHelper.creaLabelProgetto(progetto);
        nome.getChildren().add(label1);
        pane.setLeft(nome);

        // aggiunge event handler al container
        EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                cambiaProgetto(progetto);
            }
        };
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);
        
        // aggiunge il progetto alla view
        listaProgetti.getChildren().add(pane);
    }
    
    /**
     * Cambia il progetto
     */
    private void cambiaProgetto(IProgetto progetto) {

    	// modifica progetto
        Label label = TTHelper.creaLabelProgetto(progetto);
        this.progetto = progetto;
        
        // aggiorna il pulsante nella view.
        this.btnProgetto.getChildren().clear(); 
        this.btnProgetto.getChildren().add(label);
        
        // chiudi menù
        this.toggleMenuProgetti();
        
    }
    
    @FXML
    private void toggleMenuProgetti() {
        if(menuProgetti.isVisible()) 
            menuProgetti.setVisible(false);
        else
            menuProgetti.setVisible(true);  
    }
    
    public void setAttività(IAttività a) {
    	nameField.setText(a.getNome());
        this.cambiaProgetto(a.getProgetto());
        this.toggleMenuProgetti();
        datePicker.setValue(a.getData());
        oraField1.setText("" + a.getOraInizio().getHour());
        oraField2.setText("" + a.getOraInizio().getMinute());
        durataField1.setText("" + (a.getDurata() / 3600) % 60);
        durataField2.setText("" + (a.getDurata() / 60) % 60);
        durataField3.setText("" + (a.getDurata()) % 60);
    }
    
    public void setListaProgetti(List<IProgetto> progetti) {
        this.progetti = progetti;
        this.visualizzaListaProgetti();
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
    		System.out.println("Inserisci un orario valida.");
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
    		System.out.println("Inserisci una durata valida.");
    	}
    	return ore * 3600 + minuti * 60 + secondi;
    }
    
    public IProgetto getProgetto() {
    	return progetto;
    }
    
}
