/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.GoalManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Controllers.GoalManager.demo.IObiettivo;
import main.Controllers.GoalManager.demo.IObiettivoMisurabile;
import main.Controllers.GoalManager.demo.ObiettivoDemo;
import main.Controllers.GoalManager.demo.ObiettivoMisurabileDemo;

/**
 *
 * @author andre
 */
public class GoalManagerController {
    
    @FXML 
    private BorderPane obiettivo0;
    
    @FXML
    private VBox boxObiettivi;
    private ArrayList<VBox> listaBoxSottoObiettivi;
    
    private ArrayList<IObiettivo> listaObiettivi;
    
    private boolean listaSottoObiettiviAperta = false;
    
    @FXML
    private void initialize() {
        
        // ----> DEMO
        
        this.listaObiettivi = new ArrayList<IObiettivo>();
        
        ArrayList<ObiettivoDemo> sottoObiettivi = new ArrayList<ObiettivoDemo>();
        sottoObiettivi.add(new ObiettivoDemo("Superare Analisi", "", new Date(), new ArrayList<ObiettivoDemo>()));
        sottoObiettivi.add(new ObiettivoDemo("Superare Sistemi Operativi", "", new Date(), new ArrayList<ObiettivoDemo>()));
        
        ArrayList<ObiettivoDemo> sottoObiettivi2 = new ArrayList<ObiettivoDemo>();
        sottoObiettivi2.add(new ObiettivoDemo("Creare il prodotto", "", new Date(), new ArrayList<ObiettivoDemo>()));
        sottoObiettivi2.add(new ObiettivoDemo("Publicizzare e vendere il prodotto", "", new Date(), new ArrayList<ObiettivoDemo>()));
        
        this.listaObiettivi.add(new ObiettivoDemo("Dare tutti gli esami dell'università", "", new Date(), sottoObiettivi));
        this.listaObiettivi.add(new ObiettivoDemo("Dare una maratona", "", new Date(), new ArrayList<ObiettivoDemo>()));
        this.listaObiettivi.add(new ObiettivoDemo("Creare una startup", "", new Date(), sottoObiettivi2));
        
        // <---- DEMO
        
        this.listaBoxSottoObiettivi = new ArrayList<VBox>();
        this.visualizzaObiettivi();
    }
    
    private void visualizzaObiettivi() {
        this.boxObiettivi.getChildren().clear();
        for(int i = 0; i < this.listaObiettivi.size(); i++) 
            this.visualizzaObiettivo(this.listaObiettivi.get(i), i);
    }
    
    private void toggleSottoObiettivi(IObiettivo obiettivo, int indice, VBox container) {
        if(!this.listaSottoObiettiviAperta) {
            if(obiettivo.getSottoObiettivi().size() > 0) {
                container.setMaxWidth(this.boxObiettivi.getBoundsInParent().getWidth() - 60);
                for(int i = 0; i < obiettivo.getSottoObiettivi().size(); i++) {
                    // Crea il sotto-obiettivo nella view, con larghezza minore.
                    BorderPane pane = this.creaViewSottoObiettivo(obiettivo.getSottoObiettivi().get(i));
                    container.getChildren().add(pane);
                }
                this.listaSottoObiettiviAperta = true;
            }
        } else {
            container.getChildren().clear();
            this.listaSottoObiettiviAperta = false;
        }
    }
    
    private void visualizzaObiettivo(IObiettivo obiettivo, int indice) {
        BorderPane pane = new BorderPane();
        pane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        pane.getStyleClass().add("obiettivo");
        
        VBox vBox = new VBox();
        
        CheckBox check = new CheckBox();
        check.setText(obiettivo.getNome());
        check.getStyleClass().add("nome");
        check.setMaxWidth(300);
        check.setWrapText(true);
        
        Label label = new Label("Ago, 07 2021");
        label.getStyleClass().add("data");

        HBox hBox = new HBox();
        ImageView plus = new ImageView();
        plus.setFitHeight(15);
        plus.setFitWidth(15);
        plus.setImage(new Image(getClass().getResource("/main/risorse/plus.png").toString()));
        Label label2 = new Label("Sotto-obiettivo");
        hBox.getChildren().add(plus);
        hBox.getChildren().add(label2);
        hBox.getStyleClass().add("sottoBtn");
        
        // Event handler per aggiunta di un sotto-obiettivo.
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                try {
                    aggiungiSottoObiettivo(obiettivo);
                } catch (IOException ex) {
                    Logger.getLogger(GoalManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        
        vBox.getChildren().add(check);
        vBox.getChildren().add(label);
        
        vBox.getChildren().add(hBox);
        pane.setLeft(vBox);
        
        this.boxObiettivi.getChildren().add(pane);
        
        HBox hBox3 = new HBox();
        
        if(obiettivo.getSottoObiettivi().size() > 0) {
            VBox imgContainer = new VBox();
            imgContainer.setAlignment(Pos.CENTER);
            ImageView arrow = new ImageView();
            arrow.setFitHeight(15);
            arrow.setFitWidth(15);
            arrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down.png").toString()));
            imgContainer.getChildren().add(arrow);
             
            HBox hBox4 = new HBox();
            Label label3 = new Label("" + obiettivo.getSottoObiettivi().size());
            label3.getStyleClass().add("num-sottoobiettivi");
            hBox4.getChildren().add(label3);
            hBox4.getChildren().add(imgContainer);
            hBox4.setPadding(new Insets(5, 5, 5, 5));
            hBox4.setAlignment(Pos.CENTER);
            
            hBox3.getChildren().add(hBox4);
            
            pane.setRight(hBox3);
            
            // Crea un container sotto al obiettivo da popolare con gli eventuali sotto-obiettivi.
            VBox vBox2 = new VBox();
            vBox2.setAlignment(Pos.TOP_LEFT);
            vBox2.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            this.listaBoxSottoObiettivi.add(vBox2);
            
            // Event handler per apertura sotto obiettivi.
            EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    toggleSottoObiettivi(obiettivo, indice, vBox2);
                }
            };
            imgContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2);
            
            this.boxObiettivi.getChildren().add(vBox2);
        }

        VBox imgContainer2 = new VBox();
        imgContainer2.setPadding(new Insets(5, 5, 5, 15));
        imgContainer2.setAlignment(Pos.CENTER);
        ImageView dots = new ImageView();
        dots.setFitHeight(18);
        dots.setFitWidth(6);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        imgContainer2.getChildren().add(dots);
        
        // Event handler per modifica obiettivo.
        EventHandler<MouseEvent> eventHandler3 = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent t) {
                try {
                    modificaObiettivo(obiettivo);
                } catch (IOException ex) {
                    Logger.getLogger(GoalManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        imgContainer2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler3);
        
        VBox imgContainer3 = new VBox();
        imgContainer3.setPadding(new Insets(5, 5, 5, 15));
        imgContainer3.setAlignment(Pos.CENTER);
        ImageView trash = new ImageView();
        trash.setFitHeight(18);
        trash.setFitWidth(18);
        trash.setImage(new Image(getClass().getResource("/main/risorse/trash.png").toString()));
        imgContainer3.getChildren().add(trash);
        
        // Event handler per eliminazione obiettivo.
        EventHandler<MouseEvent> eventHandler4 = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent t) {
                eliminaObiettivo(obiettivo);
            }
        };
        imgContainer3.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler4);
        
        hBox3.getChildren().add(imgContainer2);
        hBox3.getChildren().add(imgContainer3);
        hBox3.setAlignment(Pos.CENTER);
    }
    
    private BorderPane creaViewSottoObiettivo(IObiettivo obiettivo) {
        BorderPane pane = new BorderPane();
        pane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        pane.getStyleClass().add("obiettivo");
        
        VBox vBox = new VBox();
        
        CheckBox check = new CheckBox();
        check.setText(obiettivo.getNome());
        check.getStyleClass().add("nome");
        
        Label label = new Label("Ago, 07 2021");
        label.getStyleClass().add("data");
        

        HBox hBox = new HBox();
        
        ImageView plus = new ImageView();
        plus.setFitHeight(15);
        plus.setFitWidth(15);
        plus.setImage(new Image(getClass().getResource("/main/risorse/plus.png").toString()));
        Label label2 = new Label("Sotto-obiettivo");
        hBox.getChildren().add(plus);
        hBox.getChildren().add(label2);
        hBox.getStyleClass().add("sottoBtn");
        
        vBox.getChildren().add(check);
        vBox.getChildren().add(label);
        
        VBox imgContainer = new VBox();
        imgContainer.setAlignment(Pos.CENTER);
        ImageView arrow = new ImageView();
        arrow.setFitHeight(15);
        arrow.setFitWidth(15);
        arrow.setImage(new Image(getClass().getResource("/main/risorse/arrow-down.png").toString()));
        imgContainer.getChildren().add(arrow);
  
        pane.setLeft(vBox);
        
        return pane;
    }
    
    @FXML
    private void aggiungiObiettivo() throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/GoalManager/EditorObiettivi.fxml"));
        DialogPane pane = fxmlLoader.load();
        pane.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle("Nuovo obiettivo");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorObiettiviController controller = fxmlLoader.getController();
        controller.setObiettivo(new ObiettivoDemo("", "", new Date(), new ArrayList<ObiettivoDemo>()));
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            // Aggiungere l'obiettivo alla listaObiettivi
            this.listaObiettivi.add(controller.getObiettivo());
            
            // Aggiornare la view
            this.visualizzaObiettivi();
            
        }
    }
    
    @FXML
    private void modificaObiettivo(IObiettivo obiettivo) throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/GoalManager/EditorObiettivi.fxml"));
        DialogPane pane = fxmlLoader.load();
        pane.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle("Modifica obiettivo");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorObiettiviController controller = fxmlLoader.getController();
        controller.setObiettivo(obiettivo);
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            
            
            System.out.println(controller.getObiettivo().getNome());
            System.out.println(controller.getObiettivo().getDescrizione());
            System.out.println(((ObiettivoMisurabileDemo) controller.getObiettivo()).getUnità());
            System.out.println(((ObiettivoMisurabileDemo) controller.getObiettivo()).getValore());
            System.out.println(controller.getObiettivo().getNome());
            
            // Aggiornare la view
            this.visualizzaObiettivi();
            
        }
    }
    
    private void eliminaObiettivo(IObiettivo obiettivo) {
        System.out.println("Obiettivo: " + obiettivo.getNome() + " eliminato.");
    }
    
    @FXML
    private void aggiungiSottoObiettivo(IObiettivo obiettivo) throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/GoalManager/EditorObiettivi.fxml"));
        DialogPane pane = fxmlLoader.load();
        pane.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle("Nuovo sotto-obiettivo");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorObiettiviController controller = fxmlLoader.getController();
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            System.out.println("Sotto-biettivo aggiunto");
            
        }
    }
    
}
