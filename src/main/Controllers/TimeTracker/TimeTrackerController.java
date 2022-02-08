package main.Controllers.TimeTracker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import main.Models.timetracker.classes.Attività;
import main.Models.timetracker.classes.PomodoroTimer;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TrackerEnum;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;
import main.Models.timetracker.interfaces.ITrackable;
import main.Colori;
import main.Main;
import main.Controllers.Helpers.Helper;
import main.Controllers.Modals.Modal;

public class TimeTrackerController implements ITrackable {
    
	//-------------------------------- CAMPI FXML -----------------------------------
    @FXML
    private AnchorPane panePrincipale;
    @FXML
    private AnchorPane containerAttività;
	@FXML
    private ScrollPane scroll;
	@FXML
	private AnchorPane menuProgettiFormContainer;
    @FXML
    private VBox listaGiorniAttività;
    @FXML 
    private BorderPane menuProgetti;
    @FXML
    private VBox listaProgetti;
    @FXML
    private TextField attivitàText;
    @FXML
    private BorderPane formTimeTracker;     
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
    private Button stopBtn;         
    @FXML
    private Button startBtn;
    @FXML
    private Label oreLabel;
    @FXML
    private Label minutiLabel;
    @FXML
    private Label secondiLabel;
    @FXML
    private Button settingsBtn;
    @FXML
    private Button manualBtn;
    @FXML
    private Button pomoBtn;
    @FXML
    private Button cronoBtn;
    
	//--------------------------------- CAMPI ------------------------------------
    /*
     * Istanza del TimeTracker dell'app.
     */
    private ITimeTracker tt;
    
    /*
     * Progetto scelto l'attività da monitorare.
     */
	private IProgetto progetto = null;
	
	/*
	 * Pagina corrente della cronologia attività.
	 */
	private int pagina = 1;
	
	/*
	 * Se un qualunque menù progetto è aperto.
	 */
    private boolean menuProgettiAperto = false;
    
    /*
     * L'eventuale menù progetto apero.
     */
    private BorderPane menuProgettiCorrente;
    
    //--------------------------- METODI PRIVATI --------------------------------
    
    /**
     * Aggiunge un progetto alla view.
     */
    public BorderPane creaViewProgetto(IProgetto progetto, VBox container) {     
    	
        // crea contenitore progetto
        BorderPane pane = TTHelper.creaPaneProgetto(progetto);
        
        // aggiunge pulsante di edit nella parte destra
        HBox editBtn = creaViewEditBtn(progetto);
        pane.setRight(editBtn);
        
        // aggiunge il progetto alla view
        container.getChildren().add(pane);
        
        return pane;
    }
    
    /**
     * Cambia il progetto dell'attività corrente.
     */
    private void cambiaProgettoCorrente(IProgetto progetto) {
    	
    	// crea il label progetto
    	Label label = TTHelper.creaLabelProgetto(progetto);
        
        // aggiunge il label alla view
    	this.progetto = progetto;
        this.boxProgetto.getChildren().clear(); 
        this.boxProgetto.getChildren().add(label);

    }
    
    /**
     * Apre l'editor di aggiunta/modifica progetti.
     * 
     */
    private void apriEditorProgetto(IProgetto progetto, boolean aggiunta) throws IOException {
    	URL fileUrl = Main.class.getResource("/main/Views/TimeTracker/EditorProgetto.fxml");
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(fileUrl);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/main/Views/TimeTracker/TimeTracker.css").toExternalForm());
        editor.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());  
    	Modal modal = new Modal(editor, "");
    	
    	if(aggiunta) {
    		modal.setTitolo("Nuovo progetto");
    		modal.getButton(ButtonType.OK).setText("Crea");
    	} else {
    		modal.setTitolo("Modifica progetto");
    		modal.getButton(ButtonType.OK).setText("Modifica");
    	}
    	
        // ottiene il controller e imposta il progetto
        EditorProgettoController controller = fxmlLoader.getController();
        controller.setProgetto(progetto);
        
        // valida gli input
    	final HBox btnLookup = modal.getBtnLookup(ButtonType.OK);
    	btnLookup.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
    		if(controller.getNome().isBlank()) {
    			event.consume();
    			throw new IllegalStateException("Perfavore, inserisci un nome per il progetto");
        	}
    	});
        
    	// apre il modal e attende l'input dell'utente
    	ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	Colori colore = controller.getColore();
        	
        	// aggiunge o modifica il colore
        	if(aggiunta) {
            	tt.aggiungiProgetto(new Progetto(nome, colore));
        	} else {
        		progetto.setNome(nome);
        		progetto.setColore(colore);
        	}
        	
        	chiudiMenuProgetti();
        	aggiornaView();
        }
    }
    
    /**
     * Elimina un Progetto dal modello.
     */
    private void eliminaProgetto(IProgetto progetto) {
    	
        // elimina progetto dal modello
    	tt.eliminaProgetto(progetto.getId());

    	aggiornaView();
    }
    
    /**
     * Aggiorna la view dei progetti.
     */
    private void aggiornaView() {
        listaGiorniAttività.getChildren().clear();
        creaCronologiaAttività(tt.getGiorniAttività(pagina));
    }
    
    /**
     * Aggiunge la cronologia delle attività alla view.
     */
    private void creaCronologiaAttività(List<List<IAttività>> giorni) {
    	
    	// resetto la view 
    	listaGiorniAttività.getChildren().clear();
    	
    	// aggiungo i giorni con le attività alla view
        for(int i = 0; i < giorni.size(); i++) {
            this.creaViewGiorno(giorni.get(i));
        }
        
        // aggiungo il pulsante di paginazione alla fine
        if(tt.getGiorniAttività(1).size() == 10) {
        	listaGiorniAttività.getChildren().add(creaPageBtn());
        }
    }
    
    /**
     * Aggiunge un giorno di attività alla view.
     */
    private void creaViewGiorno(List<IAttività> giorno) {
        
        // crea un border pane
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("container-giorno");
        pane.setPadding(new Insets(0, 0, 40, 0));
        
        // crea il container del giorno
        VBox box = new VBox();
        box.getStyleClass().add("giorno");
        pane.setCenter(box);
        
        // crea l'header con le info relative al giorno
        BorderPane header = TTHelper.creaHeaderGiorno(giorno);
        header.setMinHeight(50);
        box.getChildren().add(header);
        
        // crea il box con la lista di attività
        VBox box2 = new VBox();
        for(int i = 0; i < giorno.size(); i++) {
            IAttività attività = giorno.get(i);          
            box2.getChildren().add(creaViewAttività(attività));
        }
        box.getChildren().add(box2);

        // aggiunge il container alla view
        this.listaGiorniAttività.getChildren().add(pane);
        
    }
    
    /**
     * Crea la view del pulsante di modifica.
     */
    private HBox creaViewEditBtn(Object obj) {
    	
    	// creo il pulsante
        HBox editBtn = Helper.creaBtnEdit();
        
        // creo il menu dropdown
        ContextMenu menu = new ContextMenu();
        menu.getStyleClass().add("edit-menu");
        MenuItem menuItem1 = new MenuItem("Modifica");
        MenuItem menuItem2 = new MenuItem("Elimina");
        menuItem1.setOnAction((ActionEvent e) -> {
        	try {
        		if(obj instanceof Attività) {
        			apriEditorAttività((IAttività) obj);
        		} else if(obj instanceof Progetto) {
        			apriEditorProgetto((IProgetto) obj, false);
        		}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        menuItem2.setOnAction((ActionEvent e) -> {
        	if(obj instanceof Attività) {
    			eliminaAttività((IAttività) obj);
    		} else if(obj instanceof Progetto) {
    			eliminaProgetto((IProgetto) obj);
    		}
        });
        menu.getItems().addAll(menuItem1, menuItem2);
        
        // Evento per il click del menù attività
        editBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            	menu.show(editBtn, t.getScreenX(), t.getScreenY());
            	t.consume();
            }
        }); 
        
        return editBtn;
    }   
    
    /**
     * Apre l'editor di modifica di un'attività.
     */
    private void apriEditorAttività(IAttività attività) throws IOException {
    	
    	// crea il modal
    	URL fileUrl = Main.class.getResource("/main/Views/TimeTracker/EditorAttività.fxml");
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(fileUrl);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/main/Views/TimeTracker/TimeTracker.css").toExternalForm());
        editor.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());  
    	Modal modal = new Modal(editor, "");
        
        // ottiene il controller e imposta l'attività da modificare
        EditorAttivitàController controller = fxmlLoader.getController();
        controller.setListaProgetti(tt.getProgetti());
        controller.setAttività(attività);
        
        // imposta il titolo del modal
		modal.setTitolo("Nuovo progetto");
		modal.getButton(ButtonType.OK).setText("Crea");
        
    	// valida gli input
    	final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
    	btnOk.addEventFilter(ActionEvent.ACTION, event -> {
    		if(controller.getNome().isBlank() || controller.getDurata() <= 0) {
    			event.consume();			
    			if(controller.getNome().isBlank()) {
    				throw new IllegalStateException("Perfavore, inserisci un nome per l'attività");
    			} else if(controller.getDurata() <= 0) {
    				throw new IllegalStateException("Perfavore, inserisci un durata valida per l'attività");
    			}
        	}
    	});
        
        // apre il dialog e attende
        ButtonType btnCliccato = modal.show();
        
        // quando l'utente clicca OK.
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	IProgetto progetto = controller.getProgetto();
        	LocalDate data = controller.getData();
        	LocalTime ora = controller.getOra();
        	long durata = controller.getDurata();
            
        	// modifica l'attività
        	attività.setNome(nome);
        	attività.setDurata(durata);
        	attività.setProgettoPadre(progetto);
        	
        	aggiornaView();
        }
    }
    
    /**
     * Elimina un'Attività dal modello.
     */
    private void eliminaAttività(IAttività attività) {
    	
    	// elimina attività dal modello
        tt.eliminaAttività(attività);
        
        aggiornaView();
    }
    
    private void decrementaPagina() {
    	if(pagina > 1) {
    		pagina--;
    		aggiornaView();
    	}
    }
    
    private void incrementaPagina() {
    	if(pagina * 10 < tt.getNumGiorni()) {
    		pagina++;
    		aggiornaView();
    	}
    }

    /**
     * Crea il pulsante di cambio pagina.
     */
    private HBox creaPageBtn() {
    	int numGiorni = tt.getNumGiorni();
    	
    	// crea il container del pulsante
        HBox form = new HBox();
        form.setAlignment(Pos.CENTER_LEFT);
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("page-btn");
        pane.setPadding(new Insets(0, 15, 0, 15));
        form.getChildren().add(pane);
        
        // crea freccia sinistra
        ImageView leftArrow = TTHelper.creaArrow(true);
        pane.setLeft(leftArrow);
        
        // crea freccia destra
        ImageView rightArrow = TTHelper.creaArrow(false);
        pane.setRight(rightArrow);
        
        // disabilito le freccie se sono agli estremi
        if(pagina == 1) {
        	leftArrow.setDisable(true);
        	leftArrow.setOpacity(0.35);
        } else if(pagina * 10 >= numGiorni) {
        	rightArrow.setDisable(true);
        	rightArrow.setOpacity(0.35);
        }
        
        // evento freccia sinistra
        leftArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                decrementaPagina();
            }
        });
        
        // evento freccia destra
        rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                incrementaPagina();
            }
        });
        
        // crea label
        Label label = TTHelper.creaLabelPageBtn(pagina, numGiorni);
        pane.setCenter(label);

        return form;
    }
    
    private void chiudiMenuProgetti() {
    	if(menuProgettiAperto) {
    		menuProgettiAperto = false;
    		containerAttività.getChildren().remove(menuProgettiCorrente);
    		menuProgettiFormContainer.getChildren().clear();
    	}
    }
    
    private void toggleMenuProgetti(MouseEvent t, IAttività a, boolean menuForm) {
    	if(!menuProgettiAperto) {
    		BorderPane menu = creaMenuProgetti(t, a, menuForm);
    		menuProgettiAperto = true;
    		menuProgettiCorrente = menu;
    	} else {
    		chiudiMenuProgetti();
    	}
    }
    
    /**
     * Apre e chiude il menu progetti per le singole attività
     */
    private BorderPane creaMenuProgetti(MouseEvent t, IAttività a, boolean menuForm) {
    	
		// crea il menù dropdown
		BorderPane menuProgetti = new BorderPane();
		menuProgetti.getStyleClass().add("menu-progetti");
        HBox box = new HBox();
        box.setStyle("-fx-background-color: #1B2E4B;");
        menuProgetti.setTop(box);
        box.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Progetti");
        label.getStyleClass().add("menu-progetti-titolo");
        box.getChildren().add(label);
        menuProgetti.setMinHeight(312);
        menuProgetti.setMinWidth(230);
        menuProgetti.setMaxHeight(312);
        menuProgetti.setMaxWidth(230);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        menuProgetti.setCenter(scrollPane);
        VBox containerProgetti = new VBox();
        containerProgetti.setStyle("-fx-background-color: #1B2E4B; -fx-padding: 10 0 0 0;");
        scrollPane.setContent(containerProgetti);
        scrollPane.setStyle("-fx-background-color: #1B2E4B;");
        HBox box2 = Helper.creaBtnAggiunta("Crea nuovo progetto");
        box2.setAlignment(Pos.CENTER);
        box2.setStyle("-fx-background-color: #1B2E4B; -fx-padding: 0 0 10 0;");
        menuProgetti.setBottom(box2);
        
        // posiziona correttamente il menù
		AnchorPane container = containerAttività;
		if(menuForm) {
			container = menuProgettiFormContainer;
			AnchorPane.setRightAnchor(menuProgetti, 0.0);
			AnchorPane.setTopAnchor(menuProgetti, 0.0);
		} else {
			menuProgetti.setLayoutX(t.getSceneX() - 280);
	        menuProgetti.setLayoutY(t.getSceneY() - 270);
		}
		container.getChildren().add(menuProgetti);
        
        
        // aggiunge event handler al pulsante di aggiunta
        box2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
            	try {
					aggiungiProgetto();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        // aggiunge evento click al di fuori
        menuProgetti.requestFocus();
        menuProgetti.focusedProperty().addListener((prop, oldNode, newNode) -> {
        	if(!menuProgetti.isHover()) {
        		chiudiMenuProgetti();
        	}
        });
        
        // aggiunge gli item al menù dropdown
        for(int i = 0; i < tt.getProgetti().size(); i++) {
            if(i > 0) {
            	IProgetto p = tt.getProgetti().get(i);    	
            	BorderPane pane = creaViewProgetto(p, containerProgetti);
            	
                // evento click del progetto
                pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                        if(menuForm) {
                        	cambiaProgettoCorrente(p);
                        } else {
                        	
                        	// rimuove la durata dell'attività dal vecchio progetto
                        	IProgetto vecchioProgetto = a.getProgetto();
                        	vecchioProgetto.eliminaDurata(a);
                        	
                        	// modifica il progetto
                        	a.setProgettoPadre(p);
                        }
                        aggiornaView();
                        chiudiMenuProgetti();
                    }
                });
                
            }
        }
            
        return menuProgetti;
    }
    
    private void toggleTextField(TextField field, Label label, IAttività a) {
		field.setVisible(false);
        label.setVisible(true);
        if(!field.getText().isBlank()) {
        	a.setNome(field.getText());
        }
        creaCronologiaAttività(tt.getGiorniAttività(pagina));
    }
    
    /**
     * Crea la view di un'attività.
     */
    private BorderPane creaViewAttività(IAttività attività) {
        // crea un BorderPane.
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("attivita");
        pane.setPadding(new Insets(0, 20, 0, 20));
        pane.setMinHeight(60);
        
        // crea la parte sinistra del BorderPane.
        BorderPane attivitàSinistra = new BorderPane();
        attivitàSinistra.setMinWidth(250);
        Label label2 = new Label(attività.getNome());
        BorderPane.setAlignment(label2, Pos.CENTER);
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER_LEFT);
        stackPane.getChildren().add(label2);
        TextField field = new TextField();
        field.getStyleClass().add("text-field-attivita");
        stackPane.getChildren().add(field);
        field.setVisible(false);
        label2.getStyleClass().add("nome-attivita");
        label2.setPadding(new Insets(0, 20, 0, 20));
        attivitàSinistra.setLeft(stackPane);
        pane.setLeft(attivitàSinistra);
        HBox btnProgetto = new HBox();
        btnProgetto.setAlignment(Pos.CENTER);
        if(attività.getProgetto().getNome() != "Altro") {
        	Label label5 = TTHelper.creaLabelProgetto(attività.getProgetto());
        	btnProgetto.getChildren().add(label5);
        } else {
        	btnProgetto = Helper.creaBtnAggiunta("Progetto");	
        }
        attivitàSinistra.setRight(btnProgetto);
        
        // aggiunge event handler al pulsante progetti
        btnProgetto.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                toggleMenuProgetti(t, attività, false);
            }
        });
        
        // aggiunge event handler al nome attività
        stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                field.setVisible(true);
                field.requestFocus();
                label2.setVisible(false);
            }
        });
        
        // gestisce apertura input di testo su click nome attività
        field.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				toggleTextField(field, label2, attività);
			}
        	
        });
        
        // gestisce chiusura input di testo quando perde il focus
        field.focusedProperty().addListener((prop, oldNode, newNode) -> {
        	if(!field.isFocused()) {
        		toggleTextField(field, label2, attività);
        	}
        });

        // Crea la parte destra del BorderPane.
        HBox attivitàDestra = new HBox();
        attivitàDestra.setAlignment(Pos.CENTER);
        attivitàDestra.setFillHeight(true);
        HBox box1 = new HBox();
        box1.setAlignment(Pos.CENTER);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        Label label3 = new Label(attività.getOraInizio().format(formatter));
        Label label7 = new Label("  -  ");
        Label label6 = new Label(attività.getOraFine().format(formatter));
        label3.getStyleClass().add("data-attivita");
        label6.getStyleClass().add("data-attivita");
        label7.getStyleClass().add("data-attivita");
        box1.getChildren().addAll(label3, label7, label6);
        box1.getStyleClass().add("attivita-destra");
        box1.setPadding(new Insets(20, 25, 20, 25));
        Label label4 = new Label(TTHelper.formattaOrologio((int) attività.getDurata()));
        label4.getStyleClass().add("durata-attivita");
        label4.setPadding(new Insets(20, 25, 20, 25));
        pane.setRight(attivitàDestra);
        HBox btn = creaViewEditBtn(attività);
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0, 25, 0, 0));
        container.getChildren().add(box1);
        container.getChildren().add(label4);
        attivitàDestra.getChildren().add(container);
        attivitàDestra.getChildren().add(btn);
        
        return pane;
    }
    
    private void togglePulsantiTracker() {
		startBtn.setVisible(!startBtn.isVisible());
		stopBtn.setVisible(!stopBtn.isVisible());
    }
    
    private void nascondiTrackers() {
        formManuale.setVisible(false);
        settingsBtn.setVisible(false);
        formTimeTracker.setVisible(false);
    }
    
    private void rimuoviEvidenziazionePulsanti() {
    	String style = "-fx-background-color: #1B2A44";
    	pomoBtn.setStyle(style);
    	cronoBtn.setStyle(style);
    	manualBtn.setStyle(style);
    }
	
	private void impostaOrologio(int ore, int minuti, int secondi) {
		oreLabel.setText(TTHelper.formattaDurata(ore));
    	minutiLabel.setText(TTHelper.formattaDurata(minuti));
    	secondiLabel.setText(TTHelper.formattaDurata(secondi));
	}
	
	
    //--------------------------- METODI PUBBLICI --------------------------------
    public void setTimeTracker(ITimeTracker tt) {
    	this.tt = tt;
        this.creaCronologiaAttività(tt.getGiorniAttività(pagina));
    }
	
	@Override
	public void timerTerminato(long tempo) {
		aggiornaView();
	}
	
	@Override
	public void secondoPassato(int ore, int minuti, int secondi) {
		Platform.runLater(() -> {
			impostaOrologio(ore, minuti, secondi);
		});
	}
	
	
    //------------------------------ METODI FXML ----------------------------------
	/**
	 * Inizializza il controller.
	 */
    @FXML 
    private void initialize() {        
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #122C49;");
        scroll.getStyleClass().add("edge-to-edge");
    }
	
    /**
     * Apre menù progetti del form.
     */
    @FXML
    private void apriMenuProgettiForm(MouseEvent t) {
        toggleMenuProgetti(t, null, true);  
    }
    
    /**
     * Apre l'editor di aggiunta progetto.
     */
    @FXML
    private void aggiungiProgetto() throws IOException {
       apriEditorProgetto(new Progetto("", Colori.Giallo), true);
    }
    
    @FXML
    private void attivaCronometro() {
    	rimuoviEvidenziazionePulsanti();
    	cronoBtn.setStyle("-fx-background-color: #3C4B63");
    	tt.scegliTracker(TrackerEnum.CRONOMETRO);
        nascondiTrackers();
        formTimeTracker.setVisible(true);
        impostaOrologio(0, 0, 0);
    }
    
    @FXML
    private void attivaPomodoro() {
    	rimuoviEvidenziazionePulsanti();
    	pomoBtn.setStyle("-fx-background-color: #3C4B63");
    	tt.scegliTracker(TrackerEnum.POMODOROTIMER);
    	nascondiTrackers();
    	formTimeTracker.setVisible(true);
    	settingsBtn.setVisible(true);
    	PomodoroTimer p = (PomodoroTimer) tt.getTracker();
    	int[] params = TTHelper.scomponiDurata(p.getDurataSessione());
    	impostaOrologio(params[0], params[1], params[2]);
    }
    
    @FXML
    private void attivaManuale() {
    	rimuoviEvidenziazionePulsanti();
    	manualBtn.setStyle("-fx-background-color: #3C4B63");
        nascondiTrackers();
        orarioText1.setPromptText("" + LocalTime.now().getHour());
        orarioText2.setPromptText("" + LocalTime.now().getMinute());
        dataManuale.setValue(LocalDate.now());
        formManuale.setVisible(true);
    }
	
    /**
     * Avvia il tracker.
     */
    @FXML
    private void avviaTracker() throws IOException {
    	if(this.attivitàText.getText() != "") {
    		
    		// cambia i pulsanti
    		togglePulsantiTracker();
    		
    		// avvia il tracker
    		String nome = this.attivitàText.getText();
    		IAttività a = new Attività(nome, LocalDate.now(), LocalTime.now(), 0L);
    		if(this.progetto != null) {
    			a.setProgettoPadre(this.progetto);
    		}
    		tt.avviaTracker(a);
    		tt.getTracker().setAscoltatore(this);
    		
    	} else {
    		System.out.println("Perfavore inserisci il nome di un'attività.");
    	}
    }
	
    /**
     * Termina il tracker.
     */
    @FXML
    private void terminaTracker() {
    	
    	// termina il tracker
    	tt.terminaTracker();
 
    	// cambia i pulsanti
    	togglePulsantiTracker();
    	
    	// aggiorna la cronologia attività nella view
    	aggiornaView();
    	
    	// resetta view durata
    	oreLabel.setText("00");
    	minutiLabel.setText("00");
    	secondiLabel.setText("00");
    }
    
    @FXML 
    private void impostaTimer() throws IOException {
        
        // crea il modal
    	URL fileUrl = Main.class.getResource("/main/Views/TimeTracker/ImpostazioniTimer.fxml");
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(fileUrl);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/main/Views/TimeTracker/TimeTracker.css").toExternalForm());
        editor.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());  
    	Modal modal = new Modal(editor, "");
    	modal.setTitolo("Imposta pomodoro timer");
        
        // ottiene il controller e setta le impostazioni di del pomodoro timer attuali
        ImpostazioniTimerController controller = fxmlLoader.getController();
        IPomodoroTimer pt = (IPomodoroTimer) tt.getTracker();
        controller.setPomodoro(pt);
        
        // apre il modal
        ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {
        	
        	// imposto il pomodoro timer nel modello
        	pt.setDurataSessione(controller.getSessione());
        	pt.setDurataPausaBreve(controller.getPausaBreve());
        	pt.setDurataPausaLunga(controller.getPausaLunga());
        	
        	// refresh della view dell'orologio
        	int[] params = TTHelper.scomponiDurata(controller.getSessione());
        	impostaOrologio(params[0], params[1], params[2]);
        }
    }
    
    /**
     * Crea un'attività manualmente senza far partire il tracker.
     */
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
        } else if(this.dataManuale.getValue() == null) {
        	System.out.println("Perfavore inserisci una data per l'attività.");
            return;
        }
        String nome = attivitàText.getText();
        LocalDate data = dataManuale.getValue();
        int ore = Integer.parseInt(durataText1.getText());
        int minuti = Integer.parseInt(durataText2.getText());
        int secondi = Integer.parseInt(durataText3.getText());
        int ora1 = Integer.parseInt(this.orarioText1.getText());
        int ora2 = Integer.parseInt(this.orarioText2.getText());
        long durata = ore * 3600 + minuti * 60 + secondi;
        LocalTime ora = LocalTime.of(ora1, ora2);
        Attività a = new Attività(nome, data, ora, durata); 
        if(this.progetto != null) {
        	a.setProgettoPadre(this.progetto);
        }
        
    	// aggiunge attività al modello
    	tt.aggiungiAttività(a);
    	
    	aggiornaView();
    }
    
}
