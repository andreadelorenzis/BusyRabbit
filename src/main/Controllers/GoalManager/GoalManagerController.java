package main.Controllers.GoalManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.Giorno;
import main.Main;
import main.PageController;
import main.Controllers.Helpers.Helper;
import main.Views.Modals.Modal;
import main.Views.Notifications.Notification;
import main.Views.Notifications.NotificationType;
import main.Models.accountmanager.classes.App;
import main.Models.goalmanager.classes.Azione;
import main.Models.goalmanager.classes.AzioneScomponibile;
import main.Models.goalmanager.classes.AzioneSessione;
import main.Models.goalmanager.classes.Obiettivo;
import main.Models.goalmanager.classes.ObiettivoAzione;
import main.Models.goalmanager.classes.ObiettivoScomponibile;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IAzioneScomponibile;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;
import main.Models.goalmanager.interfaces.Item;


public class GoalManagerController {
    
	//-------------------------------- CAMPI FXML -----------------------------------
    @FXML 
    private BorderPane obiettivo0;
    @FXML
    private VBox boxObiettivi;    
    @FXML
    private BorderPane paginaAzioni;    
    
	//--------------------------------- CAMPI ------------------------------------
    /*
     * Istanza del GoalManager dell'app.
     */
    private IGoalManager gm;
    
    /*
     * L'ultimo obiettivo cliccato.
     */
    private IObiettivo obiettivoCliccato = null;
    
    /*
     * Il container nella view dell'ultimo obiettivo cliccato.
     */
    private BorderPane paneObiettivoCliccato = null;
    
    private VBox listaSottoObiettiviAttiva = null;
    
    /*
     * Map che contiene quali menu sotto-obiettivi sono aperti
     * chiave: id obiettivo
     * valore: se la lista sotto-obiettivi è aperta o meno
     */
    private Map<String, Boolean> obiettiviAperti = new HashMap<>();
    
    //--------------------------- METODI PRIVATI --------------------------------
    
    /**
     * Aggiorna la view degli obiettivi.
     */
    private void aggiornaObiettivi() {
        boxObiettivi.getChildren().clear();
        for(int i = 0; i < gm.getObiettivi().size(); i++) {
        	BorderPane pane = creaViewObiettivo((Obiettivo) gm.getObiettivi().get(i));
        	boxObiettivi.getChildren().add(pane);
        	if(i == 0) {
        		pane.setStyle("-fx-border-width: 0 0 0 0;");
        	}
        }            
    }
    
    private void aggiornaView() {
    	aggiornaObiettivi();
    	visualizzaAzioniGiornaliere();
    	if(obiettivoCliccato != null) {
    		this.resettaPaginaInfo();
    		apriPaginaInfo(obiettivoCliccato);
    	}
    }
    
    /**
     * Calcola a che livello si trova un determinato obiettivo nella scala gerarchica.
     */
    private int calcolaProfonditàObiettivo(IObiettivo o) {
    	int i = 0;
    	IObiettivo nodo = o.getObiettivoPadre();
    	while(nodo != null) {
    		i++;
    		nodo = nodo.getObiettivoPadre();
    	};
    	return i;
    }
    
    /**
     * Crea a view di un singolo obiettivo.
     */
    private BorderPane creaViewObiettivo(IObiettivo o) {
    	
    	// crea i container
        BorderPane pane = new BorderPane();
        pane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        pane.getStyleClass().add("obiettivo");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        pane.setLeft(vBox);
        
        // aggiunge il checkbox
        CheckBox check = new CheckBox();
        check.setText(o.getNome());
        check.setSelected(o.getCompletato());
        check.getStyleClass().add("nome");
        check.setMaxWidth(300);
        check.setWrapText(true);
        vBox.getChildren().add(check);
        vBox.getStyleClass().add("obiettivo-content");
        
        // collega evento completamento obiettivo
        check.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	t.consume();
                o.completa();
                if(o.getCompletato()) {
                	new Notification("Obiettivo completato.", NotificationType.SUCCESS).show();
                }
                aggiornaView();
            }
        });
        
        // aggiunge la data
        Label label = new Label("Ago, 07 2021");
        label.getStyleClass().add("data");
        vBox.getChildren().add(label);

        HBox btnAggiunta;
        HBox boxBtns = new HBox();
        boxBtns.setAlignment(Pos.CENTER_RIGHT);
        
        // aggiunge un container vuoto al centro del pane
        HBox center = new HBox();
        center.getStyleClass().add("obiettivo-center");
        pane.setCenter(center);
        
        // se è un obiettivo scomponibile
    	if(o instanceof ObiettivoScomponibile) {
    		
    		// crea pulsante di aggiunta sotto-obiettivi
    		btnAggiunta = Helper.creaBtnAggiunta("Sotto-obiettivo");
            
            // collega evento per aggiunta di un sotto-obiettivo.
    		btnAggiunta.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    try {
                    	t.consume();
                        aggiungiSottoObiettivo(o);
                    } catch (IOException ex) {
                        Logger.getLogger(GoalManagerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    		
    		// se è presente almeno un sotto-obiettivo
    		if(((ObiettivoScomponibile) o).getSottoObiettivi().size() > 0) {
            	IObiettivoScomponibile os = (IObiettivoScomponibile) o;
            	center.setCursor(Cursor.HAND);
            	
            	// crea label numero sotto-obiettivi
            	HBox btnSottoObiettivi = GMHelper.creaSottoObiettiviBtn(os.getSottoObiettivi().size(), center);
            	boxBtns.getChildren().add(btnSottoObiettivi);
                
                // aggiungo container per sotto-obiettivi
                VBox boxSottoObiettivi = new VBox();
                boxSottoObiettivi.getStyleClass().add("box-sotto-obiettivi");
                boxSottoObiettivi.setAlignment(Pos.TOP_RIGHT);
                pane.setBottom(boxSottoObiettivi);
                
                // collega eventi per apertura sotto obiettivi.
                EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                    	t.consume();
                    	apriPaginaInfo(o);
                        toggleSottoObiettivi(os, boxSottoObiettivi);
                    }
                };
                btnSottoObiettivi.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
                center.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
                
                // aggiunge obiettivo alla mappa sotto-obiettivi aperti
                obiettiviAperti.put(os.getId(), false);
                
    		}
    	} else {

    		// crea pulsante di aggiunta azioni
    		btnAggiunta = Helper.creaBtnAggiunta("Azione");
    		
            // collega evento per aggiunta di un'azione.
    		btnAggiunta.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    try {
                    	t.consume();
                    	apriEditorAzione(null, o, true);
                    } catch (IOException ex) {
                        Logger.getLogger(GoalManagerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    	}
        vBox.getChildren().add(btnAggiunta);
        
        center.setCursor(Cursor.HAND);
		// collega evento visualizzazione azioni obiettivo
		center.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
            	if(paneObiettivoCliccato != null) {
            		paneObiettivoCliccato.setStyle("-fx-background-color: #0E1726");
            	}
            	if(!(o instanceof ObiettivoScomponibile) ||
            	  (o instanceof ObiettivoScomponibile && ((IObiettivoScomponibile) o).getSottoObiettivi().size() == 0)) {
            		pane.setStyle("-fx-background-color: #374856; -fx-background-radius: 10; -fx-border-radius: 10;");
            	}
            	paneObiettivoCliccato = pane;
            	obiettivoCliccato = o;
                apriPaginaInfo(o);
            }
        });
       
        // crea il pulsante di apertura menù dell'obiettivo
        HBox menuBtn = creaBtnMenu(o);
        menuBtn.getStyleClass().add("obiettivo-content");
        boxBtns.getChildren().add(menuBtn);
        pane.setRight(boxBtns);
        
        return pane;
    }
    
    /**
     * Apre la lista di sotto-obiettivi dell'obiettivo cliccato.
     */
    private void toggleSottoObiettivi(IObiettivoScomponibile o, VBox container) {
        if(!obiettiviAperti.get(o.getId())) {
            if(o.getSottoObiettivi().size() > 0) {
            	
            	// calcolo la profondità del sotto-obiettivo
            	int prof = calcolaProfonditàObiettivo(o.getSottoObiettivi().get(0));
            	
            	// aggiungo un container di larghezza minore in base alla profondità
            	VBox box = new VBox();
            	int padding = 15;
            	box.setMaxWidth((boxObiettivi.getWidth() - padding) - prof * 60);
            	container.getChildren().add(box);
            	listaSottoObiettiviAttiva = container;
            	
            	// aggiungo gli obiettivi al container di larghezza minore
                for(int i = 0; i < o.getSottoObiettivi().size(); i++) {
                    BorderPane pane = creaViewObiettivo(o.getSottoObiettivi().get(i));
                    box.getChildren().add(pane);
                }
                
                // aggiorno l'info sulle liste sotto-obiettivi aperte
                obiettiviAperti.put(o.getId(), true);
            }
        } else {
        	
        	// chiudo la lista sotto-obiettivi dell'obiettivo scomponibile
            container.getChildren().clear();
            listaSottoObiettiviAttiva = null;
            obiettiviAperti.put(o.getId(), false);
        }
    }
    
    /**
     * Crea pulsante di apertura menù.
     */
    private HBox creaBtnMenu(Object obj) {
    	
        // crea view pulsante
        HBox editBtn = Helper.creaBtnEdit();
        
        // crea il menu dropdown
        ContextMenu menu = new ContextMenu();
        menu.getStyleClass().add("edit-menu");
        MenuItem menuItem1 = new MenuItem("Modifica");
        MenuItem menuItem2 = new MenuItem("Elimina");
        menuItem1.setOnAction((ActionEvent e) -> {
        	try {
        		if(obj instanceof Obiettivo) {
        			apriEditorObiettivo((Obiettivo) obj, false, false);
        		} else if(obj instanceof Azione) {
        			apriEditorAzione((Azione) obj, null, false);
        		}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        menuItem2.setOnAction((ActionEvent e) -> {
        	if(obj instanceof Obiettivo) {
    			eliminaObiettivo((Obiettivo) obj);
    		} else if(obj instanceof Azione) {
    			eliminaAzione((Azione) obj);
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
     * Apre l'editor degli obiettivi in aggiunta/modifica.
     */
    private void apriEditorObiettivo(IObiettivo o, boolean nuovo, boolean isSottoObiettivo) throws IOException {
    	
        // crea il modal
    	URL fileUrl = Main.class.getResource("/main/Views/GoalManager/EditorObiettivi.fxml");
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(fileUrl);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/main/Views/GoalManager/GoalManager.css").toExternalForm());
        editor.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());  
    	Modal modal = new Modal(editor, "");
        
        // imposta il titolo del dialog
        if(nuovo) {
        	modal.setTitolo("Nuovo obiettivo");
        } else {
        	modal.setTitolo("Modifica obiettivo");
        }
        
        // ottiene il controller del dialog e imposta l'obiettivo se in modifica
        EditorObiettiviController controller = fxmlLoader.getController();
        if(!nuovo && !isSottoObiettivo) {
        	controller.setObiettivo(o);
        }
        
        // valida gli input
    	final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
    	btnOk.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
    		if(controller.getNome().isBlank() 									||
    		   (controller.isTipoAzione() && controller.getUnità().isBlank()) 	||
    		   controller.getData() == null) {
    			event.consume();
    			if(controller.getNome().isBlank()) {
    				new Notification("Inserisci un nome per l'obiettivo", NotificationType.ERROR).show();
    			} else if(controller.isTipoAzione() && controller.getUnità().isBlank()) {
    				new Notification("Inserisci un'unità di misura per l'obiettivo", NotificationType.ERROR).show();
    			}
    			else if(controller.getData() == null) {
    				new Notification("Scegli una data di raggiungimento per l'obiettivo", NotificationType.ERROR).show();
    			}
    			
        	}
    	});
        
        // apre il dialog e attende
        ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {  
        	String nome = controller.getNome();
        	String descrizione = controller.getDescrizione();
        	LocalDate data = controller.getData();
        	boolean isTipoAzione = controller.isTipoAzione();
        	String unità = controller.getUnità();
    		int valore = controller.getValore();
        	
        	if(nuovo) {
        		IObiettivo nuovoObiettivo;
        		if(isTipoAzione) {
        			nuovoObiettivo = new ObiettivoAzione(nome, descrizione, data, valore, unità);
        		} else {
        			nuovoObiettivo = new ObiettivoScomponibile(nome, descrizione, data);
        		}
        		if(isSottoObiettivo) {
        			((ObiettivoScomponibile) o).aggiungiSottoObiettivo(nuovoObiettivo);
        			new Notification("Nuovo sotto-obiettivo aggiunto.", NotificationType.SUCCESS).show();
        		} else {
        			gm.aggiungiObiettivo(nuovoObiettivo);
        			new Notification("Nuovo obiettivo aggiunto.", NotificationType.SUCCESS).show();
        		}
        	} else {
        			
    			// modifico l'obiettivo 
    			o.setNome(nome);
    			o.setDescrizione(descrizione);
    			o.setData(data);
        		if(o instanceof ObiettivoAzione)
        			((ObiettivoAzione) o).setUnita(unità);
        			((ObiettivoAzione) o).setValoreTotale(valore);
        		}
        		new Notification("Obiettivo modificato con successo.", NotificationType.SUCCESS).show();
        	}
            
        // Aggiornare la view
        this.aggiornaView();
    }
    
    /**
     * Apre l'editor delle azioni in aggiunta/modifica.
     */
    private void apriEditorAzione(IAzione azione, IObiettivo o, boolean nuovo) throws IOException {
    	
        // crea il modal
    	URL fileUrl = Main.class.getResource("/main/Views/GoalManager/EditorAzioni.fxml");
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(fileUrl);
    	AnchorPane editor = fxmlLoader.load();
        editor.getStylesheets().add(getClass().getResource("/main/Views/GoalManager/GoalManager.css").toExternalForm());
        editor.getStylesheets().add(getClass().getResource("/main/Globall.css").toExternalForm());  
    	Modal modal = new Modal(editor, "");
        
        // imposta titolo
        if(nuovo) {
        	modal.setTitolo("Nuova azione");
        } else {
        	modal.setTitolo("Modifica azione");
        }
        
        // Ottiene il controller e imposta l'azione se in modifica
        EditorAzioniController controller = fxmlLoader.getController();
        if(!nuovo) {
        	controller.setAzione(azione);
        }
        
        // valida gli input
        final HBox btnOk = modal.getBtnLookup(ButtonType.OK);
        btnOk.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
    		if(controller.getNome().isBlank() && controller.getData() == null){
    			event.consume();
    			if(controller.getNome().isBlank()) {
    				new Notification("Inserisci un nome per l'azione", NotificationType.ERROR).show();
    			} else if(controller.getData() == null) {
    				new Notification("Inserisci una data di inizio per l'azione", NotificationType.ERROR).show();
    			}
        	}
    	});
        
    	// apre il dialog e attende
        ButtonType btnCliccato = modal.show();
        if(btnCliccato == ButtonType.OK) {
        	String nome = controller.getNome();
        	int valore = controller.getValore();
        	LocalDate data = controller.getData();
        	List<Giorno> giorni = controller.getGiorni();
        	boolean isTipoSessione = controller.isTipoSessione();
        	int durata = controller.getDurata();
            
        	if(nuovo) {
        		IObiettivoAzione oa = (IObiettivoAzione) o;
        		
        		// aggiungo una nuova azione
        		IAzione nuovaAzione;
        		if(isTipoSessione) {
        			nuovaAzione = new AzioneSessione(nome, valore, data, giorni, durata);
        		} else {
        			nuovaAzione = new AzioneScomponibile(nome, valore, data, giorni);
        		}
        		oa.collegaAzione(nuovaAzione);
        		nuovaAzione.setObiettivo(oa);
        		new Notification("Azione collegata.", NotificationType.SUCCESS).show();
        		
        		// aggiorno la view
        		apriPaginaInfo(o);
        	} else {
        		
        		// modifico l'azione
    			azione.setNome(nome);
    			azione.setIncremento(valore);
    			azione.setDataInizio(data);
    			azione.setGiorniRipetizione(giorni);
        		if(azione instanceof AzioneSessione) {
        			((AzioneSessione) azione).setDurata(durata);
        		}
        		new Notification("Azione modificata.", NotificationType.SUCCESS).show();
        		
        		// aggiorno la view
        		apriPaginaInfo(azione.getObiettivo());
        	}
            
        }
    }
    
    /**
     * Elimina un obiettivo dal modello.
     */
    private void eliminaObiettivo(IObiettivo obiettivo) {
        boolean sottoObiettivo = obiettivo.getObiettivoPadre() != null;
    	
    	// elimina l'obiettivo dal modello
    	if(sottoObiettivo) { 
    		IObiettivoScomponibile obPadre = (IObiettivoScomponibile) obiettivo.getObiettivoPadre();
    		obPadre.eliminaSottoObiettivo(obiettivo.getId());
    		new Notification("Sotto-obiettivo eliminato.", NotificationType.INFO).show();
    	} else {
        	gm.eliminaObiettivo(obiettivo.getId());
        	new Notification("Obiettivo eliminato.", NotificationType.INFO).show();
    	}
    	
    	// aggiorna la view
    	aggiornaView();
    	
    }
    
    private void resettaPaginaInfo() {
    	paginaAzioni.setTop(null);
    	paginaAzioni.setCenter(null);
    	paginaAzioni.setBottom(null);
    }
    
    /**
     * Visualizza la lista completa delle azioni collegate ad un obiettivo.
     */
    private void apriPaginaInfo(IObiettivo o) {
        this.obiettivoCliccato = o;
        resettaPaginaInfo();
         
        // crea header
        VBox header = creaHeaderAzioniObiettivo(o);
        paginaAzioni.setTop(header);
        
        // aggiunge le azioni
        if(o instanceof ObiettivoAzione) {
        	IObiettivoAzione oa = (ObiettivoAzione) o;
            if(oa.getAzioni().size() > 0) {
        		// aggiunge le azioni alla view
                visualizzaTotaleAzioniObiettivo(oa);
            } else {
            	// crea messaggio
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                Label label1 = new Label("Non hai ancora collegato nessuna azione a questo obiettivo.");
                label1.getStyleClass().add("no-action-label");
                Label label2 = new Label("Nuova azione");
                label2.getStyleClass().add("new-action-btn");
                vBox.getChildren().add(label1);
                vBox.getChildren().add(label2);
                paginaAzioni.setCenter(vBox);
                
                // aggiunge evento aggiutna nuova azione
                label2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                    	try {
    						aggiungiAzione();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
                    }
                }); 
            }
        }
        
        // visualizza il progresso dell'obiettivo
        double progress = o.calcolaProgresso();
        VBox bar = creaProgressBar(progress);
        paginaAzioni.setBottom(bar);

    }
    
	public static VBox creaHeaderAzioniGiornaliere() {
		VBox header = new VBox();
		header.getStyleClass().add("header-azioni-giorno");
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        Label label = new Label("Tutte le azioni di oggi");
        label.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 18;");
        Label label2 = new Label("18/09/2021");
        label2.setStyle("-fx-text-fill: #888EA8; -fx-font-size: 20; -fx-font-weight: 800;");
        hBox.getChildren().add(label);
        hBox2.getChildren().add(label2);
        header.getChildren().add(hBox);
        header.getChildren().add(hBox2);
        return header;
	}
    
	/**
	 * Crea l'intestazione della pagina azioni.
	 */
	private VBox creaHeaderAzioniObiettivo(IObiettivo obiettivo) {
		
		// crea l'intestazione
        VBox header = new VBox();
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(15, 0, 0, 0));
        hBox2.setAlignment(Pos.CENTER);
        Label label3 = new Label("Visualizza tutte le azioni giornaliere");
        label3.getStyleClass().add("tutte-azioni-btn");
        Label label4 = new Label(obiettivo.getNome());
        label4.getStyleClass().add("nome-obiettivo");
        hBox.getChildren().add(label3);
        hBox2.getChildren().add(label4);
        header.getChildren().add(hBox);
        header.getChildren().add(hBox2);
        
        // collega evento visualizzazione totale azioni giornaliere
        label3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
            	
            	// rimuovo evidenziazione obiettivo cliccato
            	paneObiettivoCliccato.setStyle("-fx-background-color: #0E1726;");
            	
            	// visualizza il totale azioni giornaliere per tutti gli obiettivi
            	visualizzaAzioniGiornaliere();
            }
        }); 
        
        // crea la descrizione
        Label desc = new Label(obiettivo.getDescrizione());
        desc.getStyleClass().add("descrizione-label");
        header.getChildren().add(desc);
        
        if(obiettivo instanceof ObiettivoAzione) {
        	IObiettivoAzione o = (IObiettivoAzione) obiettivo;
        	
            // crea i pulsanti "Tutte" e "Oggi"
            if(o.getAzioni().size() > 0) {
                HBox hBox3 = new HBox();
                hBox3.setStyle("-fx-padding: 0 0 20 0;");
                hBox3.setAlignment(Pos.CENTER_LEFT);
                Label label5 = new Label("Tutte");
                label5.setStyle("-fx-text-fill: #2196F3;");
                Label label6 = new Label("Oggi");
                label5.getStyleClass().add("azioni-obiettivo-btn");
                label6.getStyleClass().add("azioni-obiettivo-btn");
                hBox3.getChildren().addAll(label5, label6);
                header.getChildren().add(hBox3);
                
                // collega evento visualizzazione azioni obiettivo giornaliere
                label5.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                    	label5.setStyle("-fx-text-fill: #2196F3;");
                    	label6.setStyle("-fx-text-fill: #ffffff;");
                    	visualizzaTotaleAzioniObiettivo(o);
                    }
                });
                
                // collega evento visualizzazione totale azioni obiettivo
                label6.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                    	label6.setStyle("-fx-text-fill: #2196F3;");
                    	label5.setStyle("-fx-text-fill: #ffffff;");
                    	visualizzaAzioniObiettivoGiornaliere(o);
                    }
                }); 
            }
        }
        return header;
	}
    
    /**
     * Visualizza la lista di azioni programmate per oggi di un obiettivo.
     */
    private void visualizzaAzioniObiettivoGiornaliere(IObiettivoAzione o) {
    	
    	// visualizza la lista di azioni giornaliere dell'obiettivo
    	List<IAzione> azioni = o.getAzioniGiornaliere(LocalDate.now());
    	if(azioni.size() > 0) {
        	VBox container = new VBox();
        	for(IAzione a : azioni) {
        		container.getChildren().add(creaViewAzione(a, true));
        	}
        	paginaAzioni.setCenter(container);
    	} else {
        	// aggiunge messaggio 
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            Label label1 = new Label("Nessuna azione da completare oggi per questo obiettivo.");
            label1.getStyleClass().add("no-action-label");
            vBox.getChildren().add(label1);
            paginaAzioni.setCenter(vBox);
    	}
    	
    }
    
    /**
     * Visualizza la lista totale delle azioni collegate all'obiettivo.
     */
    private void visualizzaTotaleAzioniObiettivo(IObiettivoAzione o) {
    	
    	// crea header
    	VBox header = this.creaHeaderAzioniObiettivo(o);
    	paginaAzioni.setTop(header);
        
        // visualizza la lista completa di azioni per l'obiettivo
    	List<IAzione> azioni = o.getAzioni();
    	VBox container = new VBox();
    	for(IAzione a : azioni) {
    		container.getChildren().add(creaViewAzione(a, false));
    	}
    	paginaAzioni.setCenter(container);
    	
    }
    
    /**
     * Visualizza tutte le azioni da completare oggi.
     */
    private void visualizzaAzioniGiornaliere() {

        // aggiunge header.
        VBox header = creaHeaderAzioniGiornaliere();
        paginaAzioni.setTop(header);
        
        // creo la lista azioni giornaliere
        List<IAzione> azioni = gm.calcolaAzioniGiornaliere(LocalDate.now());
        if(azioni.size() > 0) {
        	VBox containerAzioni = new VBox();
        	
            // creo sezione azioni ancora da svolgere
            Label label = new Label("Da fare:");
            label.getStyleClass().add("header-label");
            VBox box1 = new VBox();
            box1.getChildren().add(label);
            
            // creo sezione azioni completate
            Label label2 = new Label("Completate:");
            label2.getStyleClass().add("header-label");
            VBox box2 = new VBox();
            box2.setPadding(new Insets(60, 0, 0, 0));
            box2.getChildren().add(label2);
            
            // visualizza la lista di azioni di oggi
            int completate = 0;
            for(IAzione a : azioni) {
            	if(a.getCompletata()) {
            		completate++;
            		box2.getChildren().add(creaViewAzione(a, true));
            	} else {
            		box1.getChildren().add(creaViewAzione(a, true));
            	}
            }
            containerAzioni.getChildren().addAll(box1, box2);
            paginaAzioni.setCenter(containerAzioni);
            
            // visualizza la barra di progresso
            double progress = completate / azioni.size();
            VBox bar = creaProgressBar(progress);
            paginaAzioni.setBottom(bar);
            
        } else {
        	
        	// aggiunge messaggio 
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            Label label1 = new Label("Nessuna azione da completare.");
            label1.getStyleClass().add("no-action-label");
            vBox.getChildren().add(label1);
            paginaAzioni.setCenter(vBox);
        }
    }
    
    private VBox creaProgressBar(double progress) {
    	VBox vBox = new VBox();
    	Label label = new Label("Progresso giornaliero:");
    	label.getStyleClass().add("progress-title");
    	HBox box = new HBox();
    	box.setAlignment(Pos.CENTER_LEFT);
    	Label perc = new Label(Math.round(progress * 100.0) / 100.0 + "%");
    	perc.getStyleClass().add("progress-label");
    	ProgressBar bar = new ProgressBar(progress + 0.01);
    	bar.getStyleClass().add("progress-bar");
    	HBox.setHgrow(bar, Priority.ALWAYS);
    	bar.setMaxWidth(Double.MAX_VALUE);
    	box.getChildren().addAll(bar, perc);
    	vBox.getChildren().addAll(label, box);
    	return vBox;
    }
    
    /**
     * Elimina un'azione dal modello.
     */
    private void eliminaAzione(IAzione azione) {
    	azione.getObiettivo().eliminaAzione(azione.getId());
    	this.apriPaginaInfo(azione.getObiettivo());
    	new Notification("Azione eliminata.", NotificationType.INFO).show();  	
    }
    
    private CheckBox creaCheckbox(String s, boolean completata) {
        CheckBox check = new CheckBox();
        check.setText(s);
        check.setSelected(completata);
        return check;
    }
    
    /**
     * Crea la view di una singola azione.
     */
    private BorderPane creaViewAzione(IAzione azione, boolean todo) {
    	
    	// crea il container
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("azione"); 
        
        if(todo) {
        	VBox container = new VBox();
        	pane.setLeft(container);
        	
        	if(azione instanceof AzioneScomponibile) {
        		IAzioneScomponibile as = (IAzioneScomponibile) azione;
        		
        		// crea checkbox
                CheckBox azioneCheck = creaCheckbox(azione.getNome(), azione.getCompletata());
                azioneCheck.getStyleClass().add("nome");
                container.getChildren().add(azioneCheck);
                
                // collega evento click checkbox azione
                azioneCheck.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                    	t.consume();
                        completaAzione(azione);
                        if(azione.getCompletata()) {
                        	new Notification("Azione completata.", NotificationType.SUCCESS).show();
                        }
                    }
                });
                
                // aggiunge gli item dell'azione scomponibile
                if(as.getItems().size() > 0) {
                	VBox itemContainer = new VBox();
                	container.getChildren().add(itemContainer);
                	HBox hBox = Helper.creaBtnAggiunta("Item");
                	itemContainer.getChildren().add(hBox); 
                	for(Item item : as.getItems()) {
                		CheckBox itemCheck = creaCheckbox(item.getNome(), item.getCompletato());
                		itemContainer.getChildren().add(itemCheck);
                		
                        // collega evento click checkbox azione
                        azioneCheck.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent t) {
                            	t.consume();
                                item.completa();
                                itemCheck.setSelected(true);
                            }
                        });
                        
                        // collega evento aggiunta item
                        hBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent t) {
                            	System.out.println("Aggiunta item");
                            }
                        });
                	}
                }
        	} else if(azione instanceof AzioneSessione) {
        		
        	}
        } else {
        	pane.setLeft(Helper.creaElementoLista(azione.getNome()));
        }
        
        // crea pulsante menu azione
        HBox menuBtn = this.creaBtnMenu(azione);
        pane.setRight(menuBtn);
        
        return pane;
    }
    
    private void completaAzione(IAzione a) {
    	
    	// comunica con il modello
    	a.completa();
    	
    	// aggiorna view
    	obiettivoCliccato = null;
    	aggiornaView();
    }
    
    //------------------------------ METODI FXML ----------------------------------
    /**
     * Collega un'azione all'obiettivo cliccato.
     */
    @FXML
    private void aggiungiAzione() throws IOException {
        apriEditorAzione(null, obiettivoCliccato, true);
    }
    
    /**
     * Aggiunge un nuovo obiettivo.
     */
    @FXML
    private void aggiungiObiettivo() throws IOException {
        apriEditorObiettivo(null, true, false);
    }
    
    /**
     * Aggiunge un nuovo sotto-obiettivo all'obiettivo cliccato.
     */
    private void aggiungiSottoObiettivo(IObiettivo obiettivo) throws IOException {
    	apriEditorObiettivo(obiettivo, true, true);
    }
    
    //---------------------------- METODI PUBBLICI --------------------------------
    /**
     * Imposta l'istanza del GoalManager.
     */
    public void setGoalManager(IGoalManager gm) {
    	this.gm = gm;
    	aggiornaView();
    	visualizzaAzioniGiornaliere();
    }
    
}
