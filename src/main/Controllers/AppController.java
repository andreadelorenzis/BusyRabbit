package main.Controllers;

import main.FxmlLoader;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Controllers.GoalManager.GoalManagerController;
import main.Main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
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
    private HBox agendaHBox;
    @FXML
    private Label agendaLabel;
    @FXML
    private ImageView agendaImg;
    
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
    
    private HBox timeReportBox = new HBox();
    private Label timeReportLabel = new Label("Report Tempo");
    
    private HBox obiettiviReportBox = new HBox();
    private Label obiettiviReportLabel = new Label("Report Obiettivi");
    
    private HBox abitudiniReportBox = new HBox();
    private Label abitudiniReportLabel = new Label("Report Abitudini");
    
    private boolean dashboardMenuAperto = false;
    
    @FXML
    private HBox impostazioniHBox;
    @FXML
    private Label impostazioniLabel;
    @FXML
    private ImageView impostazioniImg;
                    
    @FXML
    private BorderPane panePrincipale;
    
    @FXML 
    private void initialize() throws IOException {
        String stile = "-fx-text-fill: #58698D; -fx-font-weight: 800; -fx-font-size: 16;";
        
        this.timeReportLabel.setStyle(stile);
        this.obiettiviReportLabel.setStyle(stile);
        this.abitudiniReportLabel.setStyle(stile);
        
        this.timeReportBox.getChildren().add(this.timeReportLabel);
        this.timeReportBox.setMinHeight(40);
        this.timeReportBox.setAlignment(Pos.CENTER_LEFT);
        this.timeReportBox.setPadding(new Insets(0, 0, 0, 10));
        this.obiettiviReportBox.getChildren().add(this.obiettiviReportLabel);
        this.obiettiviReportBox.setMinHeight(40);
        this.obiettiviReportBox.setAlignment(Pos.CENTER_LEFT);
        this.obiettiviReportBox.setPadding(new Insets(0, 0, 0, 10));
        this.abitudiniReportBox.getChildren().add(this.abitudiniReportLabel);
        this.abitudiniReportBox.setMinHeight(40);
        this.abitudiniReportBox.setAlignment(Pos.CENTER_LEFT);
        this.abitudiniReportBox.setPadding(new Insets(0, 0, 0, 10));
        
        // Aggiunge gli event handler dei click dei bottoni.
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                apriPaginaReportTempo(t);
            }
        };
        this.timeReportBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        
        EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                apriPaginaReportObiettivi(t);
            }
        };
        this.obiettiviReportBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);
        
        EventHandler<MouseEvent> eventHandler3 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                apriPaginaReportAbitudini(t);
            }
        };
        this.abitudiniReportBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler3);
        
        this.apriPaginaTimeTracker();
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
        
        agendaHBox.setStyle("-fx-background-color: #060818;");
        agendaLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        agendaImg.setImage(new Image(getClass().getResource("/main/risorse/agenda.png").toString()));
        
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
        
        this.obiettiviReportBox.setStyle("-fx-background-color: #060818;");
        this.obiettiviReportLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        
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
    private void apriPaginaTimeTracker() throws IOException {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(this.timeHBox, this.timeLabel, this.timeImg, "clock-white");
        this.chiudiMenuDashboard();
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/TimeTracker/TimeTracker.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);

    } 
    
    @FXML
    private void apriPaginaHabitTracker(MouseEvent event) {
       
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(habitHBox, habitLabel, habitImg, "refresh-white");
        this.chiudiMenuDashboard();
       
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/HabitTracker/HabitTracker.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaGoalManager(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(goalHBox, goalLabel, goalImg, "darts-white");
        this.chiudiMenuDashboard();
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/GoalManager/GoalManager.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaAgenda(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(agendaHBox, agendaLabel, agendaImg, "agenda-white");
        this.chiudiMenuDashboard();
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
       URL fileUrl = Main.class.getResource("/main/Views/Agenda/Agenda.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaImpostazioni(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(impostazioniHBox, impostazioniLabel, impostazioniImg, "settings-white");
        this.chiudiMenuDashboard();
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Impostazioni/Impostazioni.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);
    } 
    
    private void apriPaginaReportTempo(MouseEvent event) {
        
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
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Dashboard/ReportTempo.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);
        
    }
    
    private void apriPaginaReportObiettivi(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(this.dashboardHBox, this.dashboardLabel, this.dashboardImg, "dashboard-white");
        this.dashboardArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down-white.png").toString()));
        this.dashboardArrow.setRotate(180);
        this.obiettiviReportBox.setStyle("-fx-background-color: #374856;"
                + "-fx-border-radius:12;"
                + "-fx-border-style:solid;"
                + "-fx-background-radius:12;");
        this.obiettiviReportLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: 800;");
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Dashboard/ReportObiettivi.fxml");
        Pane view = object.getPage(fileUrl);
        panePrincipale.setCenter(view);
        
    }
    
    private void apriPaginaReportAbitudini(MouseEvent event) {
    
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
        FxmlLoader object = new FxmlLoader();
        URL fileUrl = Main.class.getResource("/main/Views/Dashboard/ReportAbitudini.fxml");
        Pane view = object.getPage(fileUrl);
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
            this.dashboardBtns.getChildren().add(this.obiettiviReportBox);
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
