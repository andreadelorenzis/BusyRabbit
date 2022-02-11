package main.Controllers.HabitTracker;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import main.Controllers.Helpers.Helper;
import main.Views.HabitTracker.interfaces.HabitTrackerView;
import main.Views.Modals.Modal;
import main.Views.Notifications.Notification;
import main.Views.Notifications.NotificationType;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.habittracker.classes.SessionHabit;
import main.Models.habittracker.classes.SimpleHabit;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.habittracker.interfaces.ISessionHabit;

public class HabitTrackerControllerImpl implements HabitTrackerController {
    
	private HabitTrackerView view;
	
	private IHabitTracker ht;
	
	public HabitTrackerControllerImpl(HabitTrackerView view) {
		this.view = view;
		ht = HabitTracker.getInstance();
	}
	
	public void aggiungiAbitudine(IHabit h) {
		ht.addHabit(h);
	}
	
	public void modificaAbitudine(IHabit h1, IHabit h2) {
		String nome = h2.getName();
		String descrizione = h2.getDescription();
		LocalDate data = h2.getStartDate();
		List<DayOfWeek> giorni = h2.getDays();
		String id = h2.getId();
		
		h1.setName(nome);
		h1.setDescription(descrizione);
		h1.setStartDate(data);
		h1.setDays(giorni);
	}
	
	public void eliminaAbitudine(IHabit h) {
		ht.removeHabit(h.getId());
	}
	
	public void completaAbitudine(IHabit h) {
		h.complete();
	}
	
}
