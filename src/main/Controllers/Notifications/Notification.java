package main.Controllers.Notifications;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notification {
	
	public static final int LARGHEZZA = 800;
	public static final int ALTEZZA = 60;
	
	private String messaggio;
	private NotificationType tipo;
	
	public Notification(NotificationType tipo) {
		this.messaggio = "";
		this.tipo = tipo;
	}
	
	public Notification(String messaggio, NotificationType tipo) {
		this(tipo);
		this.messaggio = messaggio;
	}
	
	public void setMessaggio(String s) {
		this.messaggio = s;
	}
	
	private void close(Stage stage) {
		stage.close();
		NotificationsManager.getInstance().decrementaNumNotifiche();
	}
	
	public void show() {
		NotificationsManager manager = NotificationsManager.getInstance();
		manager.incrementaNumNotifiche();
		int numNotifiche = manager.getNumNotifiche();
		
		// crea la view della notifica
        AnchorPane pane = new AnchorPane();
        pane.getStyleClass().add("notification");
        BorderPane container = new BorderPane();
        container.getStyleClass().add("notification-box");
        pane.getChildren().add(container);
        AnchorPane.setBottomAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setLeftAnchor(container, 0.0);
        Label label = new Label(messaggio);
        Label label2 = new Label("CLOSE");
        label.getStyleClass().add("notification-label");
        label2.getStyleClass().add("notification-close-btn");
        container.setCenter(label);
        container.setRight(label2);
        BorderPane.setAlignment(label2, Pos.CENTER);
        
        // cambia il colore in base al tipo della notifica
        if(tipo == NotificationType.ERROR) {
        	pane.setStyle("-fx-background-color: #A93C3A;");
        	label.setTextFill(Color.web("#E9817F"));
        	label2.setTextFill(Color.web("#E9817F"));
        } else if(tipo == NotificationType.SUCCESS) {
        	pane.setStyle("-fx-background-color: #33726A;");
        	label.setTextFill(Color.web("#65E1D2"));
        	label2.setTextFill(Color.web("#65E1D2"));
        } else if(tipo == NotificationType.INFO) {
        	pane.setStyle("-fx-background-color: #866E2A;");
        	label.setTextFill(Color.web("#EACC7B"));
        	label2.setTextFill(Color.web("#EACC7B"));
        }
        
        // crea un nuovo stage
    	Stage stage = new Stage();
    	Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
    	stage.setX((dimSchermo.getWidth() / 2) - LARGHEZZA / 2);
    	stage.setY((numNotifiche * (ALTEZZA + 10)) + 15);
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane, LARGHEZZA, ALTEZZA);
        scene.setFill(Color.TRANSPARENT);
        String css = this.getClass().getResource("/main/Globall.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true); 
        
        // aggiunge un tempo di visualizzazione della notifica
    	Timer timer = new Timer(3000, new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Platform.runLater(() -> {
			        close(stage);
			    });
			}	
    	});
    	timer.start();
    	timer.setRepeats(false);
    	
        // collega evento chiusura notifica
        label2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	close(stage);
            	timer.stop();
            }
        });

    	stage.show();
	}
	
}
