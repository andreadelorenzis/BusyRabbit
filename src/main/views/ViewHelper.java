package main.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe modulo che contiene servizi utilizzabili da tutte le classi di tipo View
 */
public class ViewHelper {
	public static Image dotsIcon = LoaderRisorse.getImg("dots.png");
	public static Image plusIcon = LoaderRisorse.getImg("plus.png");
	public static Image closeIcon = LoaderRisorse.getImg("close.png");

    /**
     * Crea pulsante di aggiunta
     */
    public static HBox creaBtnAggiunta(String s) {
        HBox box = new HBox();
        box.getStyleClass().add("add-btn");
        ImageView img = new ImageView();
        img.setFitHeight(18);
        img.setFitWidth(18);
        img.setImage(plusIcon);
        Label label = new Label(s);
        label.getStyleClass().add("add-btn-label");
        box.getChildren().add(img);
        box.getChildren().add(label);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(5, 5, 5, 0));
        box.setMaxWidth(200);
        return box;
    }
    
    public static HBox creaIconaChiusura() {
    	HBox box = new HBox();
        box.getStyleClass().add("close-btn");
        ImageView closeImg = new ImageView();
        closeImg.setFitHeight(18);
        closeImg.setFitWidth(18);
        closeImg.setImage(closeIcon);
        box.getChildren().add(closeImg);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 5, 5, 5));
        HBox.setMargin(box, new Insets(0, 5, 0, 0));
        return box;
    }
    
    public static HBox creaBtnEdit() {
    	HBox box = new HBox();
        box.getStyleClass().add("edit-btn");
        ImageView dots = new ImageView();
        dots.setFitHeight(18);
        dots.setFitWidth(6);
        dots.setImage(dotsIcon);
        box.getChildren().add(dots);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 5, 5, 5));
        HBox.setMargin(box, new Insets(0, 5, 0, 0));
        return box;
    }
    
    public static HBox creaElementoLista(String s) {
    	HBox box = new HBox();
    	box.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(s);
        label.getStyleClass().add("elemento-lista");
        label.setPadding(new Insets(0, 0, 0, 10));
        Circle circle = new Circle();
        circle.setRadius(4);
        circle.setFill(Color.web("#BAC4CA"));
        box.getChildren().addAll(circle, label);
        return box;
    }
    
    /**
     * Usato per rendere un'immagine un pulsante cliccabile
     */
    public static HBox creaBtn(Image img, int dim) {
    	HBox box = new HBox();
        ImageView imgView = new ImageView();
        imgView.setFitHeight(dim);
        imgView.setFitWidth(dim);
        imgView.setImage(img);
        box.getChildren().add(imgView);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 5, 5, 5));
        HBox.setMargin(box, new Insets(0, 5, 0, 0));
        box.setStyle("-fx-cursor: hand;");
        return box;
    }
}
