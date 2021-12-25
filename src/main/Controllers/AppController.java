




import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Stage;

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
    private HBox impostazioniHBox;
    @FXML
    private Label impostazioniLabel;
    @FXML
    private ImageView impostazioniImg;
                    
    @FXML
    private BorderPane panePrincipale;
    
    @FXML 
    private void initialize() throws IOException {
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
        
        impostazioniHBox.setStyle("-fx-background-color: #060818;");
        impostazioniLabel.setStyle("-fx-text-fill: #58698D; -fx-font-weight: 800;");
        impostazioniImg.setImage(new Image(getClass().getResource("/main/risorse/settings.png").toString()));
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
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("TimeTracker");
        panePrincipale.setCenter(view);

    } 
    
    @FXML
    private void apriPaginaHabitTracker(MouseEvent event) {
       
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(habitHBox, habitLabel, habitImg, "refresh-white");
       
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("HabitTracker");
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaGoalManager(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(goalHBox, goalLabel, goalImg, "darts-white");
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("GoalManager");
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaAgenda(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(agendaHBox, agendaLabel, agendaImg, "agenda-white");
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Agenda");
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaDashboard(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(dashboardHBox, dashboardLabel, dashboardImg, "dashboard-white");
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Dashboard");
        panePrincipale.setCenter(view);
    } 
    
    @FXML
    private void apriPaginaImpostazioni(MouseEvent event) {
        
        // Cambia stile pulsanti navigazione
        this.rimuoviEvidenziazionePulsanti();
        this.evidenziaPulsante(impostazioniHBox, impostazioniLabel, impostazioniImg, "settings-white");
        
        // Cambia la pagina all'interno del BorderPane
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Impostazioni");
        panePrincipale.setCenter(view);
    } 
    
}
