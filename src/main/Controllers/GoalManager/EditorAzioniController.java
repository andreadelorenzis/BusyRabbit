/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.GoalManager;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.Controllers.GoalManager.demo.AzioneDemo;

/**
 *
 * @author andre
 */
public class EditorAzioniController {
    @FXML
    private TextField azioneField;
    
    @FXML
    private Spinner valoreSpinner;
    
    @FXML
    private Label lunBtn;
    
    private boolean lun;
    
    @FXML
    private Label marBtn;
    
    private boolean mar;
    
    @FXML
    private Label merBtn;
    
    private boolean mer;
    
    @FXML
    private Label gioBtn;
    
    private boolean gio;
    
    @FXML
    private Label venBtn;
    
    private boolean ven;
    
    @FXML
    private Label sabBtn;
    
    private boolean sab;
    
    @FXML
    private Label domBtn;
    
    private boolean dom;
    
    private AzioneDemo azione;
    
    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory1.setValue(1);
        this.valoreSpinner.setValueFactory(valueFactory1);
        
        this.valoreSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer t, Integer t1) {
                azione.setValore((int) valoreSpinner.getValue());
            }
        });
        
        this.lun = false;
        this.mar = false;
        this.mer = false;
        this.gio = false;
        this.ven = false;
        this.sab = false;
        this.dom = false;
    }
    
    @FXML
    private void cambiaNome() {
        this.azione.setNome(this.azioneField.getText());
    }
    
    @FXML
    private void toggleBtn(Label label, boolean giorno) {
        if(!giorno) {
            giorno = true;
            label.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
        } else {
            giorno = false;
            label.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE; -fx-border-color: #4361EE;");
        }
    }
    
    @FXML
    private void cambiaGiorni(MouseEvent event) {
        String btnGiornoCliccato = ((Label) event.getSource()).getId();
        System.out.println(btnGiornoCliccato);
        
        switch(btnGiornoCliccato) {
            case "lunBtn": 
                {
                    if(this.lun) {
                        this.lun = false;
                        this.lunBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.lun = true;
                        this.lunBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
            case "marBtn": 
                {
                    if(this.mar) {
                        this.mar = false;
                        this.marBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.mar = true;
                        this.marBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
            case "merBtn": 
                {
                    if(this.mer) {
                        this.mer = false;
                        this.merBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.mer = true;
                        this.merBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
            case "gioBtn": 
                {
                    if(this.gio) {
                        this.gio = false;
                        this.gioBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.gio = true;
                        this.gioBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
            case "venBtn": 
                {
                    if(this.ven) {
                        this.ven = false;
                        this.venBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.ven = true;
                        this.venBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
            case "sabBtn": 
                {
                    if(this.sab) {
                        this.sab = false;
                        this.sabBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.sab = true;
                        this.sabBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
            case "domBtn": 
                {
                    if(this.dom) {
                        this.dom = false;
                        this.domBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4361EE;"
                                + " -fx-border-color: #4361EE; -fx-font-weight: 800;");
                    } else {
                        this.dom = true;
                        this.domBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff; -fx-font-weight: 800;");
                    }
                    break;
                }
        }
        
        ArrayList<String> giorni = new ArrayList<String>();
        
        if(this.lun)
            giorni.add("LUN");
        if(this.mar)
            giorni.add("MAR");
        if(this.mer)
            giorni.add("MER");
        if(this.gio)
            giorni.add("GIO");
        if(this.ven)
            giorni.add("VEN");
        if(this.sab)
            giorni.add("SAB");
        if(this.dom)
            giorni.add("DOM");
        
        this.azione.setGiorni(giorni);
    }
    
    public void setAzione(AzioneDemo azione) {
        this.azione = azione;
        this.azioneField.setText(azione.getNome());
        this.valoreSpinner.getValueFactory().setValue(azione.getValore());
        
        ArrayList<String> giorni = this.azione.getGiorni();
        for(int i = 0; i < giorni.size(); i++) {
            switch(giorni.get(i)) {
                case "LUN": 
                    {
                        this.lun = true;
                        this.lunBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
                    
                case "MAR":
                    {
                        this.mar = true;
                        this.marBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
                case "MER":
                    {
                        this.mer = true;
                        this.merBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
                case "GIO":
                    {
                        this.gio = true;
                        this.gioBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
                case "VEN":
                    {
                        this.ven = true;
                        this.venBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
                case "SAB":
                    {
                        this.sab = true;
                        this.sabBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
                case "DOM":
                    {
                        this.dom = true;
                        this.domBtn.setStyle("-fx-background-color: #4361EE; -fx-text-fill: #ffffff;");
                         break;
                    }
            }
        }
    }
    
    public AzioneDemo getAzione() {
        return this.azione;
    }
    
}
