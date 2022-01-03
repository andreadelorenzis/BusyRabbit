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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
       
        // progetto 1
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
        
        this.listaProgetti.add(prog1);
        this.listaProgetti.add(prog2);
        this.listaProgetti.add(prog3);
        
        // <---- DEMO
        
        this.visualizzaStackedBarChart(this.calcolaTempoAnno());
        this.visualizzaPieChart();
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
        
        System.out.println(datiAnno);
        return datiAnno;
    }
    
    private void calcolaTempoMese() {
    
    }
    
    private void visualizzaStackedBarChart(ArrayList<XYChart.Series<String, Double>> dati) {
        for(int i = 0; i < dati.size(); i++) {
            this.mesiChart.getData().add(dati.get(i));
        }
        
        int i = 0;
        for(XYChart.Series<String, Double> serie : dati) {
            this.mesiChart.getStyleClass().add(this.listaProgetti.get(i).getColore() + i);
        }
        
    }
    
    private double calcolaTempoTotaleProgetto(ProgettoDemo progetto, boolean anno) {
        double tempoTotale = 0;
        
        if(anno) {
            for(String mese : progetto.getAnno().keySet()) {
                tempoTotale += progetto.getAnno().get(mese);
            }
        }
        
        return tempoTotale;
    }
    
    private void visualizzaPieChart() {
        
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        
        for(int i = 0; i < this.listaProgetti.size(); i++) {
            ProgettoDemo progetto = this.listaProgetti.get(i);
            double tempoTotaleProgetto = this.calcolaTempoTotaleProgetto(progetto, true);
            pieData.add(new PieChart.Data(progetto.getNome(), tempoTotaleProgetto));
        }
        
        PieChart chart = new PieChart(pieData);
        chart.setStyle("-fx-background-color: transparent;");
        chart.setLabelLineLength(30);
        chart.setLabelsVisible(true);
        chart.setLegendVisible(false);
        chart.setMinHeight(550);
        chart.setMinWidth(550);
        chart.setTranslateX(-100);
        
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
            progress.setProgress(10);
            Label nome = new Label(progetto.getNome());
            nome.setStyle("-fx-text-fill: #BAC4CA; -fx-font-size: 14; -fx-font-weight: 800;");
            Label ore = new Label("100:23:20");
            ore.setStyle(stile);
            ore.setPadding(new Insets(0, 10, 0, 10));
            HBox container = new HBox();
            container.getChildren().addAll(nome, ore);
            
            double percent = (this.calcolaTempoTotaleProgetto(progetto, true) / tempoTotale) * 100;
            DecimalFormat df = new DecimalFormat("#.##");
            
            progress.setProgress(percent);
            
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
