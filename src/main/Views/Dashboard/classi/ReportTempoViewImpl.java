package main.views.dashboard.classi;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.model.timetracker.classi.Progetto;
import main.model.timetracker.classi.TimeTracker;
import main.model.timetracker.interfacce.IProgetto;
import main.views.dashboard.interfacce.ReportTempoView;
import main.views.timetracker.classi.ViewHelper;

public class ReportTempoViewImpl implements Initializable, ReportTempoView {
    
	//-------------------------------- CAMPI FXML -----------------------------------
    @FXML
    private StackedBarChart<String, Double> stackedChart;
    @FXML
    private CategoryAxis mesiX;
    @FXML
    private NumberAxis mesiY;
    @FXML
    private HBox pieChartContainer;
    @FXML
    private VBox progressBarsContainer;
    @FXML
    private Spinner<Integer> annoSpinner;
    @FXML
    private ChoiceBox<String> meseChoice;
    
	//--------------------------------- CAMPI ------------------------------------
    /*
     * L'anno per cui visualizzare le statistiche sul tempo.
     */
    private int annoSelezionato = LocalDate.now().getYear();
    
    /*
     * Il mese per cui visualizzare le statistiche sul tempo.
     */
    private String meseSelezionato = LocalDate.now().getMonth().toString();
    
    //--------------------------- METODI PUBBLICI --------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	// inizializza choice box mese
        ObservableList<String> mesi = this.meseChoice.getItems();
        mesi.addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", "ALL");
        meseChoice.setValue("ALL");
        
        // aggiunge listener per cambiamento mese
        meseChoice.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                	 
                	// visualizza dati mensili
                    meseSelezionato = (String) meseChoice.getItems().get((Integer) new_val);
                    visualizzaStackedBarChart(false);
                    visualizzaPieChart(false);
                    visualizzaProgressBars(false);
                    
                    if((String) meseChoice.getItems().get((Integer) new_val) == "ALL") {
                    	
                    	// visualizza dati annuali
                    	visualizzaStackedBarChart(true);
                        visualizzaPieChart(true);
                        visualizzaProgressBars(true);
                    }
        });
        
        // inizializza spinner anno
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2021, 2100);
        valueFactory1.setValue(LocalDate.now().getYear());
        this.annoSpinner.setValueFactory(valueFactory1); 
        
        // aggiunge listener cambiamento anno
        this.annoSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                annoSelezionato = (int) annoSpinner.getValue();
                meseChoice.setValue("ALL");
                visualizzaStackedBarChart(true);
                visualizzaPieChart(true);
                visualizzaProgressBars(true);
            }
        });
        
        this.visualizzaStackedBarChart(true);
        this.visualizzaPieChart(true);
        this.visualizzaProgressBars(true);
    }
    
    //--------------------------- METODI PRIVATI --------------------------------
    /**
     * Calcola i dati da inserire nel grafico a barre annuale.
     */
    private ArrayList<XYChart.Series<String, Double>> calcolaTempoAnno() {
        ArrayList<XYChart.Series<String, Double>> datiAnno = new ArrayList<XYChart.Series<String, Double>>();
        for(IProgetto progetto : TimeTracker.getInstance().getProgetti()) {
        	if(!isProgettoVuoto(progetto, true)) {
                XYChart.Series<String, Double> tempiProgetto = new XYChart.Series<>();
                tempiProgetto.setName(progetto.getNome());
                for(int i = 1; i <= 12; i++) {
             	   double tempo = 0;
             	   String mese = Month.of(i).toString().substring(0, 3);
     			   if(progetto.getAnnoProgetto(annoSelezionato) != null && progetto.getAnnoProgetto(annoSelezionato).containsKey(i)) {
     		    		   tempo = progetto.getAnnoProgetto(annoSelezionato).get(i);
     		       }
             	   tempiProgetto.getData().add(new XYChart.Data<>(mese, tempo));
                }
                datiAnno.add(tempiProgetto);
        	}
        }
        return datiAnno;
    }
    
    /**
     * Calcola i dati da inserire nel grafico a barre mensile.
     */
    private ArrayList<XYChart.Series<String, Double>> calcolaTempoMese() {
        ArrayList<XYChart.Series<String, Double>> datiMese = new ArrayList<XYChart.Series<String, Double>>();
        for(IProgetto progetto : TimeTracker.getInstance().getProgetti()) {
        	if(!isProgettoVuoto(progetto, false)) {
                XYChart.Series<String, Double> tempiProgetto = new XYChart.Series<>();
                tempiProgetto.setName(progetto.getNome());
                
                // ottengo tutti i giorni di questo mese
                YearMonth ym = YearMonth.of(annoSelezionato, Month.valueOf(meseSelezionato));
                LocalDate primoDelMese = ym.atDay(1);
                LocalDate primoDelMeseDopo = ym.plusMonths(1).atDay(1);
                
                // aggiungo ogni giorno del progetto di questo mese
                primoDelMese.datesUntil(primoDelMeseDopo).forEach(date -> {
                	double tempo = 0;
                	int giorno = date.getDayOfMonth();
                	if(progetto.getMeseProgetto(annoSelezionato, Month.valueOf(meseSelezionato)).containsKey(giorno)) {
                		tempo = progetto.getMeseProgetto(annoSelezionato, Month.valueOf(meseSelezionato)).get(giorno);
                	}
                	tempiProgetto.getData().add(new XYChart.Data<>(giorno + "", tempo));
                });
                
                datiMese.add(tempiProgetto);
        	}
        }
        return datiMese;
    }
    
    /**
     * Visualizza il grafico a barre.
     */
    private void visualizzaStackedBarChart(boolean isAnno) {
        this.stackedChart.getData().clear();
        ArrayList<XYChart.Series<String, Double>> dati;
        if(isAnno) {
        	dati = calcolaTempoAnno();
        } else {
        	dati = calcolaTempoMese();
        }
        
        // aggiungo i dati al grafico a barre
        for(int i = 0; i < dati.size(); i++) {
            this.stackedChart.getData().add(dati.get(i));
        }
        
        // aggiungo i colori alle sezione del grafico a torta
        List<IProgetto> progettiMisurati = TimeTracker.getInstance().getProgetti().stream()
        												   .filter(p -> !(isProgettoVuoto(p, isAnno)))
        												   .collect(Collectors.toList());
        
        for(int i = 0; i < dati.size(); i++) {
	    	stackedChart.getStyleClass().add(progettiMisurati.get(i).getColore().toString() + i);
        }
        
    }
    
    /**
     * Calcola i dati (annuali o mensili) da inserire nel grafico a torta.
     */
    private ObservableList<PieChart.Data> calcolaTempiTotaliProgetti(boolean anno) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for(IProgetto progetto : TimeTracker.getInstance().getProgetti()) {
        	if(!isProgettoVuoto(progetto, anno)) {
                long tempoTotale = this.calcolaTempoTotaleProgetto(progetto, anno);
                pieData.add(new PieChart.Data(progetto.getNome(), tempoTotale));
        	}
        }
        return pieData;
    }
    
    /**
     * Visualizza il grafico a torta.
     */
    private void visualizzaPieChart(boolean isAnno) {
        this.pieChartContainer.getChildren().clear();
        
        // calcolo i tempi
        ObservableList<PieChart.Data> pieData = this.calcolaTempiTotaliProgetti(isAnno);
        
        // creo il grafico a torta
        PieChart chart = new PieChart(pieData); 
        chart.getStyleClass().add("pie-chart");
        chart.setLabelLineLength(10);
        chart.setLabelsVisible(false);
        chart.setLegendVisible(false);
        chart.setMinHeight(550);
        chart.setMinWidth(550);
        chart.setTranslateX(-50);
        
        // aggiungo i colori alle sezione del grafico a torta
        List<IProgetto> progettiMisurati = TimeTracker.getInstance().getProgetti().stream()
        												   .filter(p -> !(isProgettoVuoto(p, isAnno)))
        												   .collect(Collectors.toList());
        
        for(int i = 0; i < pieData.size(); i++) {
	        chart.getStyleClass().add(progettiMisurati.get(i).getColore().toString() + i);
        } 
        
        // creo tooltip percentuale
        double total = 0;
        for(final PieChart.Data data : chart.getData()) {
        	total += data.getPieValue();
        }
        for (final PieChart.Data data : chart.getData()) {
        	String percentage = String.format("%2.2f%%", (data.getPieValue() / total) * 100);
        	Tooltip tooltipo = new Tooltip(percentage);
        	Tooltip.install(data.getNode(), tooltipo);
        }
        
        this.pieChartContainer.setAlignment(Pos.TOP_CENTER);
        this.pieChartContainer.getChildren().add(chart);
    }
    
    /**
     * Calcola il tempo totale misurato per un progetto in un anno o mese.
     */
    private long calcolaTempoTotaleProgetto(IProgetto progetto, boolean isAnno) {
        long tempoTotale = 0;
        if(isAnno) {
            for(int mese : progetto.getAnnoProgetto(annoSelezionato).keySet()) {
                tempoTotale += progetto.getAnnoProgetto(annoSelezionato).get(mese);
            }
        } else {
            for(int giorno : progetto.getMeseProgetto(annoSelezionato, Month.valueOf(meseSelezionato)).keySet()) {
                tempoTotale += progetto.getMeseProgetto(annoSelezionato, Month.valueOf(meseSelezionato)).get(giorno);
            }
        }
        return tempoTotale;
    }
    
    /**
     * Visualizza le progress bars relative ad un anno o mese di monitoraggio.
     */
    private void visualizzaProgressBars(boolean isAnno) {
    	this.progressBarsContainer.getChildren().clear();
    	
    	// fa la somma dei tempi misurati nel periodo
        long tempoTotale = 0;
        for(IProgetto p : TimeTracker.getInstance().getProgetti()) {
            tempoTotale += this.calcolaTempoTotaleProgetto(p, isAnno);
        }
        
        List<IProgetto> progettiSorted = TimeTracker.getInstance().getProgetti().stream()
        												 .map(p -> (Progetto) p)
        												 .sorted(new Comparator<Progetto>() {
															@Override
															public int compare(Progetto p1, Progetto p2) {
																long durataP1 = calcolaTempoTotaleProgetto(p1, isAnno);
																long durataP2 = calcolaTempoTotaleProgetto(p2, isAnno);
																if(durataP1 <= durataP2) {
																	return 1;
																} else {
																	return -1;
																}
															} 
        												 })
        												 .collect(Collectors.toList());
        
        // crea le progress bars per ogni progetto
        for(IProgetto progetto : progettiSorted) {
        	if(!isProgettoVuoto(progetto, isAnno)) {
        		long durata = this.calcolaTempoTotaleProgetto(progetto, isAnno);
        		
            	// crea il container
                String stile = "-fx-text-fill: #BAC4CA; -fx-font-size: 14;";
                HBox hBox = new HBox();
                hBox.setPadding(new Insets(0, 0, 10, 0));
                AnchorPane pane = new AnchorPane();
                
                // crea la progress bar
                ProgressBar progress = new ProgressBar();
                progress.setStyle("-fx-accent:" + progetto.getColore().hex + ";");
                progress.setMinHeight(30);
                double percent = ((double) durata / tempoTotale) * 100;
                DecimalFormat df = new DecimalFormat("#.#");
                progress.setProgress((double) durata / tempoTotale);
                
                // crea il label durata
                Label nome = new Label(progetto.getNome());
                nome.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 14; -fx-font-weight: 800;");
                Label ore = new Label("" + ViewHelper.formattaOrologio((int)durata));
                ore.setStyle(stile);
                ore.setPadding(new Insets(0, 10, 0, 10));
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER_RIGHT);
                container.getChildren().addAll(nome, ore);

                // crea il label percentuale
                Label percentuale = new Label(df.format(percent) + "%");
                percentuale.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16; -fx-font-weight: 800;");
                percentuale.setPadding(new Insets(0, 0, 0, 20));
                
                // aggiunge gli elementi alla view
                AnchorPane.setLeftAnchor(progress, 200.0);
                AnchorPane.setRightAnchor(progress, 70.0);
                AnchorPane.setLeftAnchor(container, 0.0);
                AnchorPane.setRightAnchor(percentuale, 0.0);
                pane.getChildren().add(container);
                pane.getChildren().add(progress);
                pane.getChildren().add(percentuale);
                hBox.getChildren().add(pane);
                HBox.setHgrow(pane, Priority.ALWAYS);
                this.progressBarsContainer.setAlignment(Pos.TOP_CENTER);
                this.progressBarsContainer.getChildren().add(hBox);
        	}
        }
    }
    
    /**
     * Verifica se il tempo misurato per il progetto è pari a zero.
     */
    private boolean isProgettoVuoto(IProgetto progetto, boolean isAnno) {
    	long tempoProgetto = calcolaTempoTotaleProgetto(progetto, isAnno);
    	return tempoProgetto <= 0;
    }
    
    public void aggiornaView() {
    	meseChoice.setValue("ALL");
    	this.annoSpinner.getValueFactory().setValue(LocalDate.now().getYear());
        this.visualizzaStackedBarChart(true);
        this.visualizzaPieChart(true);
        this.visualizzaProgressBars(true);
    }
}
