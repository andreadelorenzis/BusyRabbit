package main.Views.Notifications;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Main;
import main.Views.LoaderRisorse;

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
        Animation transition = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.LINEAR);
            }
            @Override
            protected void interpolate(double frac) {
            	if(stage.getY() > 0) {
            		stage.setY(stage.getY() - frac * 200);    
            	} else {
            		stage.close();
            		NotificationsManager.getInstance().decrementaNumNotifiche();
            	}
            }
        };
        transition.play();
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
        label.getStyleClass().add("notification-label");
        HBox imgContainer = new HBox();
        ImageView closeImg = new ImageView();
        imgContainer.getChildren().add(closeImg);
        closeImg.setFitHeight(18);
        closeImg.setFitWidth(18);
        imgContainer.getStyleClass().add("notification-close-img");
        imgContainer.setPadding(new Insets(0, 20, 0, 0));
        imgContainer.setAlignment(Pos.CENTER);
        container.setCenter(label);
        container.setRight(imgContainer);
        BorderPane.setAlignment(imgContainer, Pos.CENTER);
        
        // cambia il colore in base al tipo della notifica
        if(tipo == NotificationType.ERROR) {
        	pane.setStyle("-fx-background-color: #A93C3A;");
        	label.setTextFill(Color.web("#E9817F"));
        	closeImg.setImage(LoaderRisorse.closeRed);
        } else if(tipo == NotificationType.SUCCESS) {
        	pane.setStyle("-fx-background-color: #33726A;");
        	label.setTextFill(Color.web("#65E1D2"));
        	closeImg.setImage(LoaderRisorse.closeGreen);
        } else if(tipo == NotificationType.INFO) {
        	pane.setStyle("-fx-background-color: #866E2A;");
        	label.setTextFill(Color.web("#EACC7B"));
        	closeImg.setImage(LoaderRisorse.closeYellow);
        }
        
        // crea un nuovo stage
    	Stage stage = new Stage();
    	Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane, LARGHEZZA, ALTEZZA);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(LoaderRisorse.globalCss);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true); 
        
        // aggiunge un tempo di visualizzazione della notifica
    	Timer timer = new Timer(2000, new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Platform.runLater(() -> {
			        close(stage);
			    });
			}	
    	});
    	timer.start();
    	timer.setRepeats(false);
    	
        // collega evento chiusura notifica
    	imgContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	close(stage);
            	timer.stop();
            }
        });
        
        stage.show();
        stage.setX((dimSchermo.getWidth() / 2) - LARGHEZZA / 2);
        stage.setY(0 - ALTEZZA);
    	double maxPosition = (numNotifiche * (ALTEZZA + 10));
        Animation transition = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.LINEAR);
            }
            @Override
            protected void interpolate(double frac) {
            	if(stage.getY() < maxPosition) {
            		stage.setY(stage.getY() + frac * 200);    
            	}            
            }
        };
        transition.play();
        
        
	}
	
}
