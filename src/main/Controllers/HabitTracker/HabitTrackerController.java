/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.HabitTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author andre
 */
public class HabitTrackerController {
    
    @FXML
    private VBox abitudiniBox;
    
    @FXML
    private VBox infoBox;
    
    @FXML
    private Label giornaliereBtn;
    
    @FXML
    private Label completateBtn;
    
    @FXML
    private Label tutteBtn;
    
    // DEMO
    private ArrayList<AbitudineDemo> listaAbitudini;
    
    private ArrayList<AbitudineDemo> listaAbitudiniGiornaliere;
    
    private ArrayList<AbitudineDemo> listaAbitudiniCompletate;
    
    private String pulsanteAttivo;
    
    @FXML
    private void initialize() {
        
        // <---- DEMO
        
        this.listaAbitudiniGiornaliere = new ArrayList<AbitudineDemo>();
        this.listaAbitudiniCompletate = new ArrayList<AbitudineDemo>();
        
        ArrayList<String> giorniRipetizione = new ArrayList<String>();
        giorniRipetizione.add("LUN");
        giorniRipetizione.add("MAR");
        
        ArrayList<ItemDemo> items = new ArrayList<ItemDemo>();
        
        ArrayList<ItemDemo> items2 = new ArrayList<ItemDemo>();
        items.add(new ItemDemo("20 trazioni"));
        items.add(new ItemDemo("20 piegamenti"));
        
        AbitudineDemo abitudine1 = new AbitudineDemo("Fare doccia fredda", "Lorem ipsum dolor sit amet, consectetur adipiscing "
                + "elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
                + " exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit "
                + "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum.", giorniRipetizione, items);
        
        AbitudineDemo abitudine2 = new AbitudineDemo("Fare allenamento muscolare", "Lorem ipsum dolor sit amet, consectetur adipiscing "
                + "elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
                + "sunt in culpa qui officia deserunt mollit anim id est laborum.", giorniRipetizione, items2);
        
        AbitudineDemo abitudine3 = new AbitudineDemo("Bere 2 litri di acqua", "Lorem ipsum dolor sit amet, consectetur adipiscing "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum.", giorniRipetizione, items);
        
        this.listaAbitudini = new ArrayList<AbitudineDemo>();
        this.listaAbitudini.add(abitudine1);
        this.listaAbitudini.add(abitudine2);
        this.listaAbitudini.add(abitudine3);
        
        this.listaAbitudiniGiornaliere.add(abitudine1);
        this.listaAbitudiniGiornaliere.add(abitudine2);
        this.listaAbitudiniGiornaliere.add(abitudine3);
        
        //----> DEMO
        
        this.pulsanteAttivo = "GIORNALIERE";
        this.visualizzaAbitudiniGiornaliere();
    }
    
    private void visualizzaAbitudini() {
        this.abitudiniBox.getChildren().clear();
        if(this.listaAbitudini.size() > 0) {    
            for(int i = 0; i < this.listaAbitudini.size(); i++) {
                this.visualizzaAbitudine(this.listaAbitudini.get(i), "TUTTE");
            }
        }

    }
    
    private void visualizzaAbitudiniGiornaliere() {
        this.abitudiniBox.getChildren().clear();
        if(this.listaAbitudiniGiornaliere.size() > 0) {
            for(int i = 0; i < this.listaAbitudiniGiornaliere.size(); i++) {
                this.visualizzaAbitudine(this.listaAbitudiniGiornaliere.get(i), "NON COMPLETATA");
            }
        }
    }
    
    private void visualizzaAbitudiniCompletate() {
        this.abitudiniBox.getChildren().clear();
        if(this.listaAbitudiniCompletate.size() > 0) {
            for(int i = 0; i < this.listaAbitudiniCompletate.size(); i++) {
                this.visualizzaAbitudine(this.listaAbitudiniCompletate.get(i), "COMPLETATA");
            }
        }
    }
    
    private void gestisciCheckAbitudine(AbitudineDemo abitudine, CheckBox check) {
        if(check.isSelected()) {
            
            // elimina da lista abitudini giornaliere
            for(int i = 0; i < this.listaAbitudiniGiornaliere.size(); i++) {
                if(abitudine.getId() == this.listaAbitudiniGiornaliere.get(i).getId())
                    this.listaAbitudiniGiornaliere.remove(i);
            }
            
            // aggiunge a lista abitudini completate
            this.listaAbitudiniCompletate.add(abitudine);
            
             // Aggiorna la view
             this.visualizzaAbitudiniGiornaliere();
             
        } else {
            
            // elimina da lista abitudini completate
            for(int i = 0; i < this.listaAbitudiniCompletate.size(); i++) {
                if(abitudine.getId() == this.listaAbitudiniCompletate.get(i).getId())
                    this.listaAbitudiniCompletate.remove(i);
            }
            
            // Aggiunge a lista abitudini giornaliere
            this.listaAbitudiniGiornaliere.add(abitudine);
            
            // Aggiorna la view
             this.visualizzaAbitudiniCompletate();
            
        }
        
        
    } 
    
    @FXML
    private void visualizzaAbitudine(AbitudineDemo abitudine, String tipo) {
        System.out.println(abitudine.getNome());
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 0, 10, 0));
        
        if(tipo == "TUTTE") {
            Label label10 = new Label(abitudine.getNome());
            label10.setStyle("-fx-text-fill: #ffffff");
            pane.setLeft(label10);
        } else {
            CheckBox check = new CheckBox();
            check.setText(abitudine.getNome());
            check.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14;");
            check.setSelected(false);
            
            if(tipo == "COMPLETATA") {
                check.setSelected(true);
            } 
            
            // Evento per gestire il click di un'abitudine.
            EventHandler<ActionEvent> eventHandler4 = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    gestisciCheckAbitudine(abitudine, check);

                }
            };
            check.addEventHandler(ActionEvent.ACTION, eventHandler4); 
            pane.setLeft(check);
        }
        
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        
        Label label = new Label("INFO");
        label.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14;");
        
        // Evento per aggiornare la sezione info abitudine
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                visualizzaInfoAbitudine(abitudine);
            }
        };
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);   
        
        HBox imgContainer = new HBox();
        imgContainer.setAlignment(Pos.CENTER);
        ImageView dots = new ImageView();
        dots.setFitHeight(22);
        dots.setFitWidth(8);
        dots.setImage(new Image(getClass().getResource("/main/risorse/dots.png").toString()));
        imgContainer.getChildren().add(dots);
        imgContainer.setPadding(new Insets(5, 5, 5, 5));
        imgContainer.setPadding(new Insets(0, 20, 0, 20));   
     
        // Evento per modificare un'abitudine.
        EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    modificaAbitudine(abitudine);
                } catch (IOException ex) {
                    Logger.getLogger(HabitTrackerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        imgContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler2); 
        
        HBox imgContainer2 = new HBox();
        imgContainer2.setAlignment(Pos.CENTER);
        ImageView trash = new ImageView();
        trash.setFitHeight(15);
        trash.setFitWidth(15);
        trash.setImage(new Image(getClass().getResource("/main/risorse/trash.png").toString()));
        imgContainer2.getChildren().add(trash);
        imgContainer2.setPadding(new Insets(5, 5, 5, 5));
        imgContainer2.setPadding(new Insets(0, 0, 0, 20));  

        // Evento per eliminare un'abitudine.
        EventHandler<MouseEvent> eventHandler3 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                eliminaAbitudine(abitudine);
 
            }
        };
        imgContainer2.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler3); 
        
        hBox.getChildren().add(label);
        hBox.getChildren().add(imgContainer2);
        hBox.getChildren().add(imgContainer);
       
        pane.setRight(hBox);
        
        this.abitudiniBox.getChildren().add(pane);
    }
    
    @FXML
    private void visualizzaInfoAbitudine(AbitudineDemo abitudine) {
        this.infoBox.getChildren().clear();
        
        Label label = new Label(abitudine.getNome());
        label.setPadding(new Insets(0, 0, 20, 0));
        label.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 18;");
        Label label2 = new Label("Descrizione:");
        label2.setPadding(new Insets(0, 0, 10, 0));
        label2.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: 800;");
        Label label3 = new Label(abitudine.getDescrizione());
        label3.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 14;");
        label3.setPadding(new Insets(0, 0, 20, 0));
        label3.setMaxWidth(500);
        label3.setWrapText(true);
        Label label4 = new Label("Serie attuale:");
        label4.setPadding(new Insets(0, 0, 20, 0));
        label4.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: 800;");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Label label5 = new Label("Come stai andando fino ad ora.");
        label5.setPadding(new Insets(0, 0, 10, 0));
        label5.setStyle("-fx-text-fill: #545454; -fx-font-size: 14;");
        Label label6 = new Label(Integer.toString(abitudine.getSerieAttuale()));
        label6.setStyle("-fx-background-color: #0E1726; -fx-text-fill: #ffffff; -fx-border-radius: 50%; -fx-background-radius: 50%; -fx-pref-width: 100; -fx-pref-height: 100; -fx-font-size: 25; -fx-font-weight: 800;");
        label6.alignmentProperty().set(Pos.CENTER);
        vBox.getChildren().add(label5);
        vBox.getChildren().add(label6);
        hBox.getChildren().add(vBox);
        
        Label label9 = new Label("Serie Record:");
        label9.setPadding(new Insets(20, 0, 20, 0));
        label9.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: 800;");
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        Label label7 = new Label("Il tuo record personale.");
        label7.setStyle("-fx-text-fill: #545454; -fx-font-size: 14;");
        label7.setPadding(new Insets(0, 0, 10, 0));
        Label label8 = new Label(Integer.toString(abitudine.getSerieRecord()));
        label8.alignmentProperty().set(Pos.CENTER);
        label8.setStyle("-fx-background-color: #0E1726; -fx-text-fill: #ffffff; -fx-border-radius: 50%; -fx-background-radius: 50%; -fx-pref-width: 100; -fx-pref-height: 100; -fx-font-size: 25; -fx-font-weight: 800;");
        vBox2.getChildren().add(label7);
        vBox2.getChildren().add(label8);
        hBox2.getChildren().add(vBox2);
        
        this.infoBox.getChildren().add(label);
        this.infoBox.getChildren().add(label2);
        this.infoBox.getChildren().add(label3);
        this.infoBox.getChildren().add(label4);
        this.infoBox.getChildren().add(hBox);
        this.infoBox.getChildren().add(label9);
        this.infoBox.getChildren().add(hBox2);
    }
    
    @FXML
    private void aggiungiAbitudine() throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/HabitTracker/EditorAbitudine.fxml"));
        DialogPane pane = fxmlLoader.load();
        pane.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle("Nuova abitudine");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorAbitudineController controller = fxmlLoader.getController();
        controller.setAbitudine(new AbitudineDemo("", "", new ArrayList<String>(), new ArrayList<ItemDemo>()));
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            // Aggiunge abitudine al modello
            this.listaAbitudini.add(controller.getAbitudine());
            
            this.abitudiniBox.getChildren().clear();
            // Aggiorna la view
            if(this.pulsanteAttivo == "GIORNALIERE")
                this.visualizzaAbitudiniGiornaliere();
            else if(this.pulsanteAttivo == "TUTTE")
                this.visualizzaAbitudini();
            
            this.visualizzaInfoAbitudine(controller.getAbitudine());
            
        }
    }
    
    @FXML
    private void modificaAbitudine(AbitudineDemo abitudine) throws IOException {
        
        // Carica il file fxml e crea un nuovo popup Dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/HabitTracker/EditorAbitudine.fxml"));
        DialogPane pane = fxmlLoader.load();
        pane.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        dialog.setTitle("Modifica abitudine");
        
        // Ottiene il controller EditorProgettoController associato alla view
        EditorAbitudineController controller = fxmlLoader.getController();
        controller.setAbitudine(abitudine);
        
        // Apre dialog popup
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // Se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
            
            // Aggiorna la view
            if(this.pulsanteAttivo == "GIORNALIERE")
                this.visualizzaAbitudiniGiornaliere();
            else if(this.pulsanteAttivo == "TUTTE")
                this.visualizzaAbitudini();
            
            this.visualizzaInfoAbitudine(abitudine);
        }
    }
    
    private void eliminaAbitudine(AbitudineDemo abitudine) {
        System.out.println("Abitudine " + abitudine.getNome() + " eliminata.");
    }
    
    @FXML
    private void evidenziaPulsante(Label label, String nomeBtn) {
        this.pulsanteAttivo = nomeBtn;
        
        // Rimuove evidenziazione da tutti i label
        this.tutteBtn.setStyle("-fx-text-fill: #ffffff;");
        this.giornaliereBtn.setStyle("-fx-text-fill: #ffffff;");
        this.completateBtn.setStyle("-fx-text-fill: #ffffff;");
        
        // Aggiunge evidenziazione al pulsante scelto
        label.setStyle("-fx-text-fill: #2196F3;");
        
    }
    
    @FXML
    private void apriGiornaliere() {
        this.evidenziaPulsante(this.giornaliereBtn, "GIORNALIERE");
        this.visualizzaAbitudiniGiornaliere();
    }
    
    @FXML
    private void apriTutte() {
        this.evidenziaPulsante(this.tutteBtn, "TUTTE");
        this.visualizzaAbitudini();
    }
    
    @FXML
    private void apriCompletate() {
        this.evidenziaPulsante(this.completateBtn, "COMPLETATE");
        this.visualizzaAbitudiniCompletate();
    }
    
}
