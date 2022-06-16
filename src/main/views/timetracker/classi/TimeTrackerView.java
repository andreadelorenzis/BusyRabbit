package main.views.timetracker.classi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.controller.IController;
import main.controller.timetracker.ITimeTrackerController;
import main.controller.timetracker.TimeTrackerController;
import main.model.timetracker.classi.Attività;
import main.model.timetracker.classi.PomodoroTimer;
import main.model.timetracker.classi.Progetto;
import main.model.timetracker.classi.TimeTracker;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IPomodoroTimer;
import main.model.timetracker.interfacce.IProgetto;
import main.views.Colore;
import main.views.ViewHelper;
import main.views.LoaderRisorse;
import main.views.modal.Modal;
import main.views.notification.Notification;
import main.views.notification.NotificationType;
import main.views.timetracker.interfacce.ITimeTrackerView;

public class TimeTrackerView implements ITimeTrackerView {
	
	//------------------------------ CAMPI FXML ---------------------------------
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
    
    //-------------------------------- CAMPI -----------------------------------
    /*
     * Observer di questa view
     */
	private ITimeTrackerController controller;
	
	/*
	 * Se un qualunque menù progetto è aperto
	 */
    private boolean menuProgettiAperto = false;
    
    /*
     * L'eventuale menù progetto apero
     */
    private BorderPane menuProgettiCorrente;
    
    /*
     * Progetto scelto dal menù progetti
     */
    private IProgetto progetto = TimeTracker.progettoDefault;
    
	/**
	 * Inizializza il controller.
	 */
    @FXML 
    private void initialize() {        
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #122C49;");
        scroll.getStyleClass().add("edge-to-edge");
        this.aggiornaView(TimeTracker.getInstance().getGiorniAttività(1), 1);
        
        // imposta il controller
        ITimeTrackerController controller = new TimeTrackerController();
        setController(controller);
    }
    
    //--------------------------- METODI PUBBLICI ------------------------------
	@Override
	public void setController(IController c) {
		this.controller = (ITimeTrackerController) c;
		this.controller.setView(this);
	}

	@Override
	public IController getController() {
		return this.controller;
	}
	
	@Override
	public void aggiornaView(List<List<IAttività>> giorni, int pagina) {
        creaCronologiaAttività(giorni, pagina);
	}

	@Override
	public void progettoAggiunto() {
		new Notification("Progetto aggiunto", NotificationType.SUCCESS).show();
	}

	@Override
	public void attivitàAggiunta() {
		new Notification("Attività aggiunta", NotificationType.SUCCESS).show();
	}
	
	@Override
	public void successo(String m) {
		new Notification(m, NotificationType.SUCCESS).show();
	}

	@Override
	public void errore(String m) {
		new Notification(m, NotificationType.ERROR).show();
	}

	@Override
	public void info(String m) {
		new Notification(m, NotificationType.INFO).show();
	}
	
	@FXML
	public void trackerTerminato() {
		
		// termina il tracker
		this.controller.terminaTracker();
		
		// cambia i pulsanti
		togglePulsantiTracker();
		
		// resetta view durata
		oreLabel.setText("00");
		minutiLabel.setText("00"); 
		secondiLabel.setText("00");
	}
	
	@Override
	public void visualizzaOrologio(int ore, int min, int sec) {
		oreLabel.setText(ViewHelperTT.formattaDurata(ore));
    	minutiLabel.setText(ViewHelperTT.formattaDurata(min));
    	secondiLabel.setText(ViewHelperTT.formattaDurata(sec));
	}
    
	//---------------------------- METODI PRIVATI ------------------------------
    private BorderPane creaViewProgetto(IProgetto progetto, VBox container) {     
    	
        // crea contenitore progetto
        BorderPane pane = ViewHelperTT.creaPaneProgetto(progetto);
        
        // aggiunge pulsante di edit nella parte destra
        HBox editBtn = creaEditBtn(progetto);
        pane.setRight(editBtn);
        
        // aggiunge il progetto alla view
        container.getChildren().add(pane);
        
        return pane;
    }
    
    /**
     * Crea il pulsante di opzioni di attività e progetti
     * 
     * @param obj
     * @return
     */
    private HBox creaEditBtn(Object obj) {
    	
    	// creo il pulsante
        HBox editBtn = ViewHelper.creaBtnEdit();
        
        // creo il menu dropdown
        ContextMenu menu = new ContextMenu();
        menu.getStyleClass().add("edit-menu");
        MenuItem menuItem1 = new MenuItem("Modifica");
        MenuItem menuItem2 = new MenuItem("Elimina");
        menuItem1.setOnAction((ActionEvent e) -> {
        	try {
        		if(obj instanceof Attività) {
        			List<IProgetto> progetti = TimeTracker.getInstance().getProgetti();
        			apriEditorAttività((IAttività) obj, progetti);
        		} else if(obj instanceof Progetto) {
        			apriEditorProgetto((IProgetto) obj, false);
        		}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        menuItem2.setOnAction((ActionEvent e) -> {
        	if(obj instanceof Attività) {
    			this.controller.eliminaAttività((IAttività) obj);
    		} else if(obj instanceof Progetto) {
    			this.controller.eliminaProgetto((IProgetto) obj);
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
     * Apre l'editor delle attività per modifica/aggiunta
     * 
     * @param attività
     * @param progetti
     * @throws IOException
     */
    private void apriEditorAttività(IAttività attività, List<IProgetto> progetti) throws IOException {
    	
    	// crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.TT, "EditorAttività"));
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.TT, "TimeTracker"));
        editor.getStylesheets().add(LoaderRisorse.globalCss);  
    	Modal modal = new Modal(editor, "");
        
        // ottiene il controller e imposta l'attività da modificare
        EditorAttività controller = fxmlLoader.getController();
        controller.setAttività(attività);
         
        // imposta il titolo del modal
		modal.setTitolo("Modifica attività");
		modal.getButton(ButtonType.OK).setText("Crea");
        
    	// valida gli input
    	final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
    	btnOk.addEventFilter(ActionEvent.ACTION, event -> {
    		if(controller.getNome().isBlank() || controller.getDurata() <= 0) {
    			event.consume();	
    			Notification n = new Notification(NotificationType.ERROR);
    			if(controller.getNome().isBlank()) {
    				n.setMessaggio("Perfavore, inserisci un nome per l'attività");
    			} else if(controller.getDurata() <= 0) {
    				n.setMessaggio("Perfavore, inserisci un durata valida per l'attività");
    			}
    			n.show();
        	}
    	});
        
        // apre il dialog e attende
        ButtonType btnCliccato = modal.showAndWait();
        
        // quando l'utente clicca OK.
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	LocalDate data = controller.getData();
        	LocalTime ora = controller.getOra();
        	long durata = controller.getDurata();
            
        	// modifica l'attività
        	IAttività attivitàModificata = new Attività(nome, data, ora, durata);
        	this.controller.modificaAttività(attività, attivitàModificata);
        }
    }
    
    /**
     * Apre l'editor dei progetti per modifica/aggiunta
     * 
     * @param progetto
     * @param aggiunta
     * @throws IOException
     */
    private void apriEditorProgetto(IProgetto progetto, boolean aggiunta) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.TT, "EditorProgetto"));
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.TT, "TimeTracker"));
        editor.getStylesheets().add(LoaderRisorse.globalCss);  
    	Modal modal = new Modal(editor, "");
    	
    	if(aggiunta) {
    		modal.setTitolo("Nuovo progetto");
    		modal.getButton(ButtonType.OK).setText("Crea");
    	} else {
    		modal.setTitolo("Modifica progetto");
    		modal.getButton(ButtonType.OK).setText("Modifica");
    	}
    	
        // ottiene il controller e imposta il progetto
        EditorProgetto controller = fxmlLoader.getController();
        controller.setProgetto(progetto);
        
        // valida gli input
    	final HBox btnLookup = modal.getBtnLookup(ButtonType.OK);
    	btnLookup.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
    		if(controller.getNome().isBlank()) {
    			event.consume();
    			new Notification("Perfavore, inserisci un nome per il progetto", NotificationType.ERROR).show();
        	}
    	});
        
    	// apre il modal e attende l'input dell'utente
    	ButtonType btnCliccato = modal.showAndWait();
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	Colore colore = controller.getColore();
        	
        	// aggiunge o modifica il colore
        	if(aggiunta) {
        		this.controller.aggiungiProgetto(new Progetto(nome, colore));
        	} else {
        		IProgetto progettoModificato = new Progetto(nome, colore);
        		this.controller.modificaProgetto(progetto, progettoModificato);
        		cambiaProgettoCorrente(progettoModificato);
        	}
        	
        	chiudiMenuProgetti();
        }
    }
    
    /**
     * Chiude il menu dei progetti attualmente aperto
     */
    private void chiudiMenuProgetti() {
    	if(menuProgettiAperto) {
    		menuProgettiAperto = false;
    		containerAttività.getChildren().remove(menuProgettiCorrente);
    		menuProgettiFormContainer.getChildren().clear();
    	}
    }
    
    /**
     * Visualizza la cronologia dei giorni con tutte le attività
     * 
     * @param giorni
     * @param pagina
     */
    private void creaCronologiaAttività(List<List<IAttività>> giorni, int pagina) {
    	
    	// resetto la view 
    	listaGiorniAttività.getChildren().clear();
    	
    	// aggiungo i giorni con le attività alla view
        for(int i = 0; i < giorni.size(); i++) {
            this.creaViewGiorno(giorni.get(i));
        }
        
        // aggiungo il pulsante di paginazione alla fine
        if(giorni.size() == 10 || pagina != 1) {
        	listaGiorniAttività.getChildren().add(creaPageBtn(pagina));
        }
    }
    
    /**
     * Crea il pulsante di paginazione delle attività
     * 
     * @param pagina
     * @return
     */
    private HBox creaPageBtn(int pagina) {
    	int numGiorni = TimeTracker.getInstance().getNumGiorni();
    	
    	// crea il container del pulsante
        HBox form = new HBox();
        form.setAlignment(Pos.CENTER_LEFT);
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("page-btn");
        pane.setPadding(new Insets(0, 15, 0, 15));
        form.getChildren().add(pane);
        
        // crea freccia sinistra
        ImageView leftArrow = ViewHelperTT.creaArrow(true);
        pane.setLeft(leftArrow);
        
        // crea freccia destra
        ImageView rightArrow = ViewHelperTT.creaArrow(false);
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
                controller.decrementaPagina();
            }
        });
        
        // evento freccia destra
        rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                controller.incrementaPagina();
            }
        });
        
        // crea label
        Label label = ViewHelperTT.creaLabelPageBtn(pagina, numGiorni);
        pane.setCenter(label);

        return form;
    }
    
    /**
     * Crea la view di un singolo giorno, contenente delle attività
     * 
     * @param giorno
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
        BorderPane header = ViewHelperTT.creaHeaderGiorno(giorno);
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
    
    private void toggleTextField(TextField field, Label label, IAttività a) {
		field.setVisible(false);
        label.setVisible(true);
        if(!field.getText().isBlank() && !(field.getText().equals(a.getNome()))) {
        	IAttività copia = this.getCopiaAttività(a);
        	copia.setNome(field.getText());
        	this.controller.modificaAttività(a, copia);
        }
    }

    /**
     * Crea la view di una singola attività
     * 
     * @param attività
     * @return
     */
    private BorderPane creaViewAttività(IAttività attività) {
        // crea un BorderPane.
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("attivita");
        pane.setPadding(new Insets(0, 20, 0, 20));
        pane.setMinHeight(70);
        
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
        if(!attività.getProgetto().getId().equals(TimeTracker.progettoDefault.getId())) {
        	Label label5 = ViewHelperTT.creaLabelProgetto(attività.getProgetto());
        	btnProgetto.getChildren().add(label5);
        } else {
        	btnProgetto = ViewHelper.creaBtnAggiunta("Progetto");	
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
                field.setText(attività.getNome());
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
        		field.setVisible(false);
		        label2.setVisible(true);
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
        Label label4 = new Label(ViewHelperTT.formattaOrologio((int) attività.getDurata()));
        label4.getStyleClass().add("durata-attivita");
        label4.setPadding(new Insets(20, 25, 20, 25));
        pane.setRight(attivitàDestra);
        HBox btn = creaEditBtn(attività);
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0, 25, 0, 0));
        container.getChildren().add(box1);
        container.getChildren().add(label4);
        attivitàDestra.getChildren().add(container);
        attivitàDestra.getChildren().add(btn);
        
        return pane;
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
    
    private IAttività getCopiaAttività(IAttività a) {
    	return new Attività(a.getNome(), a.getData(), a.getOraInizio(), a.getDurata(), a.getProgetto(), a.getId());
    }
    
    /**
     * Crea il menù contenente la lista di tutti i progetti
     */
    private BorderPane creaMenuProgetti(MouseEvent t, IAttività a, boolean menuForm) {
    	List<IProgetto> progetti = TimeTracker.getInstance().getProgetti();
    	
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
        HBox box2 = ViewHelper.creaBtnAggiunta("Crea nuovo progetto");
        box2.setAlignment(Pos.CENTER);
        box2.setStyle("-fx-background-color: #1B2E4B; -fx-padding: 0 0 10 0;");
        menuProgetti.setBottom(box2);
        
        // posiziona correttamente il menù
        final Stage stage = new Stage();
		if(menuForm) {
			
			// posiziona il menù nel form attività
			AnchorPane container = menuProgettiFormContainer;
			AnchorPane.setRightAnchor(menuProgetti, 14.0);
			AnchorPane.setTopAnchor(menuProgetti, 0.0);
			container.getChildren().add(menuProgetti);
		} else {
			// posiziona il menù in corrispondenza della rispettiva attività
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.initModality(Modality.WINDOW_MODAL);
	        Scene scene = new Scene(menuProgetti, 250, 320);
	        scene.setFill(Color.TRANSPARENT);
	        String css = LoaderRisorse.globalCss;
	        String css2 = LoaderRisorse.getCSS(LoaderRisorse.TT, "TimeTracker");
	        scene.getStylesheets().addAll(css, css2);
	        stage.setResizable(false);
	        stage.setScene(scene);
	        stage.setAlwaysOnTop(true); 
	        stage.setX(t.getScreenX());
	        stage.setY(t.getScreenY());
	        stage.show();
		}
        
        // aggiunge evento click al di fuori
        menuProgetti.requestFocus(); 
        menuProgetti.focusedProperty().addListener((prop, oldNode, newNode) -> {
        	if(!menuProgetti.isHover()) {
        		chiudiMenuProgetti();
        		stage.close();
        	}
        });
		
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
        
        // aggiunge gli item al menù dropdown
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("lista-progetti-elem");
        Label label2 = new Label("Nessun progetto");
        label2.getStyleClass().add("no-project-btn");
        borderPane.setLeft(label2);
        containerProgetti.getChildren().add(borderPane);
        
        // evento click pulsante no-project
        borderPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                if(menuForm) {
                	cambiaProgettoCorrente(TimeTracker.progettoDefault);
                } else {
                	IAttività copia = getCopiaAttività(a);
                	copia.setProgettoPadre(TimeTracker.progettoDefault);
                	controller.modificaAttività(a, copia);
                }
                chiudiMenuProgetti();
                stage.close();
                aggiornaView(TimeTracker.getInstance().getGiorniAttività(controller.getPagina()), controller.getPagina());
            }
        });
        
        for(int i = 0; i < progetti.size(); i++) {
            if(i > 0) {
            	IProgetto p = progetti.get(i);    	
            	BorderPane pane = creaViewProgetto(p, containerProgetti);
            	
                // evento click del progetto
                pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                        if(menuForm) {
                        	cambiaProgettoCorrente(p);
                        } else {
                        	
                        	// rimuove la durata dell'attività dal vecchio progetto
                        	IProgetto vecchioProgetto = a.getProgetto();
                        	vecchioProgetto.eliminaAttività(a);
                        	
                        	// aggiunge durata dell'attività al nuovo progetto
                        	p.aggiungiAttività(a);
                        	a.setProgettoPadre(p);
                        	new Notification("Attività modificata", NotificationType.SUCCESS).show();
                        	
                        }
                        chiudiMenuProgetti();
                        stage.close();
                        aggiornaView(TimeTracker.getInstance().getGiorniAttività(controller.getPagina()), controller.getPagina());
                    }
                });
                
            }
        }
            
        return menuProgetti;
    }
    
    /**
     * Cambia il progetto dell'attività attualmente monitorata
     */
    private void cambiaProgettoCorrente(IProgetto progetto) {
    	this.boxProgetto.getChildren().clear(); 
    	IProgetto progettoDefault = TimeTracker.getInstance().progettoDefault;
    	if(progetto != null && !progetto.equals(progettoDefault)) {
    		this.progetto = progetto;
    		
        	// crea il label progetto
        	Label label = ViewHelperTT.creaLabelProgetto(progetto);
        	
            // aggiunge il label alla view
            this.boxProgetto.getChildren().add(label);
    	} else {
    		this.progetto = progettoDefault;
    		
    		HBox box = ViewHelper.creaBtnAggiunta("Progetto");
    		this.boxProgetto.getChildren().add(box);
    	}

    }

	@FXML
	private void cronometroScelto() {
    	rimuoviEvidenziazionePulsanti();
    	cronoBtn.setStyle("-fx-background-color: #3C4B63");
    	this.controller.scegliCronometro();
        nascondiTrackers();
        formTimeTracker.setVisible(true);
        visualizzaOrologio(0, 0, 0);
	}

	@FXML
	private void pomodoroScelto() {
    	rimuoviEvidenziazionePulsanti();
    	pomoBtn.setStyle("-fx-background-color: #3C4B63");
    	this.controller.scegliPomodoro();
    	nascondiTrackers();
    	formTimeTracker.setVisible(true);
    	settingsBtn.setVisible(true);
    	PomodoroTimer p = (PomodoroTimer) TimeTracker.getInstance().getTracker();
    	int[] params = ViewHelperTT.scomponiDurata(p.getSessione());
    	visualizzaOrologio(params[0], params[1], params[2]);
	}

	@FXML
	private void formManualeScelto() {
    	rimuoviEvidenziazionePulsanti();
    	manualBtn.setStyle("-fx-background-color: #3C4B63");
        nascondiTrackers();
        orarioText1.setText("" + LocalTime.now().getHour());
        orarioText2.setText("" + LocalTime.now().getMinute());
        dataManuale.setValue(LocalDate.now());
        formManuale.setVisible(true);
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
       apriEditorProgetto(new Progetto("", Colore.Giallo), true);
    }
	
    /**
     * Avvia il tracker.
     */
    @FXML
    private void trackerAvviato() throws IOException {
    	if(attivitàText.getText() != "") {
    		
    		// cambia i pulsanti
    		togglePulsantiTracker();
    		
    		// avvia il tracker
    		String nome = attivitàText.getText();
    		this.controller.avviaTracker(nome, progetto);
    		
    	} else {
    		new Notification("Perfavore inserisci il nome di un'attività.", NotificationType.ERROR).show();
    	}
    }
    
    /**
     * Imposta le opzioni di tempo del timer
     */
    @FXML 
    private void impostaTimer() throws IOException {
        
        // crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(LoaderRisorse.getFXML(LoaderRisorse.TT, "ImpostazioniTimer"));
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(LoaderRisorse.getCSS(LoaderRisorse.TT, "TimeTracker"));
        editor.getStylesheets().add(LoaderRisorse.globalCss);  
    	Modal modal = new Modal(editor, "");
    	modal.setTitolo("Imposta pomodoro timer");
        
        // ottiene il controller e setta le impostazioni di del pomodoro timer attuali
        EditorTimer controller = fxmlLoader.getController();
        IPomodoroTimer pt = (IPomodoroTimer) TimeTracker.getInstance().getTracker();
        controller.setPomodoro(pt);
        
        // apre il modal
        ButtonType btnCliccato = modal.showAndWait();
        if(btnCliccato == ButtonType.OK) {
        	int sessione = controller.getSessione();
        	int pausaBreve = controller.getPausaBreve();
        	int pausaLunga = controller.getPausaLunga();
        	this.controller.impostaPomodoroTimer(sessione, pausaBreve, pausaLunga);
        	int[] params = ViewHelperTT.scomponiDurata(controller.getSessione());
        	this.visualizzaOrologio(params[0], params[1], params[2]);
        	new Notification("Pomodoro timer modificato", NotificationType.SUCCESS).show();
        }
    }
    
    /**
     * Crea un'attività manualmente senza far partire il tracker.
     */
    @FXML
    private void creaAttivitàManualmente() throws IOException {
        if(this.attivitàText.getText() == "") {
        	new Notification("Perfavore inserisci il nome dell'attività.", NotificationType.ERROR).show();
            return;
        } else if (this.orarioText1.getText() == "" && this.orarioText2.getText() == "") {
        	new Notification("Perfavore inserisci l'orario di inizio attività.", NotificationType.ERROR).show();
            return;
        } else if (this.durataText1.getText() == "" && this.durataText2.getText() == "" && this.durataText3.getText() == "") {
        	new Notification("Perfavore inserisci la durata dell'attività.", NotificationType.ERROR).show();
            return;
        } else if(this.dataManuale.getValue() == null) {
        	new Notification("Perfavore inserisci una data per l'attività", NotificationType.ERROR).show();
            return;
        }
        String nome = attivitàText.getText();
        LocalDate data = dataManuale.getValue();
        String sOre = durataText1.getText();
        String sMinuti = durataText2.getText();
        String sSecondi = durataText3.getText();
        int ore = 0;
        int minuti = 0;
        int secondi = 0;
        if(!sOre.isBlank()) {
        	ore = Integer.parseInt(durataText1.getText());
        }
        if(!sMinuti.isBlank()) {
        	minuti = Integer.parseInt(durataText2.getText());
        }
        if(!sSecondi.isBlank()) {
        	secondi = Integer.parseInt(durataText3.getText());
        }
        String sOra1 = this.orarioText1.getText();
        String sOra2 = this.orarioText2.getText();
        int ora1 = 0;
        int ora2 = 0;
        if(!sOra1.isBlank()) {
        	ora1 = Integer.parseInt(this.orarioText1.getText());
        }
        if(!sOra2.isBlank()) {
        	ora2 = Integer.parseInt(this.orarioText2.getText());
        }
        long durata = ore * 3600 + minuti * 60 + secondi;
        LocalTime ora = LocalTime.of(ora1, ora2);
        Attività a = new Attività(nome, data, ora, durata); 
        if(this.progetto != null) {
        	a.setProgettoPadre(this.progetto);
        }
    	this.controller.aggiungiAttività(a);
    }

}
