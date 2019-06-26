/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	model.creaGrafo(boxAnno.getValue());
    	txtResult.appendText(model.getGrafo().toString());
    	txtResult.appendText(model.visualizzaAdiacenti());
    	
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	int mese=boxMese.getValue();
    	int giorno=boxGiorno.getValue();
    	int agenti=Integer.parseInt(txtN.getText());
    	boolean corretto=true;
    
    	if((boxMese.getValue()==11 || boxMese.getValue()==4 || boxMese.getValue()==6 || boxMese.getValue()==9) && boxGiorno.getValue()==31) {
    		txtResult.appendText("\nIl mese selezionato non ha il 31esimo giorno!");
    		corretto=false;
    	}
    	if(boxMese.getValue()==2 && (boxGiorno.getValue()==29 || boxGiorno.getValue()==30 || boxGiorno.getValue()==31)){
    		txtResult.appendText("\nIl mese di febbraio si ferma a 28 giorni!");
    		corretto=false;
    	}
    	if(agenti<1 || agenti>10) {
    		txtResult.appendText("\nPuoi selezionare un numero di agenti compreso tra 1 e 10.");
    		corretto=false;
    	}
    	if(corretto==true) {
    		int malgestiti=model.simula(agenti, boxAnno.getValue(), mese, giorno);
    		txtResult.appendText("\nSimulazione Terminata. Eventi malgestiti "+malgestiti);
    	}
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.caricaDate());
    	List<Integer> mesi=new LinkedList<Integer>();
    	List<Integer> giorni = new LinkedList<Integer>();
    	for(int i=1; i<=12; i++) {
    		mesi.add(i);
    	}
    	for(int i=1; i<=31; i++) {
    		giorni.add(i);
    	}
    	boxMese.getItems().addAll(mesi);
    	boxGiorno.getItems().addAll(giorni);
    }
}
