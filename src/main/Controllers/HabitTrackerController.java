/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers;



import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import main.FxmlLoader;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import static javax.swing.Spring.width;
import main.FxmlLoader;
import main.Main;


/**
 *
 * @author Bit
 */
public class HabitTrackerController {
    
   /* HabitTracker  */
    
    private Stage stage;
    private Scene scene;
  
    @FXML
    private HBox addDaily;
    @FXML
    private HBox addhabit;
    @FXML 
    private BorderPane panevc;
    
   
    @FXML
    public void SwitchAll(MouseEvent event ) throws IOException {
      
    FxmlLoader object = new FxmlLoader();
    URL fileUrl = Main.class.getResource("/main/Views/HabitTracker/HabitDaily.fxml");
    Pane view = object.getPage(fileUrl);
    panevc.setCenter(view);
  }
    @FXML
    public void SwitchToDo(MouseEvent event ) throws IOException {
      
    FxmlLoader object = new FxmlLoader();
    URL fileUrl = Main.class.getResource("/main/Views/HabitTracker/HabitDaily.fxml");
    Pane view = object.getPage(fileUrl);
    panevc.setCenter(view);
  }
    @FXML
    public void SwitchDone(MouseEvent event ) throws IOException {
      
    FxmlLoader object = new FxmlLoader();
    URL fileUrl = Main.class.getResource("/main/Views/HabitTracker/HabitDaily.fxml");
    Pane view = object.getPage(fileUrl);
    panevc.setCenter(view);
    }
    
    @FXML
    public void SwitchDailyAdd(MouseEvent event ) throws IOException {
      
    Parent root = FXMLLoader.load(getClass().getResource("/main/Views/HabitDaily.fxml"));  
    stage = new Stage();
    scene = new Scene(root);
    stage.setTitle("BusyRabbit");
    Image icon = new Image("/src/logo2.png");
    stage.getIcons().add(icon);
    stage.setScene(scene);
    stage.setY(220);
    stage.setX(720);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(addDaily.getScene().getWindow());
    stage.show();
    }
    
     @FXML
    public void SwitchHabitAdd(MouseEvent event ) throws IOException {
      
    Parent root = FXMLLoader.load(getClass().getResource("/main/Views/HabitHabit.fxml"));  
    stage = new Stage();
    scene = new Scene(root);
    stage.setTitle("BusyRabbit");
    Image icon = new Image("/src/logo2.png");
    stage.getIcons().add(icon);
    stage.setScene(scene);
    stage.setY(220);
    stage.setX(720);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(addhabit.getScene().getWindow());
    stage.show();
    
      }
    }
