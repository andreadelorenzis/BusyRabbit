package main.Views.GoalManager.classes;


import java.time.LocalDate;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Giorno;
import main.Main;
import main.Models.goalmanager.classes.Obiettivo;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Views.LoaderRisorse;

public class ViewHelper {
	public static Image arrowIcon = LoaderRisorse.arrowIcon;
	
	public static HBox creaSottoObiettiviBtn(int size, HBox box) {
		
		// crea view pulsante
		HBox hBox = new HBox();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER_LEFT);
		vBox.getChildren().add(hBox);
		hBox.getStyleClass().add("btn-sotto-obiettivi");
    	hBox.setPadding(new Insets(5, 5, 5, 0));
    	hBox.setAlignment(Pos.CENTER_LEFT);
        ImageView arrow = new ImageView();
        arrow.setFitHeight(16);
        arrow.setFitWidth(16);
        arrow.setImage(arrowIcon);
        Label label3 = new Label("" + size);
        label3.getStyleClass().add("num-sotto-obiettivi");
        hBox.getChildren().add(label3);
        hBox.getChildren().add(arrow);

        // aggiunge evento rotazione freccia 
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
            	t.consume();
            	double rotation = arrow.getRotate();
                if(rotation == 0) { 
                	arrow.setRotate(180);
                } else {
                	arrow.setRotate(0);
                }
            }
        };
        if(box != null) {
        	box.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        }
        
        hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        
        return hBox;
	}
	
}
