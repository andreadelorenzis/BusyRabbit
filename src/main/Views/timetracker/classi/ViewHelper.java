package main.views.timetracker.classi;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IProgetto;
import main.views.LoaderRisorse;

public class ViewHelper {
	
	public static BorderPane creaPaneProgetto(IProgetto progetto) {
		
		// crea contenitore progetto
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("lista-progetti-elem");
        pane.setPadding(new Insets(0, 20, 0, 10));
        pane.setMinHeight(60);
       
        // crea nome e pallino progetto nella parte sinistra
        HBox nome = new HBox();
        nome.setAlignment(Pos.CENTER);
        Label label1 = creaLabelProgetto(progetto);
        nome.getChildren().add(label1);
        pane.setLeft(nome);
        
        return pane;
	}
	
    /**
     * Crea il label colorato del progetto.
     */
    public static Label creaLabelProgetto(IProgetto p) {
        Label label = new Label(p.getNome());
        label.getStyleClass().add("label-progetto");
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setFill(Color.web(p.getColore().hex));
        label.setGraphic(circle);
        label.setStyle("-fx-text-fill: " + p.getColore().hex + ";");
        return label;
    }
    
    /**
     * Crea l'header di un giorno di attività.
     */
    public static BorderPane creaHeaderGiorno(List<IAttività> giorno) {
    	
    	// data e durata totale del giorno
    	String durata = formattaOrologio((int) calcolaTotaleGiorno(giorno));
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");
    	String data = giorno.get(0).getData().format(formatter);
        
        // crea un container
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header-giorno");
        header.setPadding(new Insets(0, 20, 0, 20));
        
        // crea parte destra del BorderPane.
        HBox headerDestra = new HBox();
        headerDestra.setAlignment(Pos.CENTER);
        Label label1 = new Label("Total:");
        label1.getStyleClass().add("header-total");
        headerDestra.getChildren().add(label1);     
        Label label2 = new Label(durata);
        label2.getStyleClass().add("header-durata");
        label2.setPadding(new Insets(0, 20, 0, 10));
        headerDestra.getChildren().add(label2);
        header.setRight(headerDestra);
        
        // Crea parte sinistra del BorderPane.
        HBox headerSinistra = new HBox();
        headerSinistra.setAlignment(Pos.CENTER);
        Label label3 = new Label(data);
        label3.getStyleClass().add("header-data");
        headerSinistra.getChildren().add(label3);
        header.setLeft(headerSinistra);
        
        return header;
    }

    
    /**
     * Calcola il tempo totale misurato in un giorno.
     */
    public static long calcolaTotaleGiorno(List<IAttività> giorno) {
    	long durata = 0;
    	for(IAttività a : giorno) {
    		durata += a.getDurata();
    	}
    	return durata;
    }
    
    /**
     * Crea una stringa che mostra ore, minuti e secondi a partire da una durata in secondi.
     */
    public static String formattaOrologio(int durata) {
    	int[] params = scomponiDurata(durata);
    	String sOre = formattaDurata(params[0]);
    	String sMinuti = formattaDurata(params[1]);
    	String sSecondi = formattaDurata(params[2]);
    	return sOre + ":" + sMinuti + ":" + sSecondi;
    }
    
    /**
     * Scompone la durata (sec) in ore, minuti e secondi, in quest'ordine
     */
    public static int[] scomponiDurata(int durata) {
    	int ore = durata / 3600;
    	int minuti = (durata / 60) % 60;
    	int secondi = (durata / 1) % 60;
    	
    	return new int[] {ore, minuti, secondi};
    }
    
    /**
     * Trasforma il numero (ore, minuti o secondi) in una stringa in formato 00.
     */
    public static String formattaDurata(int durata) {
    	String s = "";
    	if(durata < 10) {
    		s += "0";
    	}
    	s += durata;
    	return s;
    }
    
    public static ImageView creaArrow(boolean left) {
        ImageView arrow = new ImageView();
        arrow.setFitHeight(15);
        arrow.setFitWidth(15);
        if(left) {
        	arrow.setRotate(-90);
        	arrow.getStyleClass().add("left-arrow");
        } else {
        	arrow.setRotate(90);
        	arrow.getStyleClass().add("right-arrow");
        }
        arrow.setImage(LoaderRisorse.getImg("arrow.png"));
        BorderPane.setAlignment(arrow, Pos.CENTER);
        return arrow;
    }
    
    public static Label creaLabelPageBtn(int pagina, int numGiorni) {
    	
    	// crea stringa testo
    	String testo = "";
    	if(pagina == 1) {
    		testo = "1-10 di " + numGiorni;
    	} else {
    		int max = 0;
    		if(pagina * 10 >= numGiorni) {
    			max = numGiorni;
    		} else {
    			max = pagina * 10;
    		}
    		testo = (pagina - 1) * 10 + "-" + max + " di " + numGiorni;
    	}
    	
    	// crea label
        Label label = new Label(testo);
        label.setPadding(new Insets(12, 15, 12, 15));
        label.setStyle("-fx-text-fill: #ffffff; -fx-border-color: #191E3A; -fx-border-width: 0;");
        
        return label;
    }
    
}
