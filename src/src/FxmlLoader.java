package src;






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
    
    public Pane getPage(String fileName) {
        System.out.print(("Il file è " + fileName + ".fxml"));
        try {
            URL fileUrl = Main.class.getResource("/main/Views/" + fileName + ".fxml");
            if(fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            System.out.print(fileUrl);
            
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No page " + fileName + " found.");
        }
        return view;
    }
}
