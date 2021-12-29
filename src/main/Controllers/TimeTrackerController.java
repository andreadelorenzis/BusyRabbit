/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Models.timetracker.classes.Attività;
import main.Models.timetracker.classes.GiornoAttività;
import main.Models.timetracker.classes.Progetto;
import src.FxmlLoader;

public class TimeTrackerController {
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private VBox listaGiorniAttività;
    
    @FXML 
    private ChoiceBox timeChoice;
    
    @FXML 
    private BorderPane menuProgetti;
    @FXML
    private VBox listaProgetti;
    
    @FXML
    private TextField attivitàText;
    
    @FXML
    private BorderPane formTimeTracker;
    
    @FXML
    private BorderPane formPomodoro;
    
    @FXML
    private BorderPane formManuale;
    
    @FXML
    private TextField orarioText1;
    
    @FXML
    private TextField orarioText2;
    
    @FXML
    private DatePicker dataManuale;
    
    @FXML
    private TextField durataText1;
    
    @FXML
    private TextField durataText2;
    
    @FXML
    private TextField durataText3;
    
    @FXML
    private HBox boxProgetto;
    
    @FXML
    private HBox pauseBtnContainer;
    
    @FXML
    private Button resetBtn;
            
    @FXML
    private Button avviaBtn;
    
    private Progetto progettoAssociato;
    
    /**
     * 
     * !!!! DEMO !!!!
     * 
     */
    private ArrayList<Progetto> progetti;
    
    /**
    * 
    * !!!! DEMO !!!!
    * 
    */
    private ArrayList<GiornoAttività> giorniAttività;
    
    private int pomodoro;
    
    private int pausaBreve;
    
    private int pausaLunga;
    
    @FXML 
    private void initialize() {
        
        ObservableList<String> list = timeChoice.getItems();
        list.add("Cronometro");
        list.add("Pomodoro");
        list.add("Manuale");
        timeChoice.setValue("Cronometro");
        timeChoice.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    this.cambiaTipoTracker((String) timeChoice.getItems().get((Integer) new_val));
        });
        
        /**
        * 
        * !!!! DEMO !!!!
        * 
        */
        this.progetti = new ArrayList<Progetto> ();
        progetti.add(new Progetto("Social Network", "#FEB019"));
        progetti.add(new Progetto("Studiare", "#00E396"));
        progetti.add(new Progetto("Workout", "#9C27B0"));

        /**
        * 
        * !!!! DEMO !!!!
        * 
        */
        giorniAttività = new ArrayList<GiornoAttività>();
        for(int i = 0; i < 3; i++) {
            ArrayList<Attività> listaAttività = new ArrayList<Attività>();
            listaAttività.add(new Attività(new Date(), 500, "Attività1", new Progetto("Social Network", "#FEB019")));
            listaAttività.add(new Attività(new Date(), 500, "Attività2", new Progetto("Studiare", "#00E396")));
            listaAttività.add(new Attività(new Date(), 500, "Attività3", new Progetto("Workout", "#9C27B0")));
            
            GiornoAttività giorno = new GiornoAttività();
            giorno.setListaAttività(listaAttività);
            
            giorniAttività.add(giorno);
        }
        
        this.visualizzaListaProgetti(progetti);
        this.visualizzaCronologiaAttività(giorniAttività);
        
        this.pomodoro = 30;
        this.pausaBreve = 5;
        this.pausaLunga = 10;
       
    }
    
    /**
     * Visualizza nella view la lista dei progetti esistenti.
     */
    private void visualizzaListaProgetti(ArrayList<Progetto> progetti) {
        for(int i = 0; i < progetti.size(); i++) {
            this.visualizzaProgetto(progetti.get(i));
        }
    }
    
    /**
     * Usa i dati di un Progetto per creare un componenete nella view.
     */
    @FXML
    private void visualizzaProgetto(Progetto progetto) {
        
        // Crea nuovo progetto
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(0, 20, 0, 10));
        pane.setMinHeight(60);
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setFill(Color.web(progetto.getColore()));
        
        HBox box3 = new HBox();
        box3.setAlignment(Pos.CENTER);
        Label label1 = new Label(progetto.getNome());
        label1.setGraphic(circle);
        label1.setStyle("-fx-text-fill: " + progetto.getColore() +";");
        box3.getChildren().add(label1);
        
        HBox containerBtn = new HBox();
        containerBtn.setAlignment(Pos.CENTER);
        //containerBtn.setStyle("-fx-background-color: red;");
        
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        ImageView edit = new ImageView();
        edit.setFitHeight(15);
        edit.setFitWidth(15);
        edit.setImage(new Image(getClass().getResource("/main/risorse/edit.png").toString()));
        box.getChildren().add(edit);
        box.setPadding(new Insets(5, 5, 5, 5));
        
        HBox box2 = new HBox();
        box2.setAlignment(Pos.CENTER);
        ImageView trash = new ImageView();
        trash.setFitHeight(13);
        trash.setFitWidth(13);
        trash.setImage(new Image(getClass().getResource("/main/risorse/trash.png").toString()));
        box2.getChildren().add(trash);
        box2.setPadding(new Insets(5, 5, 5, 5));
        
        containerBtn.getChildren().add(box);
        containerBtn.getChildren().add(box2);
        
        pane.setLeft(box3);
        pane.setRight(containerBtn);
        
        // Event handler per la modifica del progetto
        EventHandler<MouseEvent> eventHandlerModifica = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    modificaProgetto(progetto);
                } catch (IOException ex) {
                    Logger.getLogger(TimeTrackerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
       
        // Event handler per l'eliminazione di un progetto
        EventHandler<MouseEvent> eventHandlerElimina = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                eliminaProgetto(progetto);
            }
        };
       
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerModifica);
        box2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerElimina);
        
        // Collega un event handler per la scelta del progetto
        EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                cambiaProgetto(progetto);
            }
        };
        label1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);
        
        listaProgetti.getChildren().add(pane);
    }
    
    /**
     * Cambia il progett
     */
    private void cambiaProgetto(Progetto progetto) {
        
        // Crea nuovo progetto
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setFill(Color.web(progetto.getColore()));
        Label label = new Label(progetto.getNome());
        label.setGraphic(circle);
        label.setStyle("-fx-text-fill: " + progetto.getColore() +";");
        
        this.progettoAssociato = progetto;
        this.boxProgetto.getChildren().clear(); 
        this.boxProgetto.getChildren().add(label);
        
    }
    
    /**
     * Apre il il form dei progetti, aggiunge un Progetto e aggiorna la view.
     */
    @FXML
    private void aggiungiProgetto() throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/EditorProgetto.fxml"));
        DialogPane newProject = fxmlLoader.load();
        newProject.getStylesheets().add(getClass().getResource("/src/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(newProject);
        dialog.setTitle("Nuovo progetto");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorProgettoController editorController = fxmlLoader.getController();
        editorController.setProgetto(new Progetto("", ""));
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            // Aggiungere il progetto nel modello.
            this.progetti.add(editorController.getProgetto());
            
            // Aggiornare la view
            this.listaProgetti.getChildren().clear();
            this.visualizzaListaProgetti(this.progetti);
        }
    }
    
    /**
     * Modifica un Progetto e aggiorna la view.
     */
    @FXML
    private void modificaProgetto(Progetto progetto) throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/EditorProgetto.fxml"));
        DialogPane newProject = fxmlLoader.load();
        newProject.getStylesheets().add(getClass().getResource("/src/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(newProject);
        dialog.setTitle("Modifica progetto");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorProgettoController editorController = fxmlLoader.getController();
        editorController.setProgetto(progetto);
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            // Modificare il progetto nel modello.
            for(int i = 0; i < this.progetti.size() - 1; i++) {
                if(progetto == this.progetti.get(i)) {
                    
                }
            }
            
            // Refreshare la lista di componenti nella view
            this.listaProgetti.getChildren().clear();
            this.visualizzaListaProgetti(this.progetti);
            
        }
    }
    
    /**
     * Elimina un Progetto e aggiorna la view.
     */
    private void eliminaProgetto(Progetto progetto) {
        System.out.println("Progetto: " + progetto.getNome() + " eliminato!");
    }
    
    /**
     * Apre il menù a tendina della lista progetti.
     */
    @FXML
    private void toggleMenuProgetto() {
        if(menuProgetti.isVisible()) 
            menuProgetti.setVisible(false);
        else
            menuProgetti.setVisible(true);    
    }
    
    /**
     * Crea e inserisce nella view il componente per poter cambiare pagina attività.
     */
    @FXML
    private HBox creaFormPagine() {
        
        HBox form = new HBox();
        form.setAlignment(Pos.CENTER_LEFT);
        
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #0E1726; -fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 3;");
        pane.setPadding(new Insets(0, 15, 0, 15));
        
        ImageView leftArrow = new ImageView();
        leftArrow.setFitHeight(15);
        leftArrow.setFitWidth(15);
        leftArrow.setRotate(-90);
        leftArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow.png").toString()));
        BorderPane.setAlignment(leftArrow, Pos.CENTER);
        leftArrow.setStyle("-fx-border-color: #191E3A; -fx-border-width: 0 0 0 3;");
        
        ImageView rightArrow = new ImageView();
        rightArrow.setFitHeight(15);
        rightArrow.setFitWidth(15);
        rightArrow.setRotate(90);
        rightArrow.setImage(new Image(getClass().getResource("/main/risorse/arrow.png").toString()));
        BorderPane.setAlignment(rightArrow, Pos.CENTER);
        rightArrow.setStyle("-fx-border-color: #191E3A; -fx-border-width: 0 3 0 0;");
        
        Label label = new Label("1-50 di 832");
        label.setPadding(new Insets(12, 15, 12, 15));
        label.setStyle("-fx-text-fill: #ffffff; -fx-border-color: #191E3A; -fx-border-width: 0;");
        
        pane.setLeft(leftArrow);
        pane.setRight(rightArrow);
        pane.setCenter(label);
        
        form.getChildren().add(pane);
        
        return form;
    }
    
    /**
     * Visualizza nella view le informazioni sulle attività passate.
     */
    private void visualizzaCronologiaAttività(ArrayList<GiornoAttività> giorniAttività) {
        for(int i = 0; i < giorniAttività.size(); i++) {
            this.visualizzaGiornoAttività(giorniAttività.get(i));
        }
        HBox formPagine = this.creaFormPagine();
        listaGiorniAttività.getChildren().add(formPagine);
    }
    
    
    /**
     * Usa i dati di un GiornoAttività per creare e poi inserire un componenente nella view.
     */
    @FXML
    private void visualizzaGiornoAttività(GiornoAttività giorno) {
        
        // Crea un BorderPane container.
        BorderPane giornoAttivitàContainer = new BorderPane();
        giornoAttivitàContainer.setStyle("-fx-border-radius: 14; -fx-background-radius: 12;");
        giornoAttivitàContainer.setPadding(new Insets(0, 0, 40, 0));
        
        // Crea un VBox da mettere nel BorderPane.
        VBox giornoAttività = new VBox();
        giornoAttività.setStyle("-fx-background-color: #0E1726; -fx-border-radius: 14; -fx-background-radius: 12;");
        
        // Crea un BorderPane che fungerà da header.
        BorderPane header = creaHeaderGiornoAttività();
        header.setMinHeight(50);
        
        // Crea un VBox come lista per le attività svolte durante il giorno.
        VBox listaAttività = new VBox();
        
        for(int i = 0; i < giorno.getListaAttività().size(); i++) {
            Attività attività = giorno.getListaAttività().get(i);
            
            // Crea un'attività e la aggiunge alla lista.
            BorderPane pane = visualizzaAttività(attività);
            listaAttività.getChildren().add(pane);
        }
        
        // Aggiunge header e lista attività al VBox.
        giornoAttività.getChildren().add(header);
        giornoAttività.getChildren().add(listaAttività);
        
        // Mette il giorno attività nel container.
        giornoAttivitàContainer.setCenter(giornoAttività);
        
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #122C49;");
        scroll.getStyleClass().add("edge-to-edge");
      
        
        // Inserisce l'oggetto creato nella view.
        this.listaGiorniAttività.getChildren().add(giornoAttivitàContainer);
        
    }
    
    /**
     * Crea e inserisce nella view il pulsante di aggiunta progetto.
     */
    private HBox creaBtnProgetto() {
        HBox btnProgetto = new HBox();
        btnProgetto.setAlignment(Pos.CENTER);
        ImageView plus = new ImageView();
        plus.setFitHeight(15);
        plus.setFitWidth(15);
        plus.setImage(new Image(getClass().getResource("/main/risorse/plus.png").toString()));
        Label label6 = new Label("Progetto");
        label6.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14;");
        label6.setPadding(new Insets(0, 0, 0, 10));
        btnProgetto.getChildren().add(plus);
        btnProgetto.getChildren().add(label6);
        return btnProgetto;
    }
    
    /**
     * Usa i dati di un GiornoAttività per creare l'header del componente nella view.
     */
    private BorderPane creaHeaderGiornoAttività() {
        
        // Crea un BorderPane.
        BorderPane header = new BorderPane();
        header.setStyle("-fx-background-color: #191E3A; -fx-pref-height: 50; -fx-alignment: center; -fx-border-radius: 12 12 0 0; -fx-background-radius: 12 12 0 0;");
        header.setPadding(new Insets(0, 20, 0, 20));
        
        // Crea parte destra del BorderPane.
        HBox headerDestra = new HBox();
        headerDestra.setAlignment(Pos.CENTER);
        Label label1 = new Label("Total:");
        label1.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: 800;");
        headerDestra.getChildren().add(label1);     
        Label label2 = new Label("04:53:23");
        label2.setStyle("-fx-text-fill: #888ea8; -fx-font-size: 14; -fx-font-weight: 800;");
        label2.setPadding(new Insets(0, 20, 0, 10));
        headerDestra.getChildren().add(label2);
        ImageView dots = new ImageView();
        dots.setFitHeight(18);
        dots.setFitWidth(6);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        headerDestra.getChildren().add(dots);
        
        // Crea parte sinistra del BorderPane.
        HBox headerSinistra = new HBox();
        headerSinistra.setAlignment(Pos.CENTER);
        Label label3 = new Label("Nov 17, 2020");
        label3.setStyle("-fx-text-fill: #BAC4CA; -fx-alignment: center; -fx-font-size: 14;");
        headerSinistra.getChildren().add(label3);
        
        // Imposta parte sinistra e destra del BorderPane.
        header.setLeft(headerSinistra);
        header.setRight(headerDestra);
        
        return header;
    }
    
    /**
     * 
     */
    @FXML
    private void avviaTimeTracker(ActionEvent event) throws IOException {
        
        if(this.attivitàText.getText() != "") {
            this.aggiungiAttività(new Attività(new Date(), 5, this.attivitàText.getText()));
        } else {
            System.out.println("Perfavore inserisci il nome di un'attività.");
        }
    }
    
    /**
     * Aggiunge una nuova Attività e aggiorna la view.
     */
    @FXML
    private void aggiungiAttività(Attività attività) throws IOException {
        
        // Aggiungere l'attività nel modello.

        /**
         * 
         * !!!! DEMO !!!!
         * 
         */
        
        // Se l'ultimo giorno della cronologia è ieri crea un nuovo giorno e aggiungi l'attività.
        GiornoAttività giorno = new GiornoAttività();
        ArrayList<Attività> listaAttività = new ArrayList<Attività>();
        attività.setProgettoPadre(this.progettoAssociato);
        listaAttività.add(attività);
        giorno.setListaAttività(listaAttività);
        this.giorniAttività.add(0, giorno);

        // Refreshare la lista attività della view
        this.listaGiorniAttività.getChildren().clear();
        this.visualizzaCronologiaAttività(giorniAttività);
            
    }
    
    /**
     * Modifica un'attività e aggiorna la view.
     */
    private void modificaAttività(Attività attività) throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/EditorAttività.fxml"));
        DialogPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/src/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(editor);
        dialog.setTitle("Modifica Attività");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorAttivitàController editorController = fxmlLoader.getController();
        System.out.println(this.progetti.get(1));
        editorController.setListaProgetti(this.progetti);
        editorController.setAttività(attività);
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            // Refreshare la lista attività della view
            this.listaGiorniAttività.getChildren().clear();
            this.visualizzaCronologiaAttività(this.giorniAttività);
            System.out.println(attività.getNome() + " " + attività.getProgetto().getNome());
            
        }
    }
    
    /**
     * Elimina un'attività e aggiorna la view.
     */
    private void eliminaAttività(Attività attività) {
        System.out.println("Attività: " + attività.getNome() + " eliminata.");
    }
    
    /**
     * Usa i dati di un'Attività per creare un componente nella view.
     */
    private BorderPane visualizzaAttività(Attività attività) {
        
        // Crea un BorderPane.
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-border-color: #191E3A; -fx-border-width: 2 0 0 0; ");
        pane.setPadding(new Insets(0, 20, 0, 20));
        pane.setMinHeight(60);
        
        // Crea la parte sinistra del BorderPane.
        HBox attivitàSinistra = new HBox();
        attivitàSinistra.setAlignment(Pos.CENTER);
        attivitàSinistra.setFillHeight(true);
        Label label2 = new Label(attività.getNome());
        label2.setStyle("-fx-font-size: 14; -fx-text-fill: #888EA8;");
        label2.setPadding(new Insets(0, 20, 0, 20));
        attivitàSinistra.getChildren().add(label2);
        
        // Visualizza progetto associato ad attività.
        Label label5;
        if(attività.getProgetto() != null) {
           label5 = new Label(attività.getProgetto().getNome());
           Circle circle = new Circle();
           circle.setRadius(3);
           circle.setFill(Color.web(attività.getProgetto().getColore()));
           label5.setGraphic(circle);
           label5.setStyle("-fx-text-fill: " + attività.getProgetto().getColore() + ";");
        } else {
            label5 = new Label("Altro");
            Circle circle = new Circle();
            circle.setRadius(3);
            circle.setFill(Color.web("#E5E5E5"));
            label5.setGraphic(circle);
            label5.setStyle("-fx-text-fill: #E5E5E5;");
        }
        attivitàSinistra.getChildren().add(label5);
        
        // Crea la parte destra del BorderPane.
        HBox attivitàDestra = new HBox();
        attivitàDestra.setAlignment(Pos.CENTER);
        attivitàDestra.setFillHeight(true);
        Label label3 = new Label("12:52  -  8:56");
        label3.setStyle("-fx-font-size: 14; -fx-text-fill: #888EA8; -fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 0 3 0 3;");
        label3.setPadding(new Insets(20, 25, 20, 25));
        Label label4 = new Label("05:53:23");
        label4.setStyle("-fx-text-fill: #888ea8; -fx-font-size: 14; -fx-font-weight: 800; -fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 0 3 0 0;");
        label4.setPadding(new Insets(20, 25, 20, 25));
        
        HBox box = new HBox();
        ImageView dots = new ImageView();
        dots.setFitHeight(18);
        dots.setFitWidth(6);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        box.getChildren().add(dots);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 23, 5, 5));
        
        HBox box2 = new HBox();
        ImageView trash = new ImageView();
        box2.setStyle("-fx-border-style: solid; -fx-border-color: #191E3A; -fx-border-width: 0 0 0 3;");
        box2.setPadding(new Insets(5, 5, 5, 20));
        trash.setFitHeight(18);
        trash.setFitWidth(18);
        trash.setImage(new Image(getClass().getResource("/main/risorse/trash.png").toString()));
        box2.getChildren().add(trash);
        box2.setAlignment(Pos.CENTER);
        
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0, 25, 0, 0));
        container.getChildren().add(label3);
        container.getChildren().add(label4);
        attivitàDestra.getChildren().add(container);
        attivitàDestra.getChildren().add(box);
        attivitàDestra.getChildren().add(box2);
        
        // Evento per il click del menù attività
        EventHandler<MouseEvent> eventHandlerMenu = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    modificaAttività(attività);
                } catch (IOException ex) {
                    Logger.getLogger(TimeTrackerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
        // Evento per il click del pulsante elimina attività.
        EventHandler<MouseEvent> eventHandlerElimina = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                eliminaAttività(attività);
            }
        };
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerMenu);   
        box2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerElimina);   

        
        // Imposta parte sinistra e destra del BorderPane.
        pane.setLeft(attivitàSinistra);
        pane.setRight(attivitàDestra);
        
        return pane;
    }
    
    @FXML
    private void avviaPomodoroTimer() throws IOException {
        if(this.attivitàText.getText() != "") {
            
            // Cambia pulsanti
            this.avviaBtn.setVisible(false);
            this.resetBtn.setVisible(true);
            Button btn = new Button();
            btn.setText("PAUSA");
            ImageView pausa = new ImageView();
            pausa.setFitHeight(13);
            pausa.setFitWidth(13);
            pausa.setImage(new Image(getClass().getResource("/main/risorse/pause.png").toString()));
            btn.setGraphic(pausa);
            btn.setContentDisplay(ContentDisplay.RIGHT);
            btn.setStyle("-fx-background-color: #E7515A; -fx-text-fill: #ffffff; -fx-font-size: 12; -fx-font-weight: 800;");
            btn.setPadding(new Insets(10, 20, 10, 20));
            
            // Event handler per il pulsante di pausa
            EventHandler<ActionEvent> eventHandlerBtn = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    sospendiPomodoroTimer();
                }
            };
            btn.addEventHandler(ActionEvent.ACTION, eventHandlerBtn);
            
            this.pauseBtnContainer.getChildren().add(btn);
            this.pauseBtnContainer.setAlignment(Pos.CENTER);
            this.pauseBtnContainer.setMinWidth(100);
            
            //this.aggiungiAttività(new Attività(new Date(), 5, this.attivitàText.getText()));
        } else {
            System.out.println("Perfavore inserisci il nome di un'attività.");
        }
    }
    
    @FXML
    private void sospendiPomodoroTimer() {
        this.avviaBtn.setVisible(true);
        this.resetBtn.setVisible(false);
        this.pauseBtnContainer.getChildren().clear();
        this.pauseBtnContainer.setMinWidth(0);
    }
    
    @FXML
    private void resettaPomodoroTimer() {
        this.avviaBtn.setVisible(true);
        this.resetBtn.setVisible(false);
        this.pauseBtnContainer.getChildren().clear();
        this.pauseBtnContainer.setMinWidth(0);
    }
    
    @FXML private void impostaTimer() throws IOException {
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/ImpostazioniTimer.fxml"));
        DialogPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/src/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(editor);
        dialog.setTitle("Imposta Pomodoro Timer");
        
        // Ottiene il controller EditorProgettoController associato alla view
        ImpostazioniTimerController controller = fxmlLoader.getController();
        controller.setPomodoro(this.pomodoro);
        controller.setPausaBreve(this.pausaBreve);
        controller.setPausaLunga(this.pausaLunga);
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            System.out.println("Impostazioni timer cambiate.");
            System.out.println("Nuove impostazioni:");
            System.out.println("Pomodoro: " + controller.getPomodoro());
            System.out.println("Pausa breve: " + controller.getPausaBreve());
            System.out.println("Pausa lunga: " + controller.getPausaLunga());
        }
    }
    
    @FXML
    private void creaAttivitàManualmente() throws IOException {
        if(this.attivitàText.getText() == "") {
            System.out.println("Perfavore inserisci il nome dell'attività.");
            return;
        } else if (this.orarioText1.getText() == "" || this.orarioText2.getText() == "") {
            System.out.println("Perfavore inserisci l'orario di inizio attività.");
            return;
        } else if (this.durataText1.getText() == "" || this.durataText2.getText() == "" || this.durataText3.getText() == "") {
            System.out.println("Perfavore inserisci la durata dell'attività.");
            return;
        }
        
        this.aggiungiAttività(new Attività(new Date(), 5, this.attivitàText.getText(), new Progetto("Altro", "#E5E5E5")));
    }
    
    private void cambiaTipoTracker(String tipo) {
           switch (tipo) {
            case "Cronometro" -> this.cambiaInTimeTracker();
            case "Pomodoro" -> this.cambiaInPomodoro();
            case "Manuale" -> this.cambiaInManuale();
            default -> {
            }
        }
    }
    
    private void cambiaInTimeTracker() {
        
        // Nascondi gli altri form
        this.formManuale.setVisible(false);
        this.formPomodoro.setVisible(false);
        
        // Visualizza il form del pomodoro timer
        this.formTimeTracker.setVisible(true);
        
    }
    
    private void cambiaInPomodoro() {
        
        // Nascondi gli altri form
        this.formManuale.setVisible(false);
        this.formTimeTracker.setVisible(false);
        
        // Visualizza il form del pomodoro timer
        this.formPomodoro.setVisible(true);
        
    }
    
    private void cambiaInManuale() {
        
        // Nascondi gli altri form
        this.formTimeTracker.setVisible(false);
        this.formPomodoro.setVisible(false);
        
        // Visualizza il form del pomodoro timer
        this.formManuale.setVisible(true);
        
    }
    
    
    
}
