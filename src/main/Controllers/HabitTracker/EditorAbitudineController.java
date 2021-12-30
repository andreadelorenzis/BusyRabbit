/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Controllers.HabitTracker;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author andre
 */
public class EditorAbitudineController {
    
    @FXML
    private TextField abitudineField;
    
    @FXML
    private TextArea descrizioneArea;
    
    @FXML
    private DatePicker dataPicker;
    
    @FXML
    private Label lunBtn;
    private boolean lun = false;
    
    @FXML
    private Label marBtn;
    private boolean mar  = false;
    
    @FXML
    private Label merBtn;
    private boolean mer  = false;
    
    @FXML
    private Label gioBtn;
    private boolean gio  = false;
    
    @FXML
    private Label venBtn;
    private boolean ven  = false;
    
    @FXML
    private Label sabBtn;
    private boolean sab  = false;
    
    @FXML
    private Label domBtn;
    private boolean dom  = false;
    
    private AbitudineDemo abitudine;
    
    @FXML
    private void cambiaNome() {
        this.abitudine.setNome(this.abitudineField.getText());
    }
    
    @FXML
    private void cambiaDescrizione() {
        this.abitudine.setDescrizione(this.descrizioneArea.getText());
    }
    
    private void toggleBtn(Label label, Boolean giorno) {
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
        
        switch(btnGiornoCliccato) {
            case "lunBtn": 
                this.toggleBtn(this.lunBtn, this.lun);
                break;
            case "marBtn": 
                this.toggleBtn(this.marBtn, this.mar);
                 break;
            case "merBtn": 
                this.toggleBtn(this.merBtn, this.mer);
                 break;
            case "gioBtn": 
                this.toggleBtn(this.gioBtn, this.gio);
                 break;
            case "venBtn": 
                this.toggleBtn(this.venBtn, this.ven);
                 break;
            case "sabBtn": 
                this.toggleBtn(this.sabBtn, this.sab);
                 break;
            case "domBtn": 
                this.toggleBtn(this.domBtn, this.dom);
                 break;
        }
        
        ArrayList<String> giorni = new ArrayList<String>();
        
        if(lun)
            giorni.add("LUN");
        if(mar)
            giorni.add("MAR");
        if(mer)
            giorni.add("MER");
        if(gio)
            giorni.add("GIO");
        if(ven)
            giorni.add("VEN");
        if(sab)
            giorni.add("SAB");
        if(dom)
            giorni.add("DOM");
        
        this.abitudine.setGiorniRipetizione(giorni);
    }
    
    public AbitudineDemo getAbitudine() {
        return this.abitudine;
    }

    @FXML
    public void setAbitudine(AbitudineDemo abitudine) {
        this.abitudine = abitudine;
        this.abitudineField.setText(abitudine.getNome());
        this.descrizioneArea.setText(abitudine.getDescrizione());
        
        ArrayList<String> giorni = this.abitudine.getGiorniRipetizione();
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
    
}
