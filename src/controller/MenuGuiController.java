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
import xml.GameData;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuGuiController implements Initializable{

	
    @FXML
    void loadGame(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as xml");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
		if(selectedFile == null) {
			
		}else {
			
			Main.gameData = Main.xmlControll.getGame(selectedFile);
			System.out.println(Main.gameData.getPlayer());
			WindowChanger changer = new WindowChanger();
			changer.changeWindow("/guis/GameGui.fxml", event);
		}
    }
	
	
    @FXML
    void load(ActionEvent event) {
    	File selectedFile = new File("maps/start.xml");
//    	File selectedFile = new File("test123.xml");
//		if(selectedFile == null) {
//			FileChooser fileChooser = new FileChooser();
//			fileChooser.setTitle("Save as xml");
//			fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));
//			selectedFile = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
//		}else {
			MapView mapView = new MapView();
			mapView.setMap(Main.xmlControll.getMap(selectedFile));
			Main.gameData = new GameData(mapView.getMap());
			
			WindowChanger changer = new WindowChanger();
			changer.changeWindow("/guis/GameGui.fxml", event);
//		}
    }

    @FXML
    void exit(ActionEvent event) {
    	System.exit(0);
    }
    
    @FXML
    void create(ActionEvent event) {
    	WindowChanger changer = new WindowChanger();
    	changer.changeWindow("/guis/CreateGui.fxml", event, "Erstellen einer Karte");	
    }

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Main.gameData == null)
			Main.gameData = new GameData();
	}

}
