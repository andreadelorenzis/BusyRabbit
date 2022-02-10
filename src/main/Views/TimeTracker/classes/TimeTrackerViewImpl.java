package main.Views.TimeTracker.classes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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
import main.Colori;
import main.Main;
import main.Controllers.Helpers.Helper;
import main.Views.Modals.Modal;
import main.Views.Notifications.Notification;
import main.Views.Notifications.NotificationType;
import main.Controllers.TimeTracker.TimeTrackerController;
import main.Controllers.TimeTracker.TimeTrackerControllerImpl;
import main.Models.timetracker.classes.Attivit‡;
import main.Models.timetracker.classes.PomodoroTimer;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TimeTracker;
import main.Models.timetracker.interfaces.IAttivit‡;
import main.Models.timetracker.interfaces.IPomodoroTimer;
import main.Models.timetracker.interfaces.IProgetto;
import main.Views.TimeTracker.interfaces.TimeTrackerView;

public class TimeTrackerViewImpl implements TimeTrackerView {
	
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
	
    /*
     * Risorse utilizzate
     */
    private final URL editorAttivit‡ = Main.class.getResource("/main/Views/TimeTracker/resources/EditorAttivit‡.fxml");
    private final URL editorTimer = Main.class.getResource("/main/Views/TimeTracker/resources/ImpostazioniTimer.fxml");
    private final URL editorProgetti = Main.class.getResource("/main/Views/TimeTracker/resources/EditorProgetto.fxml");
    private final URL timeTrackerCss = Main.class.getResource("/main/Views/TimeTracker/resources/TimeTracker.css");
    private final URL globalCss = Main.class.getResource("/main/Globall.css");
    
    /*
     * Observer di questa view
     */
	private TimeTrackerController ttc;
	
	/*
	 * Se un qualunque men˘ progetto Ë aperto
	 */
    private boolean menuProgettiAperto = false;
    
    /*
     * L'eventuale men˘ progetto apero
     */
    private BorderPane menuProgettiCorrente;
    
    /*
     * Progetto scelto dal men˘ progetti
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
        this.aggiornaView(TimeTracker.getInstance().getGiorniAttivit‡(1), 1);
        
        // imposta il controller
        TimeTrackerController ttc = new TimeTrackerControllerImpl(this);
        this.ttc = ttc;
    }
    
    private BorderPane creaViewProgetto(IProgetto progetto, VBox container) {     
    	
        // crea contenitore progetto
        BorderPane pane = ViewHelper.creaPaneProgetto(progetto);
        
        // aggiunge pulsante di edit nella parte destra
        HBox editBtn = creaViewEditBtn(progetto);
        pane.setRight(editBtn);
        
        // aggiunge il progetto alla view
        container.getChildren().add(pane);
        
        return pane;
    }
    
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
        			List<IProgetto> progetti = TimeTracker.getInstance().getProgetti();
        			apriEditorAttivit‡((IAttivit‡) obj, progetti);
        		} else if(obj instanceof Progetto) {
        			apriEditorProgetto((IProgetto) obj, false);
        		}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        menuItem2.setOnAction((ActionEvent e) -> {
        	if(obj instanceof Attivit‡) {
    			ttc.eliminaAttivit‡((IAttivit‡) obj);
    		} else if(obj instanceof Progetto) {
    			ttc.eliminaProgetto((IProgetto) obj);
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
    
    private void apriEditorAttivit‡(IAttivit‡ attivit‡, List<IProgetto> progetti) throws IOException {
    	
    	// crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(editorAttivit‡);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(timeTrackerCss.toExternalForm());
        editor.getStylesheets().add(globalCss.toExternalForm());  
    	Modal modal = new Modal(editor, "");
        
        // ottiene il controller e imposta l'attivit‡ da modificare
        EditorAttivit‡ViewImpl controller = fxmlLoader.getController();
        controller.setListaProgetti(progetti);
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
        	IAttivit‡ attivit‡Modificata = new Attivit‡(nome, data, ora, durata, progetto);
        	ttc.modificaAttivit‡(attivit‡, attivit‡Modificata);
        	
        	new Notification("Attivit‡ modificata con successo", NotificationType.SUCCESS).show();
        }
    }
    
    private void apriEditorProgetto(IProgetto progetto, boolean aggiunta) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(editorProgetti);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(timeTrackerCss.toExternalForm());
        editor.getStylesheets().add(globalCss.toExternalForm());  
    	Modal modal = new Modal(editor, "");
    	
    	if(aggiunta) {
    		modal.setTitolo("Nuovo progetto");
    		modal.getButton(ButtonType.OK).setText("Crea");
    	} else {
    		modal.setTitolo("Modifica progetto");
    		modal.getButton(ButtonType.OK).setText("Modifica");
    	}
    	
        // ottiene il controller e imposta il progetto
        EditorProgettoViewImpl controller = fxmlLoader.getController();
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
        		ttc.aggiungiProgetto(new Progetto(nome, colore));
            	new Notification("Nuovo progetto aggiunto", NotificationType.SUCCESS).show();
        	} else {
        		IProgetto progettoModificato = new Progetto(nome, colore);
        		ttc.modificaProgetto(progetto, progettoModificato);
        		new Notification("Progetto modificato", NotificationType.SUCCESS).show();
        	}
        	
        	chiudiMenuProgetti();
        }
    }
    
    private void chiudiMenuProgetti() {
    	if(menuProgettiAperto) {
    		menuProgettiAperto = false;
    		containerAttivit‡.getChildren().remove(menuProgettiCorrente);
    		menuProgettiFormContainer.getChildren().clear();
    	}
    }
    
    private void creaCronologiaAttivit‡(List<List<IAttivit‡>> giorni, int pagina) {
    	
    	// resetto la view 
    	listaGiorniAttivit‡.getChildren().clear();
    	
    	// aggiungo i giorni con le attivit‡ alla view
        for(int i = 0; i < giorni.size(); i++) {
            this.creaViewGiorno(giorni.get(i));
        }
        
        // aggiungo il pulsante di paginazione alla fine
        if(giorni.size() == 10) {
        	listaGiorniAttivit‡.getChildren().add(creaPageBtn(pagina));
        }
    }
    
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
        ImageView leftArrow = ViewHelper.creaArrow(true);
        pane.setLeft(leftArrow);
        
        // crea freccia destra
        ImageView rightArrow = ViewHelper.creaArrow(false);
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
                ttc.decrementaPagina();
            }
        });
        
        // evento freccia destra
        rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                ttc.incrementaPagina();
            }
        });
        
        // crea label
        Label label = ViewHelper.creaLabelPageBtn(pagina, numGiorni);
        pane.setCenter(label);

        return form;
    }
    
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
        BorderPane header = ViewHelper.creaHeaderGiorno(giorno);
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
    
    private void toggleTextField(TextField field, Label label, IAttivit‡ a) {
		field.setVisible(false);
        label.setVisible(true);
        if(!field.getText().isBlank() && !(field.getText().equals(a.getNome()))) {
        	IAttivit‡ copia = this.getCopiaAttivit‡(a);
        	copia.setNome(field.getText());
        	ttc.modificaAttivit‡(a, copia);
        	new Notification("Attivit‡ modificata", NotificationType.SUCCESS).show();
        }
    }

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
        	Label label5 = ViewHelper.creaLabelProgetto(attivit‡.getProgetto());
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
        Label label4 = new Label(ViewHelper.formattaOrologio((int) attivit‡.getDurata()));
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
    
    private void toggleMenuProgetti(MouseEvent t, IAttivit‡ a, boolean menuForm) {
    	if(!menuProgettiAperto) {
    		BorderPane menu = creaMenuProgetti(t, a, menuForm);
    		menuProgettiAperto = true;
    		menuProgettiCorrente = menu;
    	} else {
    		chiudiMenuProgetti();
    	}
    }
    
    private IAttivit‡ getCopiaAttivit‡(IAttivit‡ a) {
    	return new Attivit‡(a.getNome(), a.getData(), a.getOraInizio(), a.getDurata(), a.getProgetto(), a.getId());
    }
    
    private BorderPane creaMenuProgetti(MouseEvent t, IAttivit‡ a, boolean menuForm) {
    	List<IProgetto> progetti = TimeTracker.getInstance().getProgetti();
    	
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
	        String css = globalCss.toExternalForm();
	        String css2 = timeTrackerCss.toExternalForm();
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
                	cambiaProgettoCorrente(TimeTracker.progettoDefault);
                } else {
                	
                	IAttivit‡ copia = getCopiaAttivit‡(a);
                	IProgetto vecchioProgetto = a.getProgetto();
                	copia.setProgettoPadre(TimeTracker.progettoDefault);
                	ttc.modificaAttivit‡(a, copia);
                	if(!(vecchioProgetto.getId().equals(TimeTracker.progettoDefault.getId()))) {
                		new Notification("Attivit‡ modificata", NotificationType.SUCCESS).show();
                	}
                	
                }
                chiudiMenuProgetti();
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
                        	
                        	// rimuove la durata dell'attivit‡ dal vecchio progetto
                        	IProgetto vecchioProgetto = a.getProgetto();
                        	vecchioProgetto.eliminaDurata(a);
                        	
                        	// modifica il progetto
                        	a.setProgettoPadre(p);
                        	
                        	new Notification("Attivit‡ modificata", NotificationType.SUCCESS).show();
                        }
                        chiudiMenuProgetti();
                    }
                });
                
            }
        }
            
        return menuProgetti;
    }
    
    private void cambiaProgettoCorrente(IProgetto progetto) {
    	this.boxProgetto.getChildren().clear(); 
    	if(progetto != null) {
        	// crea il label progetto
        	Label label = ViewHelper.creaLabelProgetto(progetto);
        	
            // aggiunge il label alla view
            this.boxProgetto.getChildren().add(label);
    	} else {
    		HBox box = Helper.creaBtnAggiunta("Progetto");
    		this.boxProgetto.getChildren().add(box);
    	}

    }

	@Override
	public void aggiornaView(List<List<IAttivit‡>> giorni, int pagina) {
		listaGiorniAttivit‡.getChildren().clear();
        creaCronologiaAttivit‡(giorni, pagina);
	}

	@Override
	public void progettoAggiunto() {
		new Notification("Progetto aggiunto", NotificationType.SUCCESS).show();
	}

	@Override
	public void attivit‡Aggiunta() {
		new Notification("Attivit‡ aggiunta", NotificationType.SUCCESS).show();
	}

	@FXML
	private void cronometroScelto() {
    	rimuoviEvidenziazionePulsanti();
    	cronoBtn.setStyle("-fx-background-color: #3C4B63");
    	ttc.scegliCronometro();
        nascondiTrackers();
        formTimeTracker.setVisible(true);
	}

	@FXML
	private void pomodoroScelto() {
    	rimuoviEvidenziazionePulsanti();
    	pomoBtn.setStyle("-fx-background-color: #3C4B63");
    	ttc.scegliPomodoro();
    	nascondiTrackers();
    	formTimeTracker.setVisible(true);
    	settingsBtn.setVisible(true);
    	PomodoroTimer p = (PomodoroTimer) TimeTracker.getInstance().getTracker();
    	int[] params = ViewHelper.scomponiDurata(p.getDurataSessione());
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

	@Override
	public void visualizzaOrologio(int ore, int min, int sec) {
		oreLabel.setText(ViewHelper.formattaDurata(ore));
    	minutiLabel.setText(ViewHelper.formattaDurata(min));
    	secondiLabel.setText(ViewHelper.formattaDurata(sec));
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
	
    /**
     * Avvia il tracker.
     */
    @FXML
    private void trackerAvviato() throws IOException {
    	if(this.attivit‡Text.getText() != "") {
    		
    		// cambia i pulsanti
    		togglePulsantiTracker();
    		
    		// avvia il tracker
    		String nome = this.attivit‡Text.getText();
    		ttc.avviaTracker(nome);
    		
    	} else {
    		new Notification("Perfavore inserisci il nome di un'attivit‡.", NotificationType.ERROR).show();
    	}
    }
	
    /**
     * Termina il tracker.
     */
    @FXML
    public void trackerTerminato() {
    	
    	// termina il tracker
    	ttc.terminaTracker();
 
    	// cambia i pulsanti
    	togglePulsantiTracker();
    	
    	// resetta view durata
    	oreLabel.setText("00");
    	minutiLabel.setText("00"); 
    	secondiLabel.setText("00");
    	
    	new Notification("Attivit‡ aggiunta", NotificationType.SUCCESS).show();
    }
    
    @FXML 
    private void impostaTimer() throws IOException {
        
        // crea il modal
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(editorTimer);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(timeTrackerCss.toExternalForm());
        editor.getStylesheets().add(globalCss.toExternalForm());  
    	Modal modal = new Modal(editor, "");
    	modal.setTitolo("Imposta pomodoro timer");
        
        // ottiene il controller e setta le impostazioni di del pomodoro timer attuali
        EditorTimerViewImpl controller = fxmlLoader.getController();
        IPomodoroTimer pt = (IPomodoroTimer) TimeTracker.getInstance().getTracker();
        controller.setPomodoro(pt);
        
        // apre il modal
        ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {
        	int sessione = controller.getSessione();
        	int pausaBreve = controller.getPausaBreve();
        	int pausaLunga = controller.getPausaLunga();
        	ttc.impostaPomodoroTimer(sessione, pausaBreve, pausaLunga);
        	int[] params = ViewHelper.scomponiDurata(controller.getSessione());
        	this.visualizzaOrologio(params[0], params[1], params[2]);
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
    	ttc.aggiungiAttivit‡(a);
    	new Notification("Attivit‡ aggiunta", NotificationType.SUCCESS).show();
    }

}
