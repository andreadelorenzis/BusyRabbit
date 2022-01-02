/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.Dashboard;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.Controllers.TimeTracker.ProgettoDemo;

/**
 *
 * @author andre
 */
public class ReportTempoController implements Initializable {
    
    @FXML
    private StackedBarChart mesiChart;
    
    @FXML
    private CategoryAxis mesiX;
    
    @FXML
    private NumberAxis mesiY;
    
    @FXML
    private HBox pieChartContainer;
    
    @FXML
    private VBox progressBarsContainer;
    
    @FXML
    private Spinner annoSpinner;
    
    @FXML
    private ChoiceBox meseChoice;
    
    private int annoSelezionato;
    
    private String meseSelezionato;
    
    private ArrayList<ProgettoDemo> listaProgetti;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // ----> DEMO
        
        this.listaProgetti = new ArrayList<>();
        
        // progetto 1
        Map<String, Double> anno1 = new HashMap<>();
        anno1.put("GEN", 40.0);
        anno1.put("FEB", 30.0);
        anno1.put("MAR", 10.0);
        anno1.put("APR", 60.0);
        anno1.put("MAG", 30.0);
        anno1.put("GIU", 40.0);
        anno1.put("LUG", 20.0);
        anno1.put("AGO", 80.0);
        anno1.put("SET", 40.0);
        anno1.put("OTT", 10.0);
        anno1.put("NOV", 70.0);
        anno1.put("DIC", 80.0);
        ProgettoDemo prog1 = new ProgettoDemo("Social Network", "blu");
        prog1.setHexColore("#008FFB");
        prog1.setAnno(anno1);
        
        Map<String, Double> mese1 = new HashMap<>();
        mese1.put("01", 4.0);
        mese1.put("02", 4.0);
        mese1.put("03", 7.0);
        mese1.put("04", 8.0);
        mese1.put("05", 2.0);
        mese1.put("06", 3.0);
        mese1.put("07", 4.0);
        mese1.put("08", 1.0);
        mese1.put("09", 5.0);
        mese1.put("10", 3.0);
        mese1.put("11", 2.0);
        mese1.put("12", 5.0);
        mese1.put("13", 1.0);
        mese1.put("14", 2.0);
        mese1.put("15", 4.0);
        mese1.put("16", 2.0);
        mese1.put("17", 1.0);
        mese1.put("18", 2.0);
        mese1.put("19", 3.0);
        mese1.put("20", 2.0);
        mese1.put("21", 2.0);
        mese1.put("22", 1.0);
        mese1.put("23", 4.0);
        mese1.put("24", 3.0);
        mese1.put("25", 2.0);
        mese1.put("26", 5.0);
        mese1.put("27", 4.0);
        mese1.put("28", 4.0);
        mese1.put("29", 3.0);
        mese1.put("30", 2.0);
        
        prog1.setMese(mese1);
       
        // progetto 2
        Map<String, Double> anno2 = new HashMap<String, Double>();
        anno2.put("GEN", 40.0);
        anno2.put("FEB", 30.0);
        anno2.put("MAR", 10.0);
        anno2.put("APR", 60.0);
        anno2.put("MAG", 30.0);
        anno2.put("GIU", 40.0);
        anno2.put("LUG", 20.0);
        anno2.put("AGO", 80.0);
        anno2.put("SET", 40.0);
        anno2.put("OTT", 10.0);
        anno2.put("NOV", 70.0);
        anno2.put("DIC", 80.0);
        ProgettoDemo prog2 = new ProgettoDemo("Analisi 2", "giallo");
        prog2.setHexColore("#FEB019");
        prog2.setAnno(anno2);
        
        Map<String, Double> mese2 = new HashMap<>();
        mese2.put("01", 4.0);
        mese2.put("02", 4.0);
        mese2.put("03", 7.0);
        mese2.put("04", 8.0);
        mese2.put("05", 2.0);
        mese2.put("06", 3.0);
        mese2.put("07", 4.0);
        mese2.put("08", 1.0);
        mese2.put("09", 5.0);
        mese2.put("10", 3.0);
        mese2.put("11", 2.0);
        mese2.put("12", 5.0);
        mese2.put("13", 1.0);
        mese2.put("14", 2.0);
        mese2.put("15", 4.0);
        mese2.put("16", 2.0);
        mese2.put("17", 1.0);
        mese2.put("18", 2.0);
        mese2.put("19", 3.0);
        mese2.put("20", 2.0);
        mese2.put("21", 2.0);
        mese2.put("22", 1.0);
        mese2.put("23", 4.0);
        mese2.put("24", 3.0);
        mese2.put("25", 2.0);
        mese2.put("26", 5.0);
        mese2.put("27", 4.0);
        mese2.put("28", 4.0);
        mese2.put("29", 3.0);
        mese2.put("30", 2.0);
        
        prog2.setMese(mese2);
        
        // progetto 1
        Map<String, Double> anno3 = new HashMap<String, Double>();
        anno3.put("GEN", 4.0);
        anno3.put("FEB", 3.0);
        anno3.put("MAR", 1.0);
        anno3.put("APR", 6.0);
        anno3.put("MAG", 3.0);
        anno3.put("GIU", 4.0);
        anno3.put("LUG", 2.0);
        anno3.put("AGO", 8.0);
        anno3.put("SET", 4.0);
        anno3.put("OTT", 1.0);
        anno3.put("NOV", 7.0);
        anno3.put("DIC", 8.0);
        ProgettoDemo prog3 = new ProgettoDemo("Workout", "verde");
        prog3.setHexColore("#00E396");
        prog3.setAnno(anno3);
        
        Map<String, Double> mese3 = new HashMap<>();
        mese3.put("01", 4.0);
        mese3.put("02", 4.0);
        mese3.put("03", 7.0);
        mese3.put("04", 8.0);
        mese3.put("05", 2.0);
        mese3.put("06", 3.0);
        mese3.put("07", 4.0);
        mese3.put("08", 1.0);
        mese3.put("09", 5.0);
        mese3.put("10", 3.0);
        mese3.put("11", 2.0);
        mese3.put("12", 5.0);
        mese3.put("13", 1.0);
        mese3.put("14", 2.0);
        mese3.put("15", 4.0);
        mese3.put("16", 2.0);
        mese3.put("17", 1.0);
        mese3.put("18", 2.0);
        mese3.put("19", 3.0);
        mese3.put("20", 2.0);
        mese3.put("21", 2.0);
        mese3.put("22", 1.0);
        mese3.put("23", 4.0);
        mese3.put("24", 3.0);
        mese3.put("25", 2.0);
        mese3.put("26", 5.0);
        mese3.put("27", 4.0);
        mese3.put("28", 4.0);
        mese3.put("29", 3.0);
        mese3.put("30", 2.0);
        
        prog3.setMese(mese3);
        
        this.listaProgetti.add(prog1);
        this.listaProgetti.add(prog2);
        this.listaProgetti.add(prog3);
        
        // <---- DEMO

        // Inizializzare ChoiceBox scelta mese e aggiungere event handler.
        ObservableList<String> mesi = this.meseChoice.getItems();
        mesi.add("Gen");
        mesi.add("Feb");
        mesi.add("Mar");
        mesi.add("Apr");
        mesi.add("Mag");
        mesi.add("Giu");
        mesi.add("Lug");
        mesi.add("Ago");
        mesi.add("Set");
        mesi.add("Ott");
        mesi.add("Nov");
        mesi.add("Dic");
        mesi.add("Seleziona mese");
        meseChoice.setValue("Seleziona mese");
        meseChoice.getSelectionModel().selectedIndexProperty().addListener(
                 (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    this.meseSelezionato = (String) meseChoice.getItems().get((Integer) new_val);
                    this.visualizzaStackedBarChart(this.calcolaTempoMese());
                    this.visualizzaPieChart(this.calcolaTempiTotaliProgetti(false));
                    
                    if((String) meseChoice.getItems().get((Integer) new_val) == "Selezione mese") {
                        this.visualizzaStackedBarChart(this.calcolaTempoAnno());
                        this.visualizzaPieChart(this.calcolaTempiTotaliProgetti(true));
                    }
        });
        
        // Inizializzare spinner scelta anno e aggiungere event handler.
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2021, 2100);
        valueFactory1.setValue(2021);
        this.annoSpinner.setValueFactory(valueFactory1);
        
        this.annoSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                annoSelezionato = (int) annoSpinner.getValue();
                meseChoice.setValue("Seleziona mese");
                visualizzaStackedBarChart(calcolaTempoAnno());
                visualizzaPieChart(calcolaTempiTotaliProgetti(true));
            }
        });
        
        this.visualizzaStackedBarChart(this.calcolaTempoAnno());
        this.visualizzaPieChart(this.calcolaTempiTotaliProgetti(true));
        this.visualizzaProgressBars();
    }
    
    private ArrayList<XYChart.Series<String, Double>> calcolaTempoAnno() {
        ArrayList<XYChart.Series<String, Double>> datiAnno = new ArrayList<XYChart.Series<String, Double>>();
        
        for(int i = 0; i < this.listaProgetti.size(); i++) {
           ProgettoDemo progetto = this.listaProgetti.get(i);
            
           XYChart.Series<String, Double> tempiProgetto = new XYChart.Series<>();
           tempiProgetto.setName(progetto.getNome());
           tempiProgetto.getData().add(new XYChart.Data<>("Jan", progetto.getAnno().get("GEN")));
           tempiProgetto.getData().add(new XYChart.Data<>("Feb", progetto.getAnno().get("FEB")));
           tempiProgetto.getData().add(new XYChart.Data<>("Mar", progetto.getAnno().get("MAR")));
           tempiProgetto.getData().add(new XYChart.Data<>("Apr", progetto.getAnno().get("APR")));
           tempiProgetto.getData().add(new XYChart.Data<>("Mag", progetto.getAnno().get("MAG")));
           tempiProgetto.getData().add(new XYChart.Data<>("Giu", progetto.getAnno().get("GIU")));
           tempiProgetto.getData().add(new XYChart.Data<>("Lug", progetto.getAnno().get("LUG")));
           tempiProgetto.getData().add(new XYChart.Data<>("Ago", progetto.getAnno().get("AGO")));
           tempiProgetto.getData().add(new XYChart.Data<>("Set", progetto.getAnno().get("SET")));
           tempiProgetto.getData().add(new XYChart.Data<>("Ott", progetto.getAnno().get("OTT")));
           tempiProgetto.getData().add(new XYChart.Data<>("Nov", progetto.getAnno().get("NOV")));
           tempiProgetto.getData().add(new XYChart.Data<>("Dic", progetto.getAnno().get("DIC")));
           
           datiAnno.add(tempiProgetto);
        }
        
        return datiAnno;
    }
    
    private ArrayList<XYChart.Series<String, Double>> calcolaTempoMese() {
        ArrayList<XYChart.Series<String, Double>> datiMese = new ArrayList<XYChart.Series<String, Double>>();
        
        for(int i = 0; i < this.listaProgetti.size(); i++) {
            ProgettoDemo progetto = this.listaProgetti.get(i);
            
            XYChart.Series<String, Double> tempiProgetto = new XYChart.Series<>();
            tempiProgetto.setName(progetto.getNome());
            for(String key : progetto.getMese().keySet()) {
                tempiProgetto.getData().add(new XYChart.Data<>(key, progetto.getMese().get(key)));
            }
            datiMese.add(tempiProgetto);
        }
        
        return datiMese;
    }
    
    private void visualizzaStackedBarChart(ArrayList<XYChart.Series<String, Double>> dati) {
        this.mesiChart.getData().clear();
        
        for(int i = 0; i < dati.size(); i++) {
            this.mesiChart.getData().add(dati.get(i));
        }
        
        int i = 0;
        for(XYChart.Series<String, Double> serie : dati) {
            this.mesiChart.getStyleClass().add(this.listaProgetti.get(i).getColore() + i);
        }
        
    }
    
    private ObservableList<PieChart.Data> calcolaTempiTotaliProgetti(boolean anno) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        
        for(ProgettoDemo progetto : this.listaProgetti) {
            double tempoTotale = this.calcolaTempoTotaleProgetto(progetto, anno);
            pieData.add(new PieChart.Data(progetto.getNome(), tempoTotale));
        }
        
        return pieData;
    }
    
    private double calcolaTempoTotaleProgetto(ProgettoDemo progetto, boolean anno) {
        double tempoTotale = 0;
        
        if(anno) {
            for(String mese : progetto.getAnno().keySet()) {
                tempoTotale += progetto.getAnno().get(mese);
            }
        } else {
            for(String giorno : progetto.getMese().keySet()) {
                tempoTotale += progetto.getMese().get(giorno);
            }
        }
        
        return tempoTotale;
    }
    
    private void visualizzaPieChart(ObservableList<PieChart.Data> pieData) {
        
        this.pieChartContainer.getChildren().clear();
        
        PieChart chart = new PieChart(pieData);
        chart.setStyle("-fx-background-color: transparent;");
        chart.setLabelLineLength(30);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(false);
        chart.setMinHeight(550);
        chart.setMinWidth(550);
        chart.setTranslateX(-50);
        
        int i = 0;
        for(PieChart.Data data : pieData) {
            chart.getStyleClass().add(this.listaProgetti.get(i).getColore() + i);
        }
        
        this.pieChartContainer.getChildren().add(chart);
    }
    
    private void visualizzaProgressBars() {
        double tempoTotale = 0;
        
        for(int i = 0; i < this.listaProgetti.size(); i++) {
            tempoTotale += this.calcolaTempoTotaleProgetto(this.listaProgetti.get(i), true);
        }
        
        for(int i = 0; i < this.listaProgetti.size(); i++) {
            ProgettoDemo progetto = this.listaProgetti.get(i);
            
            String stile = "-fx-text-fill: #BAC4CA; -fx-font-size: 14;";

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(0, 0, 10, 0));
            AnchorPane pane = new AnchorPane();
            
            ProgressBar progress = new ProgressBar();
            progress.setStyle("-fx-accent:" + progetto.getHexColore() + ";");
            progress.setMinHeight(30);
            
            Label nome = new Label(progetto.getNome());
            nome.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 14; -fx-font-weight: 800;");
            Label ore = new Label("100:23:20");
            ore.setStyle(stile);
            ore.setPadding(new Insets(0, 10, 0, 10));
            HBox container = new HBox();
            container.getChildren().addAll(nome, ore);
            
            double percent = (this.calcolaTempoTotaleProgetto(progetto, true) / tempoTotale) * 100;
            DecimalFormat df = new DecimalFormat("#.##");
            
            progress.setProgress(this.calcolaTempoTotaleProgetto(progetto, true) / tempoTotale);
            
            Label percentuale = new Label(df.format(percent) + "%");
            percentuale.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16; -fx-font-weight: 800;");
            percentuale.setPadding(new Insets(0, 0, 0, 20));
            AnchorPane.setLeftAnchor(progress, 200.0);
            AnchorPane.setRightAnchor(progress, 70.0);
            AnchorPane.setLeftAnchor(container, 0.0);
            AnchorPane.setRightAnchor(percentuale, 0.0);
            pane.getChildren().add(container);
            pane.getChildren().add(progress);
            pane.getChildren().add(percentuale);
            hBox.getChildren().add(pane);
            HBox.setHgrow(pane, Priority.ALWAYS);
            
            this.progressBarsContainer.setAlignment(Pos.CENTER);
            this.progressBarsContainer.getChildren().add(hBox);
        }
        
    }
    
}
