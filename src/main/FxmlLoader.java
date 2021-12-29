package main;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public class FxmlLoader {
    private Pane view;
    
    public Pane getPage(URL fileUrl) {
        try {
            if(fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No page " + fileUrl.getPath() + " found.");
        }
        return view;
    }
}
