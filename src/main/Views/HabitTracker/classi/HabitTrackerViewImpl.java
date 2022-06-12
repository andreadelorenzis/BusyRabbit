package main.views.habittracker.classi;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import main.Controller.HabitTracker.HabitTrackerController;
import main.Controller.HabitTracker.HabitTrackerControllerImpl;
import main.Controller.Helpers.Helper;
import main.model.habittracker.classi.AbitudineScomponibile;
import main.model.habittracker.classi.AbitudineSessione;
import main.model.habittracker.classi.AbitudineTracker;
import main.model.habittracker.interfacce.IAbitudine;
import main.views.LoaderRisorse;
import main.views.HabitTracker.interfaces.HabitTrackerView;
import main.views.Modals.Modal;
import main.views.Notifications.Notification;
import main.views.Notifications.NotificationType;

public class HabitTrackerViewImpl implements HabitTrackerView {

    @FXML
    private VBox abitudiniBox;
    @FXML
    private VBox infoBox;
    @FXML
    private Label giornaliereBtn;
    @FXML
    private Label tutteBtn;
    
    private IAbitudine abitudineCliccata = null;
    
    private BorderPane paneAbitudineCliccata = null;
    
    private boolean giornaliereSelected = true;
    
    private HabitTrackerController controller;
  
    @FXML
    private void initialize() {
    	controller = new HabitTrackerControllerImpl(this);
    	visualizzaAbitudiniGiornaliere();
    }
    
    private void visualizzaAbitudiniGiornaliere() {
        abitudiniBox.getChildren().clear();
        List<IAbitudine> habits = AbitudineTracker.getInstance().calculateTodayHabits(LocalDate.now());
        if(habits.size() > 0) {
            // crea container abitudini non completate
            Label label1 = new Label("Da fare");
            label1.getStyleClass().add("daily-label");
            VBox vBox1 = new VBox();
            
            // crea container abitudini completate
            Label label2 = new Label("Completate");
            label2.getStyleClass().add("daily-label");
            label2.setStyle("-fx-padding: 60 0 0 0;");
            VBox vBox2 = new VBox();
            
            // aggiunge le abitudini ai rispettivi container
            for(IAbitudine h : habits) {
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
        List<IAbitudine> habits = AbitudineTracker.getInstance().getHabits();
        
        // crea container abitudini
        VBox vBox1 = new VBox();
        
        // aggiunge le abitudini al container
        for(IAbitudine h : habits) {
        	BorderPane pane = this.creaViewAbitudine(h, false);
        	vBox1.getChildren().add(pane);
        }
        
        abitudiniBox.getChildren().add(vBox1);
    }
    
    public void aggiornaView() {
    	if(giornaliereSelected) {
    		visualizzaAbitudiniGiornaliere();
    	} else {
    		visualizzaTotaleAbitudini();
    	}
    }
    
    private BorderPane creaViewAbitudine(IAbitudine abitudine, boolean isDaily) {
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
                	controller.completaAbitudine(abitudine);
                	if(abitudine.isCompleted()) {
                		new Notification("Abitudine completata.", NotificationType.SUCCESS).show();
                	}
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
        	controller.eliminaAbitudine(abitudine);
        	new Notification("Abitudine eliminata.", NotificationType.INFO).show();
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
    
    private void visualizzaInfoAbitudine(IAbitudine abitudine) {
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
    
    private void apriEditorAbitudine(IAbitudine abitudine, boolean nuovo) throws IOException {
        
        // crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.HT, "EditorAbitudine"));
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.HT, "HabitTracker"));
        editor.getStylesheets().add(LoaderRisorse.globalCss);  
    	Modal modal = new Modal(editor, "");
    	EditorAbitudineViewImpl controller = fxmlLoader.getController();
        
        // imposta il titolo del dialog
        if(nuovo) {
        	modal.setTitolo("Nuova abitudine");
        } else {
        	modal.setTitolo("Modifica abitudine");
        }
        
        // validazioen  degli input
        final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
    	btnOk.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
    		if(controller.getNome().isBlank() 	||
    		   controller.getData() == null) {
    			event.consume();
    			if(controller.getNome().isBlank()) {
    				new Notification("Perfavore, inserisci un nome per l'abitudine", NotificationType.ERROR).show();
    			}else if(controller.getData() == null) {
    				new Notification("Perfavore, la data di partenza dell'abitudine", NotificationType.ERROR).show();
    			}
        	}
    	});
        
    	// apre il dialog e attende
        ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	String descrizione = controller.getDescrizione();
        	LocalDate data = controller.getData();
        	List<DayOfWeek> giorni = controller.getGiorni();
        	boolean isSessione = controller.isSessione();
        	int durata = controller.getDurata();
        	if(nuovo) {
        		// aggiunge abitudine 
        		IAbitudine newHabit;
        		if(isSessione) {
        			newHabit = new AbitudineSessione(nome, descrizione, data, giorni, durata);
        		} else {
        			newHabit = new AbitudineScomponibile(nome, descrizione, data, giorni);
        		}
        		this.controller.aggiungiAbitudine(newHabit);
        		new Notification("Abitudine aggiunta.", NotificationType.SUCCESS).show();
        		this.visualizzaInfoAbitudine(newHabit);
        	} else {
        		// modifica abitudine
        		IAbitudine modificata = null;
        		if(abitudine instanceof AbitudineScomponibile) {
        			modificata = new AbitudineScomponibile(nome, descrizione, data, giorni);
        		} else if(abitudine instanceof AbitudineSessione) {
        			modificata = new AbitudineSessione(nome, descrizione, data, giorni, durata);
        		}
        		this.controller.modificaAbitudine(abitudine, modificata);
        		new Notification("Abitudine modificata.", NotificationType.SUCCESS).show();
        		this.visualizzaInfoAbitudine(abitudine);
        	} 
            aggiornaView();
        }
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
