package main.Controllers.HabitTracker;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import main.Controllers.Helper;
import main.Controllers.Modal;
import main.Models.habittracker.classes.SessionHabit;
import main.Models.habittracker.classes.SimpleHabit;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.habittracker.interfaces.ISessionHabit;

public class HabitTrackerController {
    
    @FXML
    private VBox abitudiniBox;
    @FXML
    private VBox infoBox;
    @FXML
    private Label giornaliereBtn;
    @FXML
    private Label tutteBtn;
    
    private IHabit abitudineCliccata = null;
    
    private BorderPane paneAbitudineCliccata = null;
    
    private boolean giornaliereSelected = true;
    
    /*
     * Istanza dell'habit tracker.
     */
    private IHabitTracker ht;
    
    public void setHabitTracker(IHabitTracker ht) {
    	this.ht = ht;
    	visualizzaAbitudiniGiornaliere();
        Modal modal = new Modal(Main.class.getResource("/main/Views/Modal.fxml"));
        try {
			modal.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void visualizzaAbitudiniGiornaliere() {
        abitudiniBox.getChildren().clear();
        List<IHabit> habits = ht.calculateTodayHabits(LocalDate.now());
        if(habits.size() > 0) {
            // crea container abitudini non completate
            Label label1 = new Label("Da completare");
            label1.getStyleClass().add("daily-label");
            VBox vBox1 = new VBox();
            
            // crea container abitudini completate
            Label label2 = new Label("Completate");
            label2.getStyleClass().add("daily-label");
            VBox vBox2 = new VBox();
            
            // aggiunge le abitudini ai rispettivi container
            for(IHabit h : habits) {
            	BorderPane pane = this.creaViewAbitudine(h, true);
            	if(!h.isCompleted()) {
            		vBox1.getChildren().add(pane);
            	} else {
            		vBox2.getChildren().add(pane);
            	}
            }
           
            // aggiunge i container alla view
            abitudiniBox.getChildren().addAll(label1, vBox1, label2, vBox2);
        } else {
        	VBox vBox = new VBox();
        	vBox.setAlignment(Pos.CENTER);
        	Label label1 = new Label("Non ci sono abitudini da completare oggi.");
        	vBox.getChildren().add(label1);
        	label1.getStyleClass().add("no-habits-label");
        	abitudiniBox.getChildren().add(vBox);
        }
    }
    
    private void visualizzaTotaleAbitudini() {
        abitudiniBox.getChildren().clear();
        List<IHabit> habits = ht.getHabits();
        
        // crea container abitudini
        VBox vBox1 = new VBox();
        
        // aggiunge le abitudini al container
        for(IHabit h : habits) {
        	BorderPane pane = this.creaViewAbitudine(h, false);
        	vBox1.getChildren().add(pane);
        }
        
        abitudiniBox.getChildren().add(vBox1);
    }
    
    private void aggiornaView() {
    	if(giornaliereSelected) {
    		visualizzaAbitudiniGiornaliere();
    	} else {
    		visualizzaTotaleAbitudini();
    	}
    }
    
    private BorderPane creaViewAbitudine(IHabit abitudine, boolean isDaily) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 0, 10, 0));
        pane.getStyleClass().add("abitudine");
        
        // collega evento apertura informazioni
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	if(paneAbitudineCliccata != null) {
            		paneAbitudineCliccata.setStyle("-fx-background-color: #0E1726;");
            	}
            	pane.setStyle("-fx-background-color: #374856; -fx-background-radius: 10; -fx-border-radius: 10; -fx-cursor: hand;");
            	paneAbitudineCliccata = pane;
            	abitudineCliccata = abitudine;
            	visualizzaInfoAbitudine(abitudine);
            }
        }); 
        
        if(!isDaily) {
        	// crea l'elemento della lista
            pane.setLeft(Helper.creaElementoLista(abitudine.getName()));
        } else {
        	
        	// crea la checkbox
            CheckBox check = new CheckBox();
            check.setText(abitudine.getName());
            check.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14;");
            check.setSelected(false);
            if(abitudine.isCompleted()) {
                check.setSelected(true);
            } 
            
            // collega evento completamento abitudine
            check.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                	t.consume();
                	abitudine.complete();
                    aggiornaView();
                }
            }); 
            pane.setLeft(check);
        }
        
        // crea pulsante apertura menù abitudine
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        HBox editBtn = Helper.creaBtnEdit(); 
        hBox.getChildren().addAll(editBtn);
     
        // crea il menu dropdown
        ContextMenu menu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Modifica");
        MenuItem menuItem2 = new MenuItem("Elimina");
        menuItem1.setOnAction((ActionEvent e) -> {
        	e.consume();
        	try {
        		apriEditorAbitudine(abitudine, false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        menuItem2.setOnAction((ActionEvent e) -> {
        	eliminaAbitudine(abitudine);
        });
        menu.getItems().addAll(menuItem1, menuItem2);
        
        // Evento per il click del menù attività
        editBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	t.consume();
            	menu.show(editBtn, t.getScreenX(), t.getScreenY());
            }
        }); 
    
        pane.setRight(hBox);
        return pane;
    }
    
    private String getColore(int conteggio, int record) {
        String colore = "#545454";
        if(conteggio == 0) {
        	colore = "#545454";
        } else if(conteggio < record - 6) {
        	colore = "#FF4560";
        } else if(conteggio < record - 3) {
        	colore = "#FEB019";
        } else if(conteggio < record) {
        	colore = "#058D5E";
        } else if(conteggio >= record) {
        	colore = "#00E396";
        }
        return colore;
    }
    
    private void visualizzaInfoAbitudine(IHabit abitudine) {
        this.infoBox.getChildren().clear();
        
    	// calcola la percentuale di completamento
        String colore = getColore(abitudine.getCount(), abitudine.getRecord());
        
        // visualizza nome abitudine
        Label label = new Label(abitudine.getName());
        label.setPadding(new Insets(0, 0, 20, 0));
        label.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 22;");
        
        // visualizza descrizione
        Label label2 = new Label("Descrizione:");
        label2.setPadding(new Insets(0, 0, 10, 0));
        label2.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16; -fx-font-weight: 800;");
        Label label3 = new Label(abitudine.getDescription());
        label3.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 16;");
        label3.setPadding(new Insets(0, 0, 20, 0));
        label3.setMaxWidth(500);
        label3.setWrapText(true);
        
        // visualizza serie attuale
        Label label4 = new Label("Serie attuale:");
        label4.setPadding(new Insets(0, 0, 20, 0));
        label4.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16; -fx-font-weight: 800;");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Label label5 = new Label("Come stai andando fino ad ora.");
        label5.setPadding(new Insets(0, 0, 10, 0));
        label5.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 14;");
        Label label6 = new Label(Integer.toString(abitudine.getCount()));
        label6.setStyle("-fx-background-color:" + colore + "; -fx-text-fill: #ffffff; -fx-border-radius: 50%; -fx-background-radius: 50%; -fx-pref-width: 100; -fx-pref-height: 100; -fx-font-size: 30; -fx-font-weight: 800;");
        label6.alignmentProperty().set(Pos.CENTER);
        vBox.getChildren().add(label5);
        vBox.getChildren().add(label6);
        hBox.getChildren().add(vBox);
        
        // visualizza serie record
        Label label9 = new Label("Serie Record:");
        label9.setPadding(new Insets(20, 0, 20, 0));
        label9.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16; -fx-font-weight: 800;");
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        Label label7 = new Label("Il tuo record personale.");
        label7.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 14;");
        label7.setPadding(new Insets(0, 0, 10, 0));
        Label label8 = new Label(Integer.toString(abitudine.getRecord()));
        label8.alignmentProperty().set(Pos.CENTER);
        label8.setStyle("-fx-background-color: " + colore + "; -fx-text-fill: #ffffff; -fx-border-radius: 50%; -fx-background-radius: 50%; -fx-pref-width: 100; -fx-pref-height: 100; -fx-font-size: 30; -fx-font-weight: 800;");
        vBox2.getChildren().add(label7);
        vBox2.getChildren().add(label8);
        hBox2.getChildren().add(vBox2);
        
        // aggiunge elementi alla view
        this.infoBox.getChildren().add(label);
        this.infoBox.getChildren().add(label2);
        this.infoBox.getChildren().add(label3);
        this.infoBox.getChildren().add(label4);
        this.infoBox.getChildren().add(hBox);
        this.infoBox.getChildren().add(label9);
        this.infoBox.getChildren().add(hBox2);
    }
    
    private void apriEditorAbitudine(IHabit abitudine, boolean isNew) throws IOException {
        
        // carica il dialog
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/main/Views/HabitTracker/EditorAbitudine.fxml"));
        DialogPane pane = fxmlLoader.load();
        pane.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);
        
        // Ottiene il controller e imposta l'abitudine
        EditorAbitudineController controller = fxmlLoader.getController();
        
        // imposta il titolo e l'abitudine
        if(isNew) {
        	dialog.setTitle("Nuova abitudine");
        } else {
        	dialog.setTitle("Modifica abitudine");
        	controller.setAbitudine(abitudine);
        }
        
        // validazioen  degli input
    	final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
    	btOk.addEventFilter(ActionEvent.ACTION, event -> {
    		if(controller.getNome().isBlank() 	||
    		   controller.getData() == null) {
    			event.consume();
    			if(controller.getNome().isBlank()) {
    				throw new IllegalStateException("Perfavore, inserisci un nome per l'obiettivo");
    			}else if(controller.getData() == null) {
    				throw new IllegalStateException("Perfavore, scegli una data di raggiungimento per l'obiettivo");
    			}
        	}
    	});
        
        // Apre dialog popup e attende
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        // se l'utente clicca OK.
        if(clickedButton.get() == ButtonType.OK) {
        	String nome = controller.getNome();
        	String descrizione = controller.getDescrizione();
        	LocalDate data = controller.getData();
        	List<DayOfWeek> giorni = controller.getGiorni();
        	boolean isSessione = controller.isSessione();
        	int durata = controller.getDurata();
        	if(isNew) {
        		// aggiunge abitudine 
        		IHabit newHabit;
        		if(isSessione) {
        			newHabit = new SessionHabit(nome, descrizione, data, giorni, durata);
        		} else {
        			newHabit = new SimpleHabit(nome, descrizione, data, giorni);
        		}
        		ht.addHabit(newHabit);
        		this.visualizzaInfoAbitudine(newHabit);
        	} else {
        		// modifica abitudine
        		abitudine.setName(nome);
        		abitudine.setDescription(descrizione);
        		abitudine.setStartDate(data);
        		abitudine.setDays(giorni);
        		if(isSessione) {
        			((ISessionHabit) abitudine).setDuration(durata);
        		}
        		this.visualizzaInfoAbitudine(abitudine);
        	} 
            aggiornaView();
        }
    }
    
    private void eliminaAbitudine(IHabit abitudine) {
        ht.removeHabit(abitudine.getId());
        aggiornaView();
    }
    
    private void evidenziaPulsante(Label label) {

        // Rimuove evidenziazione da tutti i label
        this.tutteBtn.setStyle("-fx-text-fill: #ffffff;-fx-cursor:hand;");
        this.giornaliereBtn.setStyle("-fx-text-fill: #ffffff;-fx-cursor:hand;");
        
        // Aggiunge evidenziazione al pulsante scelto
        label.setStyle("-fx-text-fill: #2196F3;-fx-cursor:hand;");
        
    }
    
    @FXML
    private void cambiaPagina() {
    	if(giornaliereSelected) {
        	giornaliereSelected = false;
            evidenziaPulsante(tutteBtn);
            aggiornaView();
    	} else {
    		giornaliereSelected = true;
            evidenziaPulsante(giornaliereBtn);
            aggiornaView();
    	}
    }
    
    @FXML
    public void aggiungiAbitudine() throws IOException {
    	this.apriEditorAbitudine(null, true);
    }
    
}
