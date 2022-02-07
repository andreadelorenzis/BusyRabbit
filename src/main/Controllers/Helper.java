package main.Controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;

public class Helper {
	public static Image dotsIcon = new Image(Main.class.getResource("/main/risorse/dots.png").toString());
	public static Image plusIcon = new Image(Main.class.getResource("/main/risorse/plus.png").toString());
	public static Image closeIcon = new Image(Main.class.getResource("/main/risorse/close.png").toString());

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
}
