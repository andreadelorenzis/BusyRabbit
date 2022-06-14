package main.views.modal;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
import main.controller.goalmanager.GoalManagerController;
import main.controller.helpers.Helper;
import main.views.LoaderRisorse;
import main.views.accountmanager.classi.PageView;
import main.views.notification.NotificationType;
import main.views.notification.NotificationsManager;

public class Modal implements IModal {
	
	@FXML
	private BorderPane modal;
	
	public static final int LARGHEZZA = 800;
	public static final int ALTEZZA = 600;
	
	private Pane content;
	private int larghezza = LARGHEZZA;
	private int altezza = ALTEZZA;
	private Button okBtn, cancelBtn;
	private HBox okBtnContainer = new HBox(), cancelBtnContainer = new HBox();
	private ButtonType btnCliccato = ButtonType.CANCEL;
	private String titolo;
	
	public Modal(Pane content, String titolo) {
		this.content = content;
		this.titolo = titolo;
		okBtn = new Button("Salva");
		cancelBtn = new Button("Annulla");
	}
	
	private void toggleOverlay() {
		BorderPane overlay = new BorderPane();
		if(ModalsManager.getInstance().isModalOpen()) {
	        // black overlay
	        overlay.getStyleClass().add("overlay");
	        AnchorPane.setBottomAnchor(overlay, 0.0);
	        AnchorPane.setTopAnchor(overlay, 0.0);
	        AnchorPane.setLeftAnchor(overlay, 0.0);
	        AnchorPane.setRightAnchor(overlay, 0.0);
	        PageView.appContainer.getChildren().add(overlay);
		} else {
			int indice = PageView.appContainer.getChildren().size() - 1;
			PageView.appContainer.getChildren().remove(indice);
		}
	}
	
	public void setDimensioni(int larghezza, int altezza) {
		this.larghezza = larghezza;
		this.altezza = altezza;
	}
	
	public Button getButton(ButtonType tipo) {
		if(tipo.equals(ButtonType.OK)) {
			return this.okBtn;
		} else if(tipo.equals(ButtonType.CANCEL)) {
			return this.cancelBtn;
		} else {
			return null;
		}
	}
	
	public HBox getBtnLookup(ButtonType tipo) {
		if(tipo.equals(ButtonType.OK)) {
			return this.okBtnContainer;
		} else if(tipo.equals(ButtonType.CANCEL)) {
			return this.cancelBtnContainer;
		} else {
			return null;
		}
	}
	
	public void close(Stage stage) {
		stage.close();
        ModalsManager.getInstance().chiudiModal();
        toggleOverlay();
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public ButtonType show() throws IOException {
		ModalsManager.getInstance().apriModal();
		toggleOverlay();
		
		// crea la view del modal
        AnchorPane pane = new AnchorPane();
        pane.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.MODAL, "Modal"));
        pane.getStyleClass().add("modal");
        BorderPane container = new BorderPane();
        container.getStyleClass().add("modal-container");
        pane.getChildren().add(container);
        AnchorPane.setBottomAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        
        // crea un nuovo stage al centro della finestra
    	Stage stage = new Stage();
    	Rectangle2D dimSchermo = Screen.getPrimary().getVisualBounds();
    	stage.setX((dimSchermo.getWidth() / 2) - larghezza / 2);
    	stage.setY((dimSchermo.getHeight() / 2) - altezza / 2);
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane, larghezza, altezza);
        scene.setFill(Color.TRANSPARENT);
        String css = LoaderRisorse.globalCss;
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        
        // modal header
        BorderPane header = new BorderPane();
        header.getStyleClass().add("modal-header");
        Label titleLabel = new Label(titolo);
        titleLabel.getStyleClass().add("modal-title");
        HBox closeImg = Helper.creaIconaChiusura();
        header.setLeft(titleLabel);
        header.setRight(closeImg);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setAlignment(closeImg, Pos.CENTER);
        container.setTop(header);
        
        // collega evento chiusura modal
        EventHandler<MouseEvent> handlerChiusura = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	btnCliccato = ButtonType.CANCEL;
            	close(stage);
            }
        };
        closeImg.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerChiusura); 
        
        // modal content
        AnchorPane view = (AnchorPane) this.content;
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #0E1726;");
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scroll.setContent(view);
        view.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.MODAL, "Modal"));
        container.setCenter(scroll);
        
        // modal footer
        HBox footer = new HBox();
        footer.getStyleClass().add("modal-footer");
        footer.setAlignment(Pos.CENTER_RIGHT);
        okBtnContainer.getChildren().add(okBtn);
        okBtnContainer.setPadding(new Insets(0, 0, 0, 15));
        okBtn.setMinWidth(100.0);
        okBtn.getStyleClass().addAll("modal-btn", "ok-btn");
        cancelBtnContainer.getChildren().add(cancelBtn);
        cancelBtn.setMinWidth(100.0);
        cancelBtn.getStyleClass().addAll("modal-btn", "cancel-btn");
        footer.getChildren().addAll(cancelBtnContainer, okBtnContainer);
        container.setBottom(footer);
        cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerChiusura); 
        
        // collega evento OK button
        EventHandler<MouseEvent> handlerSuccesso = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	btnCliccato = ButtonType.OK;
            	close(stage);	
            }
        };
        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerSuccesso); 
        
    	stage.showAndWait();
    	return btnCliccato;
	}
	
	
	
}
