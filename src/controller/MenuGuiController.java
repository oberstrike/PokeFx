package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.WindowChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import views.MapView;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuGuiController implements Initializable{

	
	
    @FXML
    void load(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as xml");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
		if(selectedFile == null) {
			
		}else {
			Main.mapView = new MapView();
			Main.mapView.setMap(Main.xmlControll.getMap(selectedFile));
			System.out.println(Main.mapView.getFields());
			WindowChanger changer = new WindowChanger();
			changer.changeWindow("/guis/GameGui.fxml", event);
		}
    }

    @FXML
    void exit(ActionEvent event) {

    }
    
    @FXML
    void create(ActionEvent event) {
    	WindowChanger changer = new WindowChanger();
    	changer.changeWindow("/guis/CreateGui.fxml", event, "Erstellen einer Karte");	
    }

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
