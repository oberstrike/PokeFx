package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.GameLogic;
import views.MapView;

public class GameGuiController implements Initializable {

	GameLogic logic;
	
	@FXML
	void save(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open XML");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog(Main.kprimaryStage);
		if (selectedFile == null) {

		} else {
			try {
				FileWriter writer = new FileWriter(selectedFile);
				Main.xmlControll.saveGameData(Main.gameData, writer);
				System.out.println("Gespeichert.");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

		}
	}
	
	
    @FXML
    private AnchorPane anchor2;
    
    @FXML
    private AnchorPane anchor;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MapView mapView = null;
		
		if(Main.gameData.getMap() == null) {
			mapView = new MapView();
			Main.gameData.setMap(mapView.getMap());
		}else {
			mapView = new MapView();
			mapView.setMap(Main.gameData.getMap());
		}
		
		
		mapView.setPrefWidth(600);
		mapView.setPrefHeight(500);
		mapView.setLayoutY(30);
		anchor.getChildren().add(mapView);
		
		anchor.setFocusTraversable(false);
		anchor.requestFocus();
		
		logic = new GameLogic(mapView, anchor2);
		logic.setDaemon(true);
		logic.start();

		
		mapView.setOnKeyPressed(event -> {
			logic.moveEvent(event.getCode().getName());
		});
		mapView.setFocusTraversable(true);
		mapView.requestFocus();
		
		System.out.println("Startet");
		
	}

}
