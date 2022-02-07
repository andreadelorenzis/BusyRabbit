package main.Controllers;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.PageController;
import main.Controllers.GoalManager.GoalManagerController;
import main.Controllers.Notifications.NotificationType;
import main.Controllers.Notifications.NotificationsManager;

public class Modal {
	
	@FXML
	private BorderPane modal;
	
	public static final int LARGHEZZA = 800;
	public static final int ALTEZZA = 600;
	
	private URL fileUrl;
	private boolean isOpen = false;
	String okBtnText = "Salva";
	String cancelBtnText = "Annulla";
	
	public Modal(URL fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	private void toggleOverlay() {
		BorderPane overlay = new BorderPane();
		if(isOpen) {
	        // black overlay
	        overlay.getStyleClass().add("overlay");
	        AnchorPane.setBottomAnchor(overlay, 0.0);
	        AnchorPane.setTopAnchor(overlay, 0.0);
	        AnchorPane.setLeftAnchor(overlay, 0.0);
	        AnchorPane.setRightAnchor(overlay, 0.0);
	        PageController.appContainer.getChildren().add(overlay);
		} else {
			int indice = PageController.appContainer.getChildren().size() - 1;
			PageController.appContainer.getChildren().remove(indice);
		}
	}
	
	public void setOkText(String s) {
		this.okBtnText = s;
	}
	
	public void setCancelText(String s) {
		this.cancelBtnText = s;
	}
	
	public void show() throws IOException {
		isOpen = true;
		toggleOverlay();
		
		// crea la view del modal
        AnchorPane pane = new AnchorPane();
        pane.getStylesheets().add(getClass().getResource("/main/Views/Modal.css").toExternalForm());
        pane.getStyleClass().add("modal");
        BorderPane container = new BorderPane();
        container.getStyleClass().add("modal-container");
        pane.getChildren().add(container);
        AnchorPane.setBottomAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        
        // modal header
        BorderPane header = new BorderPane();
        header.getStyleClass().add("modal-header");
        Label titolo = new Label("Titolo");
        titolo.getStyleClass().add("modal-title");
        HBox closeImg = Helper.creaIconaChiusura();
        header.setLeft(titolo);
        header.setRight(closeImg);
        BorderPane.setAlignment(titolo, Pos.CENTER);
        BorderPane.setAlignment(closeImg, Pos.CENTER);
        container.setTop(header);
        
        // modal content
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(fileUrl);
        AnchorPane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/Modal.css").toExternalForm());
        container.setCenter(view);
        
        // modal footer
        HBox footer = new HBox();
        footer.getStyleClass().add("modal-footer");
        footer.setAlignment(Pos.CENTER_RIGHT);
        HBox okbtnContainer = new HBox();
        Button okBtn = new Button(okBtnText);
        okbtnContainer.getChildren().add(okBtn);
        okbtnContainer.setPadding(new Insets(0, 0, 0, 15));
        okBtn.setMinWidth(100.0);
        okBtn.getStyleClass().addAll("modal-btn", "ok-btn");
        Button cancelBtn = new Button(cancelBtnText);
        cancelBtn.setMinWidth(100.0);
        cancelBtn.getStyleClass().addAll("modal-btn", "cancel-btn");
        footer.getChildren().addAll(cancelBtn, okbtnContainer);
        container.setBottom(footer);

        // crea un nuovo stage al centro della finestra
    	Stage stage = new Stage();
    	Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
    	stage.setX((dimSchermo.getWidth() / 2) - LARGHEZZA / 2);
    	stage.setY((dimSchermo.getHeight() / 2) - ALTEZZA / 2);
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane, LARGHEZZA, ALTEZZA);
        scene.setFill(Color.TRANSPARENT);
        String css = this.getClass().getResource("/main/Globall.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
        
        // aggiunge un tempo di visualizzazione della notifica
//    	Timer timer = new Timer(7000, new ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent e) {
//				Platform.runLater(() -> {
//			        stage.close();
//			        isOpen = false;
//			        toggleOverlay();
//			    });
//			}	
//    	});
//    	timer.setRepeats(false);
//    	timer.start();
	}
	
}
