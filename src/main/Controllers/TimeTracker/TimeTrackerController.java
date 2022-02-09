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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Models.timetracker.classes.Attivit‡;
import main.Models.timetracker.classes.PomodoroTimer;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TimeTracker;
import main.Models.timetracker.classes.TrackerEnum;
import main.Models.timetracker.interfaces.IAttivit‡;
import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;
import main.Models.timetracker.interfaces.ITrackable;
import main.Colori;
import main.Main;
import main.Controllers.Helpers.Helper;
import main.Controllers.Modals.Modal;
import main.Controllers.Notifications.Notification;
import main.Controllers.Notifications.NotificationType;

public class TimeTrackerController implements ITrackable {
    
	//-------------------------------- CAMPI FXML -----------------------------------
    @FXML
    private AnchorPane panePrincipale;
    @FXML
    private AnchorPane containerAttivit‡;
	@FXML
    private ScrollPane scroll;
	@FXML
	private AnchorPane menuProgettiFormContainer;
    @FXML
    private VBox listaGiorniAttivit‡;
    @FXML 
    private BorderPane menuProgetti;
    @FXML
    private VBox listaProgetti;
    @FXML
    private TextField attivit‡Text;
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
     * Progetto scelto l'attivit‡ da monitorare.
     */
	private IProgetto progetto = null;
	
	/*
	 * Pagina corrente della cronologia attivit‡.
	 */
	private int pagina = 1;
	
	/*
	 * Se un qualunque men˘ progetto Ë aperto.
	 */
    private boolean menuProgettiAperto = false;
    
    /*
     * L'eventuale men˘ progetto apero.
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
     * Cambia il progetto dell'attivit‡ corrente.
     */
    private void cambiaProgettoCorrente(IProgetto progetto) {
    	this.boxProgetto.getChildren().clear(); 
    	if(progetto != null) {
        	// crea il label progetto
        	Label label = TTHelper.creaLabelProgetto(progetto);
        	
            // aggiunge il label alla view
        	this.progetto = progetto;
            this.boxProgetto.getChildren().add(label);
    	} else {
    		HBox box = Helper.creaBtnAggiunta("Progetto");
    		this.boxProgetto.getChildren().add(box);
    		this.progetto = TimeTracker.progettoDefault;
    	}

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
    			Notification n = new Notification("Perfavore, inserisci un nome per il progetto", NotificationType.ERROR);
    			n.show();
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
            	new Notification("Nuovo progetto aggiunto", NotificationType.SUCCESS).show();
        	} else {
        		progetto.setNome(nome);
        		progetto.setColore(colore);
        		new Notification("Progetto modificato", NotificationType.SUCCESS).show();
        	}
        	
        	chiudiMenuProgetti();
        	aggiornaView();
        }
    }
    
    /**
     * Elimina un Progetto dal modello.
     */
    private void eliminaProgetto(IProgetto progetto) {
    	tt.eliminaProgetto(progetto.getId());
    	new Notification("Progetto eliminato", NotificationType.INFO).show();
    	chiudiMenuProgetti();
    	aggiornaView();
    }
    
    /**
     * Aggiorna la view dei progetti.
     */
    private void aggiornaView() {
        listaGiorniAttivit‡.getChildren().clear();
        creaCronologiaAttivit‡(tt.getGiorniAttivit‡(pagina));
    }
    
    /**
     * Aggiunge la cronologia delle attivit‡ alla view.
     */
    private void creaCronologiaAttivit‡(List<List<IAttivit‡>> giorni) {
    	
    	// resetto la view 
    	listaGiorniAttivit‡.getChildren().clear();
    	
    	// aggiungo i giorni con le attivit‡ alla view
        for(int i = 0; i < giorni.size(); i++) {
            this.creaViewGiorno(giorni.get(i));
        }
        
        // aggiungo il pulsante di paginazione alla fine
        if(tt.getGiorniAttivit‡(1).size() == 10) {
        	listaGiorniAttivit‡.getChildren().add(creaPageBtn());
        }
    }
    
    /**
     * Aggiunge un giorno di attivit‡ alla view.
     */
    private void creaViewGiorno(List<IAttivit‡> giorno) {
        
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
        
        // crea il box con la lista di attivit‡
        VBox box2 = new VBox();
        for(int i = 0; i < giorno.size(); i++) {
            IAttivit‡ attivit‡ = giorno.get(i);          
            box2.getChildren().add(creaViewAttivit‡(attivit‡));
        }
        box.getChildren().add(box2);

        // aggiunge il container alla view
        this.listaGiorniAttivit‡.getChildren().add(pane);
        
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
        		if(obj instanceof Attivit‡) {
        			apriEditorAttivit‡((IAttivit‡) obj);
        		} else if(obj instanceof Progetto) {
        			apriEditorProgetto((IProgetto) obj, false);
        		}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        menuItem2.setOnAction((ActionEvent e) -> {
        	if(obj instanceof Attivit‡) {
    			eliminaAttivit‡((IAttivit‡) obj);
    		} else if(obj instanceof Progetto) {
    			eliminaProgetto((IProgetto) obj);
    		}
        });
        menu.getItems().addAll(menuItem1, menuItem2);
        
        // Evento per il click del men˘ attivit‡
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
     * Apre l'editor di modifica di un'attivit‡.
     */
    private void apriEditorAttivit‡(IAttivit‡ attivit‡) throws IOException {
    	
    	// crea il modal
    	URL fileUrl = Main.class.getResource("/main/Views/TimeTracker/EditorAttivit‡.fxml");
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(fileUrl);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/main/Views/TimeTracker/TimeTracker.css").toExternalForm());
        editor.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());  
    	Modal modal = new Modal(editor, "");
        
        // ottiene il controller e imposta l'attivit‡ da modificare
        EditorAttivit‡Controller controller = fxmlLoader.getController();
        controller.setListaProgetti(tt.getProgetti());
        controller.setAttivit‡(attivit‡);
         
        // imposta il titolo del modal
		modal.setTitolo("Modifica attivit‡");
		modal.getButton(ButtonType.OK).setText("Crea");
        
    	// valida gli input
    	final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
    	btnOk.addEventFilter(ActionEvent.ACTION, event -> {
    		if(controller.getNome().isBlank() || controller.getDurata() <= 0) {
    			event.consume();	
    			Notification n = new Notification(NotificationType.ERROR);
    			if(controller.getNome().isBlank()) {
    				n.setMessaggio("Perfavore, inserisci un nome per l'attivit‡");
    			} else if(controller.getDurata() <= 0) {
    				n.setMessaggio("Perfavore, inserisci un durata valida per l'attivit‡");
    			}
    			n.show();
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
            
        	// modifica l'attivit‡
        	attivit‡.setNome(nome);
        	attivit‡.setDurata(durata);
        	attivit‡.setProgettoPadre(progetto);
        	
        	new Notification("Attivit‡ modificata con successo", NotificationType.SUCCESS).show();
        	aggiornaView();
        }
    }
    
    /**
     * Elimina un'Attivit‡ dal modello.
     */
    private void eliminaAttivit‡(IAttivit‡ attivit‡) {
        tt.eliminaAttivit‡(attivit‡);
        new Notification("Attivit‡ eliminata", NotificationType.INFO).show();
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
    		containerAttivit‡.getChildren().remove(menuProgettiCorrente);
    		menuProgettiFormContainer.getChildren().clear();
    	}
    }
    
    private void toggleMenuProgetti(MouseEvent t, IAttivit‡ a, boolean menuForm) {
    	if(!menuProgettiAperto) {
    		BorderPane menu = creaMenuProgetti(t, a, menuForm);
    		menuProgettiAperto = true;
    		menuProgettiCorrente = menu;
    	} else {
    		chiudiMenuProgetti();
    	}
    }
    
    /**
     * Apre e chiude il menu progetti per le singole attivit‡
     */
    private BorderPane creaMenuProgetti(MouseEvent t, IAttivit‡ a, boolean menuForm) {
    	
		// crea il men˘ dropdown
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
        
        // posiziona correttamente il men˘
        final Stage stage = new Stage();
		if(menuForm) {
			
			// posiziona il men˘ nel form attivit‡
			AnchorPane container = menuProgettiFormContainer;
			AnchorPane.setRightAnchor(menuProgetti, 14.0);
			AnchorPane.setTopAnchor(menuProgetti, 0.0);
			container.getChildren().add(menuProgetti);
		} else {
			
			// posiziona il men˘ in corrispondenza della rispettiva attivit‡
	    	stage.initStyle(StageStyle.TRANSPARENT);
	    	stage.initModality(Modality.WINDOW_MODAL);
	        Scene scene = new Scene(menuProgetti, 250, 320);
	        scene.setFill(Color.TRANSPARENT);
	        String css = this.getClass().getResource("/main/Globall.css").toExternalForm();
	        String css2 = this.getClass().getResource("/main/Views/TimeTracker/TimeTracker.css").toExternalForm();
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
        
        // aggiunge gli item al men˘ dropdown
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
                	cambiaProgettoCorrente(null);
                } else {
                	
                	// rimuove la durata dell'attivit‡ dal vecchio progetto
                	IProgetto vecchioProgetto = a.getProgetto();
                	vecchioProgetto.eliminaDurata(a);
                	
                	if(!(vecchioProgetto.getId().equals(TimeTracker.progettoDefault.getId()))) {
                		new Notification("Attivit‡ modificata", NotificationType.SUCCESS).show();
                	}
                	
                	// modifica il progetto
                	a.setProgettoPadre(TimeTracker.progettoDefault);
                	
                }
                aggiornaView();
                chiudiMenuProgetti();
            }
        });
        
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
                        	
                        	// rimuove la durata dell'attivit‡ dal vecchio progetto
                        	IProgetto vecchioProgetto = a.getProgetto();
                        	vecchioProgetto.eliminaDurata(a);
                        	
                        	// modifica il progetto
                        	a.setProgettoPadre(p);
                        	
                        	new Notification("Attivit‡ modificata", NotificationType.SUCCESS).show();
                        }
                        aggiornaView();
                        chiudiMenuProgetti();
                    }
                });
                
            }
        }
            
        return menuProgetti;
    }
    
    private void toggleTextField(TextField field, Label label, IAttivit‡ a) {
		field.setVisible(false);
        label.setVisible(true);
        if(!field.getText().isBlank() && !(field.getText().equals(a.getNome()))) {
        	a.setNome(field.getText());
        	new Notification("Attivit‡ modificata", NotificationType.SUCCESS).show();
        	creaCronologiaAttivit‡(tt.getGiorniAttivit‡(pagina));
        }
    }
    
    /**
     * Crea la view di un'attivit‡.
     */
    private BorderPane creaViewAttivit‡(IAttivit‡ attivit‡) {
        // crea un BorderPane.
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("attivita");
        pane.setPadding(new Insets(0, 20, 0, 20));
        pane.setMinHeight(70);
        
        // crea la parte sinistra del BorderPane.
        BorderPane attivit‡Sinistra = new BorderPane();
        attivit‡Sinistra.setMinWidth(250);
        Label label2 = new Label(attivit‡.getNome());
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
        attivit‡Sinistra.setLeft(stackPane);
        pane.setLeft(attivit‡Sinistra);
        HBox btnProgetto = new HBox();
        btnProgetto.setAlignment(Pos.CENTER);
        if(!attivit‡.getProgetto().getId().equals(TimeTracker.progettoDefault.getId())) {
        	Label label5 = TTHelper.creaLabelProgetto(attivit‡.getProgetto());
        	btnProgetto.getChildren().add(label5);
        } else {
        	btnProgetto = Helper.creaBtnAggiunta("Progetto");	
        }
        attivit‡Sinistra.setRight(btnProgetto);
        
        // aggiunge event handler al pulsante progetti
        btnProgetto.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                toggleMenuProgetti(t, attivit‡, false);
            }
        });
        
        // aggiunge event handler al nome attivit‡
        stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                field.setVisible(true);
                field.requestFocus();
                label2.setVisible(false);
                field.setText(attivit‡.getNome());
            }
        });
        
        // gestisce apertura input di testo su click nome attivit‡
        field.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				toggleTextField(field, label2, attivit‡);
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
        HBox attivit‡Destra = new HBox();
        attivit‡Destra.setAlignment(Pos.CENTER);
        attivit‡Destra.setFillHeight(true);
        HBox box1 = new HBox();
        box1.setAlignment(Pos.CENTER);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        Label label3 = new Label(attivit‡.getOraInizio().format(formatter));
        Label label7 = new Label("  -  ");
        Label label6 = new Label(attivit‡.getOraFine().format(formatter));
        label3.getStyleClass().add("data-attivita");
        label6.getStyleClass().add("data-attivita");
        label7.getStyleClass().add("data-attivita");
        box1.getChildren().addAll(label3, label7, label6);
        box1.getStyleClass().add("attivita-destra");
        box1.setPadding(new Insets(20, 25, 20, 25));
        Label label4 = new Label(TTHelper.formattaOrologio((int) attivit‡.getDurata()));
        label4.getStyleClass().add("durata-attivita");
        label4.setPadding(new Insets(20, 25, 20, 25));
        pane.setRight(attivit‡Destra);
        HBox btn = creaViewEditBtn(attivit‡);
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(0, 25, 0, 0));
        container.getChildren().add(box1);
        container.getChildren().add(label4);
        attivit‡Destra.getChildren().add(container);
        attivit‡Destra.getChildren().add(btn);
        
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
    	this.progetto = TimeTracker.progettoDefault;
        this.creaCronologiaAttivit‡(tt.getGiorniAttivit‡(pagina));
    }
	
	@Override
	public void timerTerminato(long tempo) {
		new Notification("Attivit‡ aggiunta", NotificationType.SUCCESS).show();
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
     * Apre men˘ progetti del form.
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
        orarioText1.setText("" + LocalTime.now().getHour());
        orarioText2.setText("" + LocalTime.now().getMinute());
        dataManuale.setValue(LocalDate.now());
        formManuale.setVisible(true);
    }
	
    /**
     * Avvia il tracker.
     */
    @FXML
    private void avviaTracker() throws IOException {
    	if(this.attivit‡Text.getText() != "") {
    		
    		// cambia i pulsanti
    		togglePulsantiTracker();
    		
    		// avvia il tracker
    		String nome = this.attivit‡Text.getText();
    		IAttivit‡ a = new Attivit‡(nome, LocalDate.now(), LocalTime.now(), 0L);
    		if(this.progetto != null) {
    			a.setProgettoPadre(this.progetto);
    		}
    		tt.avviaTracker(a);
    		tt.getTracker().setAscoltatore(this);
    		
    	} else {
    		new Notification("Perfavore inserisci il nome di un'attivit‡.", NotificationType.ERROR).show();
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
    	
    	// aggiorna la cronologia attivit‡ nella view
    	aggiornaView();
    	
    	// resetta view durata
    	oreLabel.setText("00");
    	minutiLabel.setText("00"); 
    	secondiLabel.setText("00");
    	
    	new Notification("Attivit‡ aggiunta", NotificationType.SUCCESS).show();
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
        	
        	new Notification("Pomodoro timer modificato", NotificationType.SUCCESS).show();
        }
    }
    
    /**
     * Crea un'attivit‡ manualmente senza far partire il tracker.
     */
    @FXML
    private void creaAttivit‡Manualmente() throws IOException {
        if(this.attivit‡Text.getText() == "") {
        	new Notification("Perfavore inserisci il nome dell'attivit‡.", NotificationType.ERROR).show();
            return;
        } else if (this.orarioText1.getText() == "" || this.orarioText2.getText() == "") {
        	new Notification("Perfavore inserisci l'orario di inizio attivit‡.", NotificationType.ERROR).show();
            return;
        } else if (this.durataText1.getText() == "" || this.durataText2.getText() == "" || this.durataText3.getText() == "") {
        	new Notification("Perfavore inserisci la durata dell'attivit‡.", NotificationType.ERROR).show();
            return;
        } else if(this.dataManuale.getValue() == null) {
        	new Notification("Perfavore inserisci una data per l'attivit‡", NotificationType.ERROR).show();
            return;
        }
        String nome = attivit‡Text.getText();
        LocalDate data = dataManuale.getValue();
        int ore = Integer.parseInt(durataText1.getText());
        int minuti = Integer.parseInt(durataText2.getText());
        int secondi = Integer.parseInt(durataText3.getText());
        int ora1 = Integer.parseInt(this.orarioText1.getText());
        int ora2 = Integer.parseInt(this.orarioText2.getText());
        long durata = ore * 3600 + minuti * 60 + secondi;
        LocalTime ora = LocalTime.of(ora1, ora2);
        Attivit‡ a = new Attivit‡(nome, data, ora, durata); 
        if(this.progetto != null) {
        	a.setProgettoPadre(this.progetto);
        }
        
    	// aggiunge attivit‡ al modello
    	tt.aggiungiAttivit‡(a);
    	
    	new Notification("Attivit‡ aggiunta", NotificationType.SUCCESS).show();
    	aggiornaView();
    }
    
}
