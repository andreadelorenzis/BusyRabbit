package main.views;

import java.net.URL;

import javafx.scene.image.Image;
import main.Main;

/**
 * Classe modulo che contiene servizi per accedere a tutte le risorse nell'applicazione
 */
public class LoaderRisorse {
	
	/* PATH immagini */
	public static final String PATH_IMGS = "/main/views/risorse/";
	
	/* PATH CSS globale */
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
	
	/**
	 * Metodo per cercare un'immagine
	 * 
	 * @param imgName
	 * @return Image
	 */
	public static Image getImg(String imgName) {
		return new Image(Main.class.getResource(PATH_IMGS + imgName).toString());
	}
	
	/**
	 * Metodo per cercare un file CSS
	 * 
	 * @param package
	 * @param file
	 * @return file CSS
	 */
	public static String getCSS(String sezione, String file) {
		return Main.class.getResource("/main/views/" + sezione + "/risorse/" + file + ".css").toExternalForm();
	}
	
	/**
	 * Metodo per cercare un file FXML
	 * 
	 * @param package
	 * @param file
	 * @return file FXML
	 */
	public static URL getFXML(String sezione, String file) {
		return Main.class.getResource("/main/views/" + sezione + "/risorse/" + file + ".fxml");
	}
	
}
