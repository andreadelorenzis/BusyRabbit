package main.Controllers;

import java.io.IOException;
import java.net.URL;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.Controllers.GoalManager.GoalManagerControllerImpl;
import main.Controllers.HabitTracker.HabitTrackerControllerImpl;
import main.Controllers.Impostazioni.ImpostazioniController;
import main.Controllers.TimeTracker.TimeTrackerController;
import main.Controllers.TimeTracker.TimeTrackerControllerImpl;
import main.Models.accountmanager.interfaces.IApp;
import main.Views.Dashboard.classes.ReportTempoViewImpl;
import main.Views.TimeTracker.classes.TimeTrackerViewImpl;
import main.Views.TimeTracker.interfaces.TimeTrackerView;
import main.Main;

public class AppController {
    @FXML
    private HBox timeHBox;
    @FXML
    private Label timeLabel;
    @FXML
    private ImageView timeImg;
    @FXML
    private HBox goalHBox;
    @FXML
    private Label goalLabel;
    @FXML
    private ImageView goalImg;
    @FXML
    private HBox habitHBox;
    @FXML
    private Label habitLabel;
    @FXML
    private ImageView habitImg;
    @FXML
    private HBox dashboardHBox;
    @FXML
    private Label dashboardLabel;
    @FXML
    private ImageView dashboardImg;
    @FXML
    private VBox dashboardBtns;
    @FXML
    private ImageView dashboardArrow;
    @FXML
    private HBox impostazioniHBox;
    @FXML
    private Label impostazioniLabel;
    @FXML
    private ImageView impostazioniImg;               
    @FXML
    private BorderPane panePrincipale;
    @FXML
    private VBox sidebar;
    
    private HBox timeReportBox = new HBox();
    private Label timeReportLabel = new Label("Report Tempo");
    
    private HBox abitudiniReportBox = new HBox();
    private Label abitudiniReportLabel = new Label("Report Abitudini");
    
    private boolean dashboardMenuAperto = false;
    private boolean sidebarAperta = true;
    private IApp app;
    
    @FXML 
    private void initialize() throws IOException {
        String stile = "-fx-text-fill: #58698D; -fx-font-weight: 800; -fx-font-size: 16;";
        
        this.timeReportLabel.setStyle(stile);
        this.abitudiniReportLabel.setStyle(stile);
        
        this.timeReportBox.getChildren().add(this.timeReportLabel);
        this.timeReportBox.setMinHeight(40);
        this.timeReportBox.setAlignment(Pos.CENTER_LEFT); 
        this.timeReportBox.setPadding(new Insets(0, 0, 0, 10));
        this.abitudiniReportBox.getChildren().add(this.abitudiniReportLabel);
        this.abitudiniReportBox.setMinHeight(40);
        this.abitudiniReportBox.setAlignment(Pos.CENTER_LEFT);
        this.abitudiniReportBox.setPadding(new Insets(0, 0, 0, 10));
        
        // Aggiunge gli event handler dei click dei bottoni.
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                try {
					apriPaginaReportTempo();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        };
        this.timeReportBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        
        EventHandler<MouseEvent> eventHandler3 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                try {
					apriPaginaReportAbitudini();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        };
        this.abitudiniReportBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler3);
    }
    
    public void setAppData(IApp app) throws IOException {
    	this.app = app;
    	apriPaginaTimeTracker();
    }
    
    private void rimuoviEvidenziazionePulsanti() {
        timeHBox.setStyle("-fx-background-color: #060818;");
        timeLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        timeImg.setImage(new Image(getClass().getResource("/main/risorse/clock.png").toString()));
        
        goalHBox.setStyle("-fx-background-color: #060818;");
        goalLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        goalImg.setImage(new Image(getClass().getResource("/main/risorse/darts.png").toString()));
        
        habitHBox.setStyle("-fx-background-color: #060818;");
        habitLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        habitImg.setImage(new Image(getClass().getResource("/main/risorse/refresh.png").toString()));
        
        dashboardHBox.setStyle("-fx-background-color: #060818;");
        dashboardLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        dashboardImg.setImage(new Image(getClass().getResource("/main/risorse/dashboard.png").toString()));
        this.dashboardArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down.png").toString()));
        this.dashboardArrow.setRotate(0);
        
        impostazioniHBox.setStyle("-fx-background-color: #060818;");
        impostazioniLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        impostazioniImg.setImage(new Image(getClass().getResource("/main/risorse/settings.png").toString()));
        
        this.timeReportBox.setStyle("-fx-background-color: #060818;");
        this.timeReportLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        
        this.abitudiniReportBox.setStyle("-fx-background-color: #060818;");
        this.abitudiniReportLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
    }
    
    private void evidenziaPulsante(HBox hBox, Label label, ImageView imageView, String imgFile) {
        hBox.setStyle("-fx-background-color: #374856;"
                + "-fx-border-radius:12;"
                + "-fx-border-style:solid;"
                + "-fx-background-radius:12;");
        label.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: 800;");
        imageView.setImage(new Image(getClass().getResource("/main/risorse/" + imgFile + ".png").toString()));
    }
    
    @FXML
    private void toggleSidebar() {
    	if(sidebarAperta) {
    		sidebarAperta = false;
        	panePrincipale.setLeft(null);
    	} else {
    		sidebarAperta = true;
        	panePrincipale.setLeft(sidebar);
    	}
    }
    
    @FXML
    private void apriPaginaTimeTracker() throws IOException {

        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(this.timeHBox, this.timeLabel, this.timeImg, "clock-white");
        this.chiudiMenuDashboard();
        
        // crea la view
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fileUrl = Main.class.getResource("/main/Views/TimeTracker/resources/TimeTracker.fxml");
        fxmlLoader.setLocation(fileUrl);
        Pane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/TimeTracker/resources/TimeTracker.css").toExternalForm());
        view.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaHabitTracker() throws IOException {
       
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(habitHBox, habitLabel, habitImg, "refresh-white");
        this.chiudiMenuDashboard();
       
        // Cambia la pagina all'interno del BorderPane
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fileUrl = Main.class.getResource("/main/Views/HabitTracker/resources/HabitTracker.fxml");
        fxmlLoader.setLocation(fileUrl);
        Pane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/HabitTracker/resources/HabitTracker.css").toExternalForm());
        view.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaGoalManager() throws IOException {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(goalHBox, goalLabel, goalImg, "darts-white");
        this.chiudiMenuDashboard();
        
        // Cambia la pagina all'interno del BorderPane
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fileUrl = Main.class.getResource("/main/Views/GoalManager/resources/GoalManager.fxml");
        fxmlLoader.setLocation(fileUrl);
        Pane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/GoalManager/resources/GoalManager.css").toExternalForm());
        view.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        panePrincipale.setCenter(view);
    } 
    
    private void apriPaginaReportTempo() throws IOException {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(this.dashboardHBox, this.dashboardLabel, this.dashboardImg, "dashboard-white");
        this.dashboardArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down-white.png").toString()));
        this.dashboardArrow.setRotate(180);
        this.timeReportBox.setStyle("-fx-background-color: #374856;"
                + "-fx-border-radius:12;"
                + "-fx-border-style:solid;"
                + "-fx-background-radius:12;");
        this.timeReportLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: 800;");
        
        // Cambia la pagina all'interno del BorderPane
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Dashboard/resources/ReportTempo.fxml");
        fxmlLoader.setLocation(fileUrl);
        Pane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/Dashboard/resources/Dashboard.css").toExternalForm());
        view.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        panePrincipale.setCenter(view);
        
    }
    
    @FXML
    private void apriPaginaImpostazioni() throws IOException {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(impostazioniHBox, impostazioniLabel, impostazioniImg, "settings-white");
        this.chiudiMenuDashboard();
        
        // Cambia la pagina all'interno del BorderPane
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Impostazioni/resources/Impostazioni.fxml");
        fxmlLoader.setLocation(fileUrl);
        Pane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/Impostazioni/resources/Impostazioni.css").toExternalForm());
        view.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        panePrincipale.setCenter(view);
    } 
    
    private void apriPaginaReportAbitudini() throws IOException {
    
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(this.dashboardHBox, this.dashboardLabel, this.dashboardImg, "dashboard-white");
        this.dashboardArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down-white.png").toString()));
        this.dashboardArrow.setRotate(180);
        this.abitudiniReportBox.setStyle("-fx-background-color: #374856;"
                + "-fx-border-radius:12;"
                + "-fx-border-style:solid;"
                + "-fx-background-radius:12;");
        this.abitudiniReportLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: 800;");
        
        // Cambia la pagina all'interno del BorderPane
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Dashboard/resources/ReportAbitudini.fxml");
        fxmlLoader.setLocation(fileUrl);
        Pane view = fxmlLoader.load();
        view.getStylesheets().add(getClass().getResource("/main/Views/Dashboard/resources/ReportAbitudini.css").toExternalForm());
        view.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        panePrincipale.setCenter(view);
    }
    
    @FXML
    private void toggleMenuDashboard() {
        if(!this.dashboardMenuAperto) {
            // Evidenza il pulsante della Dashboard
            this.rimuoviEvidenziazionePulsanti();
            this.evidenziaPulsante(this.dashboardHBox, this.dashboardLabel, this.dashboardImg, "dashboard-white");
            this.dashboardArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down-white.png").toString()));
            this.dashboardArrow.setRotate(180);
            
            // Fa comparire i pulsanti nel sotto-menu del pulsante dashboard.
            this.dashboardBtns.getChildren().add(this.timeReportBox);
            this.dashboardBtns.getChildren().add(this.abitudiniReportBox);
            
            this.dashboardMenuAperto = true;
        } else {
            this.dashboardBtns.getChildren().clear();
            this.dashboardMenuAperto = false;
            this.dashboardArrow.setRotate(0);
        }
    }
    
    private void chiudiMenuDashboard() {
        this.dashboardBtns.getChildren().clear();
        this.dashboardMenuAperto = false;
        this.dashboardImg.setImage(new Image(getClass().getResource("/main/risorse/dashboard.png").toString()));
    }
    
}
