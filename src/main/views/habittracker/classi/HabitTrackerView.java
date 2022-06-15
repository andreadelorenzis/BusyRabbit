package main.views.habittracker.classi;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import main.controller.habittracker.IHabitTrackerController;
import main.controller.IController;
import main.controller.habittracker.HabitTrackerController;
import main.controller.helpers.Helper;
import main.model.goalmanager.classi.AzioneScomponibile;
import main.model.goalmanager.classi.AzioneSessione;
import main.model.goalmanager.classi.Item;
import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IAzioneSessione;
import main.model.goalmanager.interfacce.IItem;
import main.model.habittracker.classi.AbitudineScomponibile;
import main.model.habittracker.classi.AbitudineSessione;
import main.model.habittracker.classi.HabitTracker;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineScomponibile;
import main.model.habittracker.interfacce.IAbitudineSessione;
import main.model.timetracker.classi.TimerSemplice;
import main.model.timetracker.interfacce.ITrackable;
import main.views.LoaderRisorse;
import main.views.goalmanager.classi.EditorItem;
import main.views.goalmanager.classi.ViewHelperGM;
import main.views.habittracker.interfacce.IHabitTrackerView;
import main.views.modal.Modal;
import main.views.notification.Notification;
import main.views.notification.NotificationType;
import main.views.timetracker.classi.ViewHelperTT;

public class HabitTrackerView implements IHabitTrackerView, ITrackable {

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
    
    private IHabitTrackerController controller;
    
    /*
     * Usate per il funzionamento delle abitudini sessione
     */
    private IAzioneSessione sessioneCorrente; 
    private Label labelSessioneCorrente;
    private Button btnSessioneCorrente;
  
    @FXML
    private void initialize() {
    	this.controller = new HabitTrackerController();
    	setController(controller);
    	visualizzaAbitudiniGiornaliere();
    }
    
	@Override
	public void setController(IController c) {
		this.controller = (IHabitTrackerController) c;
		this.controller.setView(this);
	}

	@Override
	public IController getController() {
		return this.controller;
	}
	
    @Override
    public void visualizzaAbitudiniGiornaliere() {
        abitudiniBox.getChildren().clear();
        List<IAbitudine> habits = HabitTracker.getInstance().calculateTodayHabits(LocalDate.now());
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
    
    @Override
    public void visualizzaTotaleAbitudini() {
        abitudiniBox.getChildren().clear();
        List<IAbitudine> habits = HabitTracker.getInstance().getHabits();
        
        // crea container abitudini
        VBox vBox1 = new VBox();
        
        // aggiunge le abitudini al container
        for(IAbitudine h : habits) {
        	BorderPane pane = this.creaViewAbitudine(h, false);
        	vBox1.getChildren().add(pane);
        }
        
        abitudiniBox.getChildren().add(vBox1);
    }
    
    @Override
    public void aggiornaAbitudini() {
    	if(giornaliereSelected) {
    		visualizzaAbitudiniGiornaliere();
    	} else {
    		visualizzaTotaleAbitudini();
    	}
    }
    
    @Override
    public void visualizzaInfoAbitudine(IAbitudine abitudine) {
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
	
	@Override
	public void timerTerminato(long tempo) {
		Platform.runLater(() -> {
			this.labelSessioneCorrente.setText("00:00:00");
	    	new Notification("Azione completata", NotificationType.SUCCESS).show();
	    	this.aggiornaAbitudini();
		});
	}

	@Override
	public void secondoPassato(int o, int m, int s) {
		Platform.runLater(() -> {
			this.visualizzaOrologio(o, m, s);
		});
	}

	@Override
	public void successo(String m) {
		new Notification(m, NotificationType.SUCCESS).show();
	}
	
	@Override
	public void errore(String s) {
		new Notification(s, NotificationType.ERROR).show();
	}

	@Override
	public void info(String m) {
		new Notification(m, NotificationType.INFO).show();
	}
	
    @FXML
    public void aggiungiAbitudine() throws IOException {
    	this.apriEditorAbitudine(null, true);
    }
    
    /**
     * Apre l'editor delle azioni in aggiunta/modifica.
     */
    private void apriEditorItem(IAbitudineScomponibile abitudine) throws IOException {
    	
        // crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.GM, "EditorItem"));
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.GM, "GoalManager"));
        editor.getStylesheets().add(LoaderRisorse.globalCss);  
    	Modal modal = new Modal(editor, "");
    	modal.setTitolo("Nuovo item");
        
        // Ottiene il controller e imposta l'azione se in modifica
        EditorItem controller = fxmlLoader.getController();
        
        // valida gli input
        final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
        btnOk.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
    		if(controller.getNome().isBlank()){
    			event.consume();
    			new Notification("Inserisci un nome per l'item", NotificationType.ERROR).show();
        	}
    	});
        
    	// apre il dialog e attende
        ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	Item nuovoItem = new Item(nome);
        	this.controller.creaItem(abitudine, nuovoItem);
        	this.aggiornaAbitudini();
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
        
        if(isDaily) {
        	VBox container = new VBox();
        	pane.setLeft(container);
        	
        	// crea la checkbox
            CheckBox check = new CheckBox();
            check.setText(abitudine.getName());
            check.getStyleClass().add("check-abitudine");
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
                    aggiornaAbitudini();
                }
            }); 
            
            if(abitudine instanceof AbitudineScomponibile) {
            	IAbitudineScomponibile abitudineScomponibile = (IAbitudineScomponibile) abitudine;
                
                // crea container per gli item
                VBox itemContainer = new VBox();
                itemContainer.getStyleClass().add("item-container");
            	container.getChildren().add(itemContainer);
            	
            	// crea pulsante aggiunta item
            	HBox hBox = Helper.creaBtnAggiunta("Item");
            	hBox.getStyleClass().add("aggiunta-item");
            	itemContainer.getChildren().add(hBox);
            	pane.setBottom(itemContainer);
            	
            	// collega evento aggiunta item
                hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                    	try {
							apriEditorItem(abitudineScomponibile);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }
                });
            	
                // aggiunge gli item dell'abitudine scomponibile
                if(abitudineScomponibile.getItems().size() > 0) { 
                	for(IItem item : abitudineScomponibile.getItems()) {
                		CheckBox itemCheck = ViewHelperGM.creaCheckbox(item.getNome(), item.getCompletato());
                		itemCheck.getStyleClass().add("item-checkbox");
                		itemContainer.getChildren().add(itemCheck);
                		
                		// se l'abitudine � completata, completa anche l'item
                		if(abitudineScomponibile.isCompleted())
                			itemCheck.setSelected(true);
                		
                        // collega evento click checkbox abitudine
                        itemCheck.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent t) {
                            	t.consume();
                                controller.completaItem((Item) item);
                            }
                        });
                	}
                }
            } else if(abitudine instanceof AbitudineSessione) {
        		IAbitudineSessione abitudineSessione = (IAbitudineSessione) abitudine;
        		
        		// aggiunge i controlli del timer all'azione sessione
        		TimerSemplice timer = new TimerSemplice(abitudineSessione.getDuration(), this);
        		HBox timerContainer = new HBox();
        		timerContainer.getStyleClass().add("session-container");
        		timerContainer.setAlignment(Pos.CENTER_LEFT);
        		String durata = ViewHelperTT.formattaOrologio(abitudineSessione.getDuration()*60);
        		Label tempo = new Label(durata);
        		tempo.getStyleClass().add("session-tempo");
        		Button timerBtn = new Button("AVVIA");
        		timerBtn.getStyleClass().add("session-btn");
        		timerContainer.getChildren().addAll(timerBtn, tempo);
        		pane.setBottom(timerContainer);
        		
        		// se l'azione � completate, disabilita il timer
        		if(abitudineSessione.isCompleted()) 
        			timerBtn.setDisable(true);
        		
        		// evento timer
        		timerBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                    	t.consume();
                    	labelSessioneCorrente = tempo;
                    	btnSessioneCorrente = timerBtn;
                        if(abitudineSessione.isStarted()) {
                        	controller.terminaAzioneSessione(abitudineSessione);
                        	timer.termina();
                        	timerBtn.setText("AVVIA");
                        	timerBtn.setStyle("-fx-background-color: #1ABC9C");
                        } else {
                        	abitudineSessione.startSession();;
                        	timer.avvia();
                        	timerBtn.setText("STOP");
                        	timerBtn.setStyle("-fx-background-color: #E7515A");
                        }
                    }
                });
            }
            
            pane.setLeft(check);
        } else {
         // crea l'elemento della lista
            pane.setLeft(Helper.creaElementoLista(abitudine.getName()));
        }
        
        // crea pulsante apertura men� abitudine
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        HBox editBtn = Helper.creaBtnEdit(); 
        hBox.getChildren().addAll(editBtn);
     
        // crea il menu dropdown
        ContextMenu menu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Modifica");
        MenuItem menuItem2 = new MenuItem("Elimina");
        menu.getStyleClass().add("edit-menu");
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
        	this.aggiornaAbitudini();
        	new Notification("Abitudine eliminata.", NotificationType.INFO).show();
        });
        menu.getItems().addAll(menuItem1, menuItem2);
        
        // Evento per il click del men� attivit�
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
    
    private void apriEditorAbitudine(IAbitudine abitudine, boolean nuovo) throws IOException {
        
        // crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.HT, "EditorAbitudine"));
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.HT, "HabitTracker"));
        editor.getStylesheets().add(LoaderRisorse.globalCss);  
    	Modal modal = new Modal(editor, "");
    	EditorAbitudine controller = fxmlLoader.getController();
        
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
            aggiornaAbitudini();
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
            aggiornaAbitudini();
    	} else {
    		giornaliereSelected = true;
            evidenziaPulsante(giornaliereBtn);
            aggiornaAbitudini();
    	}
    }
	
	private void visualizzaOrologio(int o, int m, int s) {
		String ore = ViewHelperTT.formattaDurata(o);
		String minuti = ViewHelperTT.formattaDurata(m);
		String secondi = ViewHelperTT.formattaDurata(s);
		this.labelSessioneCorrente.setText(ore + ":" + minuti + ":" + secondi);
	}
    
}
