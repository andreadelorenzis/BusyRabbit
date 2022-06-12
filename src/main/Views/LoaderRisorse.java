package main.Views;

import java.net.URL;

import javafx.scene.image.Image;
import main.Main;

public class LoaderRisorse {
	
	/* PATH immagini */
	public static final String PATH_IMGS = "/main/views/risorse/";
	public static final String globalCss = Main.class.getResource("/main/views/Globall.css").toExternalForm();
	
	/* packages */
	public static final String TT = "timetracker";
	public static final String HT = "habittracker";
	public static final String GM = "goalmanager";
	public static final String AM = "accountmanager";
	public static final String DASHBOARD = "dashboard";
	public static final String IMPOSTAZIONI = "impostazioni";
	public static final String MODAL = "modal";
	public static final String NOTIFICATION = "notification";
	
    /*
     * Risorse fxml
     */
	/*
    public static final URL timeTracker = Main.class.getResource(PATH_RISORSE_TT + "TimeTracker.fxml");
    public static final URL editorAttività = Main.class.getResource(PATH_RISORSE_TT + "EditorAttività.fxml");
    public static final URL editorTimer = Main.class.getResource(PATH_RISORSE_TT + "ImpostazioniTimer.fxml");
    public static final URL editorProgetti = Main.class.getResource(PATH_RISORSE_TT + "EditorProgetto.fxml");
    public static final URL goalManager = Main.class.getResource("/main/Views/GoalManager/resources/GoalManager.fxml");
    public static final URL editorObiettivi = Main.class.getResource("/main/Views/GoalManager/resources/EditorObiettivi.fxml");
    public static final URL editorAzioni = Main.class.getResource("/main/Views/GoalManager/resources/EditorAzioni.fxml");
    public static final URL habitTracker = Main.class.getResource("/main/Views/HabitTracker/resources/HabitTracker.fxml");
    public static final URL editorAbitudine = Main.class.getResource("/main/Views/HabitTracker/resources/EditorAbitudine.fxml");
    public static final URL impostazioni = Main.class.getResource("/main/Views/Impostazioni/resources/Impostazioni.fxml");
    public static final URL reportTempo = Main.class.getResource("/main/Views/Dashboard/resources/ReportTempo.fxml");
    public static final URL reportAbitudini = Main.class.getResource("/main/Views/Dashboard/resources/ReportAbitudini.fxml");
    public static final URL login = Main.class.getResource("/main/Views/Account/resources/Login.fxml");
    public static final URL registrazione = Main.class.getResource("/main/Views/Account/resources/Registrazione.fxml");
    public static final URL app = Main.class.getResource("/main/Views/App/resources/App.fxml");
    */
    /*
     * Risorse css
     */
	/*
    public static final String timeTrackerCss = Main.class.getResource("/main/Views/TimeTracker/resources/TimeTracker.css").toExternalForm();
    public static final String goalManagerCss = Main.class.getResource("/main/Views/GoalManager/resources/GoalManager.css").toExternalForm();
    public static final String habitTrackerCss = Main.class.getResource("/main/Views/HabitTracker/resources/HabitTracker.css").toExternalForm();
    public static final String impostazioniCss = Main.class.getResource("/main/Views/Impostazioni/resources/Impostazioni.css").toExternalForm();
    public static final String reportTempoCss = Main.class.getResource("/main/Views/Dashboard/resources/ReportTempo.css").toExternalForm();
    public static final String reportAbitudiniCss = Main.class.getResource("/main/Views/Dashboard/resources/ReportAbitudini.css").toExternalForm();
    public static final String dashboardCss = Main.class.getResource("/main/Views/Dashboard/resources/Dashboard.css").toExternalForm();
    public static final String modalCss = Main.class.getResource("/main/Views/Modals/resources/Modal.css").toExternalForm();
    public static final String globalCss = Main.class.getResource("/main/Views/Globall.css").toExternalForm();
    */
    /*
     * Immagini
     */
	/*
    public static final Image dashboardImg = new Image(Main.class.getResource(PATH_IMGS + "dashboard.png").toString());
    public static final Image clockImg = new Image(Main.class.getResource(PATH_IMGS + "clock.png").toString());
    public static final Image dartsImg = new Image(Main.class.getResource(PATH_IMGS + "darts.png").toString());
    public static final Image refreshImg = new Image(Main.class.getResource(PATH_IMGS + "refresh.png").toString());
    public static final Image tilesImg = new Image(Main.class.getResource(PATH_IMGS + "dashboard.png").toString());
    public static final Image arrowDownImg = new Image(Main.class.getResource(PATH_IMGS + "arrow-down.png").toString());
    public static final Image arrowDownWhiteImg = new Image(Main.class.getResource(PATH_IMGS + "arrow-down-white.png").toString());
    public static final Image settingsImg = new Image(Main.class.getResource(PATH_IMGS + "settings.png").toString());
	public static final Image dotsIcon = new Image(Main.class.getResource(PATH_IMGS + "dots.png").toString());
	public static final Image plusIcon = new Image(Main.class.getResource(PATH_IMGS + "plus.png").toString());
	public static final Image arrowIcon = new Image(Main.class.getResource(PATH_IMGS + "arrow.png").toString());
	public static final Image close = new Image(Main.class.getResource(PATH_IMGS + "close.png").toString());
	public static final Image closeRed = new Image(Main.class.getResource(PATH_IMGS + "close-red.png").toString());
	public static final Image closeGreen = new Image(Main.class.getResource(PATH_IMGS + "close-green.png").toString());
	public static final Image closeYellow = new Image(Main.class.getResource(PATH_IMGS + "close-yellow.png").toString());
	public static final Image logoImg = new Image(Main.class.getResource(PATH_IMGS + "logo.png").toString());
	*/
	
	public static Image getImg(String imgName) {
		return new Image(Main.class.getResource(PATH_IMGS + imgName).toString());
	}
	
	public static String getCSS(String sezione, String file) {
		return Main.class.getResource("/main/views/" + sezione + "/risorse/" + file + ".css").toExternalForm();
	}
	
	public static URL getFXML(String sezione, String file) {
		return Main.class.getResource("/main/views/" + sezione + "/risorse/" + file + ".fxml");
	}
	
}
