package main.Controllers.Dashboard;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import main.Controllers.Helper;
import main.Controllers.GoalManager.GoalManagerController;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.habittracker.interfaces.IHabitTracker;

public class ReportAbitudiniController {
    
    @FXML
    private TilePane tilePane;
    @FXML
    private HBox settimanaBox;
    @FXML
    private VBox giornoBox;
    @FXML
    private VBox selezioneAnniBox;
    @FXML
    private Label giornoLabel;
    
    private int annoSelezionato = LocalDate.now().getYear();
    
    private IHabitTracker ht; 
    
    public void setHabitTracker(IHabitTracker ht) {
    	this.ht = ht;
 
    	// aggiunge pulsanti selezione anno
    	for(int i = 0; i < 3; i++) {
    		int anno = annoSelezionato - i;
    		Label labelAnno = new Label("" + anno);
    		labelAnno.getStyleClass().add("label-anno");
    		
    		// collega evento selezione anno
    		labelAnno.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    visualizzaDatiAnno(anno);
                }
            });
    		
    		selezioneAnniBox.getChildren().add(labelAnno);
    	}
    	
    	// visualizza dati ultima settimana
    	visualizzaDatiUltimaSettimana();
    	
    	// visualizza dati anno corrente
    	visualizzaDatiAnno(annoSelezionato);
    }
    
    private AnchorPane creaViewProgressCircle(int totHabits, List<IHabit> completedHabits, LocalDate data) {
    	// formatta data
    	String formattedDate = data.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
    	
    	// calcola la percentuale di completamento
        double progress = (double) completedHabits.size() / totHabits;
        String perc = Math.round(progress * 100.0) / 100.0 + "%";
        
        // assegna il colore
        String colore = "#00E396";
        if(progress < 0.25) {
        	colore = "#FF4560";
        } else if(progress < 0.5) {
        	colore = "#FEB019";
        } else if(progress < 0.75) {
        	colore = "#058D5E";
        } else if(progress == 1.0) {
        	colore = "#00E396";
        }
        
        AnchorPane container = new AnchorPane();
        container.setPrefWidth(100);
        container.setPadding(new Insets(0, 20, 0, 0));
        
        AnchorPane pane = new AnchorPane();
        BorderPane borderPane = new BorderPane();
        
        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setFill(Color.web("#272e52"));
        
        Arc arc = new Arc();
        arc.setRadiusX(50.0f);
        arc.setRadiusY(50.0f);
        arc.setFill(Color.web(colore));
        arc.setStartAngle(270.0f);
        arc.setLength((((float)progress * 100) * 360) / 100);
        arc.setType(ArcType.ROUND);
        arc.setCenterX(50.0f);
        arc.setCenterY(50.0f);
        
        Circle circle2 = new Circle();
        circle2.setRadius(35);
        circle2.setFill(Color.web("#0e1726"));
        circle2.setCenterX(50.0f);
        circle2.setCenterY(50.0f);
        
        Label label = new Label(perc);
        label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18");
        label.setLayoutX(30);
        label.setLayoutY(35);
        label.setAlignment(Pos.CENTER);
        
        AnchorPane.setBottomAnchor(circle, 0.0);
        AnchorPane.setTopAnchor(circle, 0.0);
        AnchorPane.setLeftAnchor(circle, 0.0);
        AnchorPane.setRightAnchor(circle, 0.0);
        
        AnchorPane.setBottomAnchor(label, 28.0);
        AnchorPane.setTopAnchor(label, 22.0);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        
        pane.getChildren().add(circle);
        pane.getChildren().add(arc);
        pane.getChildren().add(circle2);
        pane.getChildren().add(label);
        
        Label label2 = new Label(formattedDate);
        label2.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
        label2.setPadding(new Insets(10, 0, 0, 0));
        label2.setAlignment(Pos.CENTER);
        label2.setMinWidth(100);
        
        borderPane.setCenter(pane);
        borderPane.setBottom(label2);
        
        container.getChildren().add(borderPane);
        
        return container;
    }
    
    private void visualizzaDatiUltimaSettimana() {
        this.settimanaBox.getChildren().clear();
        
        // crea la view dei cerchi
        int totHabits = ht.getHabits().size();
        Map<Integer, List<IHabit>> datiSettimana = ht.getWeekRecords();
        List<LocalDate> lastWeek = HabitTracker.getLastWeek();
        for(LocalDate data : lastWeek) {
        	AnchorPane circle = creaViewProgressCircle(totHabits, datiSettimana.get(data.getDayOfYear()), data);
        	this.settimanaBox.getChildren().add(circle);
        }
    }
    
    private String getColore(double progress) {
        String colore = "#00E396";
        if(progress == 0.0) {
        	colore = "#272E52";
        } else if(progress < 0.25) {
        	colore = "#04563A";
        } else if(progress < 0.5) {
        	colore = "#058D5E";
        } else if(progress < 0.75) {
        	colore = "#08C081";
        } else if(progress == 1.0) {
        	colore = "#00E396";
        }
        
        return colore;
    }
    
    private void visualizzaDatiAnno(int anno) {
        double dimCaselle = 13;
        this.tilePane.getChildren().clear();
        int nTotHabits = ht.getHabits().size();
        
        Map<Integer, List<IHabit>> datiAnno = ht.getYearRecords(anno);
        // itera tutti i giorni dell'anno
        for(int giorno : datiAnno.keySet()) {
        	LocalDate data = LocalDate.ofYearDay(anno, giorno);
        	
        	// calcolo il colore in base al numero di abitudini completate
        	List<IHabit> abitudiniCompletate = datiAnno.get(giorno);
        	double progresso = (double) abitudiniCompletate.size() / nTotHabits;
        	String colore = getColore(progresso);
        	
        	// creo la casella
            HBox container = new HBox();
            HBox hBox = new HBox();
            hBox.getStyleClass().add("casella-diagramma");
            hBox.setMinHeight(dimCaselle);
            hBox.setMinWidth(dimCaselle);
            hBox.setStyle("-fx-background-color: " + colore + ";");
            container.getChildren().add(hBox);
            container.setPadding(new Insets(0, 4, 4, 0));
            this.tilePane.getChildren().add(container);
            tilePane.setMaxWidth(1000);
            
            // Evento per gestire il click di un giorno.
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    visualizzaDatiGiorno(data, abitudiniCompletate);
                }
            };
            hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler); 
            
        }
    }
    
    private void visualizzaDatiGiorno(LocalDate data, List<IHabit> abitudini) {
        this.giornoBox.getChildren().clear();
        
        // visualizza label giorno
        String formattedDate = data.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        giornoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 800;");
        this.giornoLabel.setText(formattedDate);
        this.giornoLabel.setVisible(true);
        this.giornoBox.setVisible(true);
        
        // visualizza label numero abitudini svolte
        String s = abitudini.size() == 1 ? "e " : "i ";
        Label label = new Label("Svolto " + abitudini.size() + "abitudin" + s +  " su " + ht.getHabits().size());
        label.setStyle("-fx-text-fill: #888EA8; -fx-font-size: 16;");
        label.setPadding(new Insets(0, 0, 15, 0));
        this.giornoBox.getChildren().add(label);
        
        // crea l'elenco di abitudini svolte
        for(IHabit h : abitudini) {
            HBox hBox = Helper.creaElementoLista(h.getName());
            this.giornoBox.getChildren().add(hBox);
        }
    }

}
